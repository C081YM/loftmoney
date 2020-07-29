package com.kpetrov.loftmoney;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ItemsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler);
        adapter = new ItemsAdapter();

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);      //divider
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.recyclerview_divider));                             //divider
        recyclerView.addItemDecoration(dividerItemDecoration);                                                                      //divider

        adapter.setData(generateExpenses());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.addData(generateIncomes());
            }
        }, 3000);

    }

    private List<Item> generateExpenses() {
        List<Item> items = new ArrayList<>();
        items.add(new Item("Milk","49", R.color.colorItemPrice));
        items.add(new Item("Milk","49", R.color.colorItemPrice));
        items.add(new Item("Milk","49", R.color.colorItemPrice));
        items.add(new Item("Milk","49", R.color.colorItemPrice));
        items.add(new Item("Milk","49", R.color.colorItemPrice));
        items.add(new Item("Milk","49", R.color.colorItemPrice));
        items.add(new Item("Milk","49", R.color.colorItemPrice));
        items.add(new Item("Milk","49", R.color.colorItemPrice));
        items.add(new Item("Milk","49", R.color.colorItemPrice));
        items.add(new Item("Milk","49", R.color.colorItemPrice));

        return items;
    }

    private List<Item> generateIncomes() {
        List<Item> items = new ArrayList<>();
        items.add(new Item("ZP","15000", R.color.colorItemIncome));
        items.add(new Item("ZP","15000", R.color.colorItemIncome));
        items.add(new Item("ZP","15000", R.color.colorItemIncome));
        items.add(new Item("ZP","15000", R.color.colorItemIncome));
        items.add(new Item("ZP","15000", R.color.colorItemIncome));
        items.add(new Item("ZP","15000", R.color.colorItemIncome));
        items.add(new Item("ZP","15000", R.color.colorItemIncome));
        items.add(new Item("ZP","15000", R.color.colorItemIncome));
        items.add(new Item("ZP","15000", R.color.colorItemIncome));
        items.add(new Item("ZP","15000", R.color.colorItemIncome));

        return items;
    }


}



