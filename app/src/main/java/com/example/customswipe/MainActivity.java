package com.example.customswipe;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private Context context = this;
    private CustomSwipeView recyclerView;

    private String[] titles = {"Anastasia", "Yuliya", "Denis", "Dimitry"};

    private List<Item> itemList = new ArrayList<>();
    private SwipeViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initView();

    }

    private void initData() {
        for (String title : titles) {
            itemList.add(new Item(title));
        }
    }

    private void initView() {
        recyclerView = (CustomSwipeView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SwipeViewAdapter(itemList, context);
        recyclerView.setAdapter(adapter);
    }
}
