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
import com.kpetrov.loftmoney.cells.money.ItemsAdapter;
import com.kpetrov.loftmoney.cells.money.Item;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 100;
    RecyclerView recyclerView;
    private ItemsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button plusButton = findViewById(R.id.plusButton);
        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                startActivityForResult(new Intent(MainActivity.this, AddItemActivity.class), REQUEST_CODE);
            }
        });

       recyclerView = findViewById(R.id.recycler);
       adapter = new ItemsAdapter();

       recyclerView.setAdapter(adapter);
       recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

       new Handler().postDelayed(new Runnable() {
       @Override
           public void run() {
               adapter.addData(new Item("Milk","50", R.color.colorItemPrice));
               adapter.addData(new Item("Income","120", R.color.colorItemIncome));
           }
       }, 2000);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);      //divider
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.recyclerview_divider));                             //divider
        recyclerView.addItemDecoration(dividerItemDecoration);                                                                      //divider

    }

    private void generateExpenses() {
       adapter.addData(new Item("Комод", "5000", R.color.colorItemPrice));
    }

    private void generateIncomes() {
        adapter.addData(new Item("Проценты по вкладу", "10000", R.color.colorItemIncome));
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode,@Nullable final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            adapter.addData(new Item(data.getStringExtra("name"), data.getStringExtra("price"), R.color.colorItemPrice));

        }
    }
}



