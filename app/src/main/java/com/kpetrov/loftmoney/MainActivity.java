package com.kpetrov.loftmoney;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    FloatingActionButton floatingActionButton;

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    ItemsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler);

        floatingActionButton = findViewById(R.id.addExpenseView);

        adapter = new ItemsAdapter();

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);      //divider
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.recyclerview_divider));                             //divider
        recyclerView.addItemDecoration(dividerItemDecoration);                                                                      //divider


        configureAddExpenseView();

        /*final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                // print NEW THREAD activity
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        // print MAIN THREAD activity
                    }
                });
            }
        }).start();*/

    }

    @Override
    protected void onResume() {
        super.onResume();
        generateExpenses();
    }

    private void configureAddExpenseView() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AddItemActivity.class);
                startActivity(intent);
            }
        });
    }

    private void generateExpenses() {
        final List<Item> items = new ArrayList<>();
        Disposable disposable = ((LoftApp) getApplication()).getMoneyApi().getMoney("expense")
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MoneyResponse>() {
                    @Override
                    public void accept(MoneyResponse moneyResponse) throws Exception {
                        Log.e("TAG","Success " + moneyResponse);
                        for (MoneyItem moneyItem : moneyResponse.getMoneyItemList()) {
                            items.add(Item.getInstance(moneyItem));
                        }
                        adapter.setData(items);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("TAG","Error " + throwable);
                    }
                });
        compositeDisposable.add(disposable);
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



