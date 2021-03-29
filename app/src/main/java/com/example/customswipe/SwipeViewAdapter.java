package com.example.customswipe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SwipeViewAdapter extends RecyclerView.Adapter<SwipeViewAdapter.ViewHolder> {

    private List<Item> itemList;
    private Context context;
    private LayoutInflater layoutInflater;

    public SwipeViewAdapter(List<Item> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(this.context);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(layoutInflater.inflate(R.layout.item_linear, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.item_title.setText(itemList.get(position).getItem());
    }

    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView item_title, item_edit;
        LinearLayout ll_item, ll_hidden;

        public ViewHolder(View itemView) {
            super(itemView);
            item_title = (TextView) itemView.findViewById(R.id.item_title);

            ll_item = (LinearLayout) itemView.findViewById(R.id.ll_item);
            ll_hidden = (LinearLayout) itemView.findViewById(R.id.ll_hidden);
            item_edit = itemView.findViewById(R.id.item_edit);
        }
    }
}
