package com.example.customswipe;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class CustomSwipeView extends RecyclerView {

    enum SwipeState {
        LRIGHT, RLEFT, DEFAULT
    }

    private int xDown, xUp;

    private int curSelectPosition, hiddenWidth;

    SwipeState swipeState = SwipeState.DEFAULT;

    private TextView editItem;
    private LinearLayout deleteItem;


    public CustomSwipeView(Context context) {
        this(context, null);
    }

    public CustomSwipeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomSwipeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        int x = (int)e.getX();
        int y = (int)e.getY();

        switch (e.getAction()){
            case MotionEvent.ACTION_DOWN:
            {
                xDown = x;

                int firstPosition = ((LinearLayoutManager)getLayoutManager()).findFirstVisibleItemPosition();
                Rect itemRect = new Rect();

                final int count = getChildCount();

                for (int i=0; i<count; i++){
                    final View child = getChildAt(i);
                    if (child.getVisibility() == View.VISIBLE){
                        child.getHitRect(itemRect);
                        if (itemRect.contains(x, y)){
                            curSelectPosition = firstPosition + i;
                            break;
                        }
                    }
                }
                if (swipeState != SwipeState.DEFAULT) {
                    cancelSwipe();
                }

                View item = getChildAt(curSelectPosition - firstPosition);

                if (item != null) {
                    SwipeViewAdapter.ViewHolder viewHolder = (SwipeViewAdapter.ViewHolder) getChildViewHolder(item);
                    editItem = viewHolder.item_edit;
                    deleteItem = viewHolder.ll_hidden;
                    hiddenWidth = deleteItem.getWidth();
                }
                break;
            }

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL: {
                xUp = x;

                if (xUp - xDown > 120)
                    swipeRight();
                else if (xUp - xDown < -120)
                    swipeLeft();
                break;
            }
        }
        return super.onTouchEvent(e);
    }

    private void swipeRight() {
        switch (swipeState) {
            case DEFAULT:
            {
                swipeState = SwipeState.LRIGHT;
                editItem.setVisibility(VISIBLE);
                ObjectAnimator.ofFloat(editItem, View.TRANSLATION_X, -hiddenWidth, hiddenWidth).start();
                break;
            }
            case LRIGHT:
            {
                cancelRightSwipe();
                break;
            }
            default: break;
        }
    }
    private void swipeLeft() {
        switch (swipeState) {
            case DEFAULT:
            {
                swipeState = SwipeState.RLEFT;
                ObjectAnimator.ofFloat(deleteItem, View.TRANSLATION_X, hiddenWidth,-hiddenWidth).start();
                break;
            }
            case RLEFT:
            {
                cancelLeftSwipe();
                break;
            }
            default: break;
        }
    }


    private void cancelLeftSwipe() {
        swipeState = SwipeState.DEFAULT;
        ObjectAnimator.ofFloat(deleteItem, View.TRANSLATION_X, -hiddenWidth,hiddenWidth).start();
    }

    private void cancelRightSwipe() {
        swipeState = SwipeState.DEFAULT;
        editItem.setVisibility(INVISIBLE);
        ObjectAnimator.ofFloat(editItem, View.TRANSLATION_X, hiddenWidth, -hiddenWidth).start();
    }

    private void cancelSwipe() {
        if (swipeState == SwipeState.LRIGHT) {
            cancelRightSwipe();
        }
        else cancelLeftSwipe();
    }
}