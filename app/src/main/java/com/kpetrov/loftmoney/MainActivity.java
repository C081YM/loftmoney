package com.kpetrov.loftmoney;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import com.kpetrov.loftmoney.cells.money.ItemsAdapter;
import com.kpetrov.loftmoney.cells.money.Item;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 10;
    RecyclerView recyclerView;
    private ItemsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button plusButton = findViewById(R.id.plusButton);
        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddItemActivity.class);
                startActivityForResult(intent, 1);
            }
        });

       recyclerView = findViewById(R.id.recycler);
       adapter = new ItemsAdapter();

       recyclerView.setAdapter(adapter);
       recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

       adapter.setData(generateExpenses());
       adapter.addData(generateIncomes());

       new Handler().postDelayed(new Runnable() {
       @Override
           public void run() {
               adapter.addData(generateExpenses());
               adapter.addData(generateIncomes());
           }
        }, 3000);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);      //divider
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.recyclerview_divider));                             //divider
        recyclerView.addItemDecoration(dividerItemDecoration);                                                                      //divider

    }


    private List<Item> generateExpenses() {
        List<Item> items = new ArrayList<>();
        items.add(new Item("Milk","155",R.color.colorItemPrice));
        return items;
    }

    private List<Item> generateIncomes() {
        List<Item> items = new ArrayList<>();
        items.add(new Item("Премия", "8000", R.color.colorItemIncome));
        return items;
    }


    /*@Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            adapter.addData(new Item(data.getStringExtra("name"), data.getStringExtra("price"), R.color.colorItemPrice));

        }
    }*/
}



