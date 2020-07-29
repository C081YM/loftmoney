package com.kpetrov.loftmoney;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;

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

    //RecyclerView recyclerView;

    FloatingActionButton floatingActionButton;

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    ItemsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tabLayout = findViewById(R.id.tabs);


        ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new BudgetPagerAdapter(getSupportFragmentManager(),FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT));

        //recyclerView = findViewById(R.id.recycler);

        floatingActionButton = findViewById(R.id.floatingActionButton);

        adapter = new ItemsAdapter();

        //recyclerView.setAdapter(adapter);
       // recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));

        //DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);      //divider
        //dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.recyclerview_divider));                             //divider
        //recyclerView.addItemDecoration(dividerItemDecoration);                                                                      //divider

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

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText(R.string.expenses);
        tabLayout.getTabAt(1).setText(R.string.income);


        configureAddExpenseView();


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

    static class BudgetPagerAdapter extends FragmentPagerAdapter {

        public BudgetPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return BudgetFragment.newInstance(R.color.colorItemPrice);
                case 1:
                    return BudgetFragment.newInstance(R.color.colorItemIncome);
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

}



