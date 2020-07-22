package com.kpetrov.loftmoney;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.kpetrov.loftmoney.cells.money.ItemsAdapter;
import com.kpetrov.loftmoney.cells.money.ItemsAdapterClick;
import com.kpetrov.loftmoney.cells.money.Item;
import java.util.ArrayList;
import java.util.List;

public class ItemActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ItemsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        recyclerView = findViewById(R.id.recycler);
        adapter = new ItemsAdapter();
        adapter.setItemsAdapterClick(new ItemsAdapterClick() {
            @Override
            public void onMoneyClick(Item item) {
                Intent intent = new Intent(getApplicationContext(), AddItemActivity.class);
                startActivity(intent);
            }

            @Override
            public void onValueClick(String value) {

            }
        });

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);      //divider
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.recyclerview_divider));                             //divider
        recyclerView.addItemDecoration(dividerItemDecoration);                                                                      //divider

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        adapter.setData(generateExpenses());
        adapter.setData(generateIncomes());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.addData(generateExpenses());
                adapter.addData(generateIncomes());

            }
        }, 3000);

    }


    private List<Item> generateExpenses() {
        List<Item> items = new ArrayList<>();
        items.add(new Item("Молоко", "70 ₽", R.color.colorItemPrice));
        items.add(new Item("Зубная щётка", "70 ₽", R.color.colorItemPrice));
        items.add(new Item("Сковородка с антипригарным покрытием", "1670 ₽", R.color.colorItemPrice));

        return items;
    }

    private List<Item> generateIncomes() {
        List<Item> items = new ArrayList<>();
        items.add(new Item("Зарплата.Июнь", "70000 ₽", R.color.colorItemIncome));
        items.add(new Item("Премия", "7000 ₽", R.color.colorItemIncome));
        items.add(new Item("Олег наконец-то вернул долг", "300000 ₽", R.color.colorItemIncome));

        return items;
    }

}








