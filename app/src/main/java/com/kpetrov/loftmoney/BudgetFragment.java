package com.kpetrov.loftmoney;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class BudgetFragment extends Fragment {

    public static final int REQUEST_CODE = 100;

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    SwipeRefreshLayout swipeRefreshLayout;

    ItemsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_budget,null);

        adapter = new ItemsAdapter();

        RecyclerView recyclerView = view.findViewById(R.id.recycler);

        /*swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                generateExpense();             // в видео здесь loadItems()
            }
        });*/



        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(Objects.requireNonNull(getActivity()),DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.recyclerview_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);

        recyclerView.setAdapter(adapter);

        return view;
    }



    @Override
    public void onResume() {
        super.onResume();
        generateExpense();
    }

    private void generateExpense() {
        final List<Item> items = new ArrayList<>();
        Disposable disposable = ((LoftApp) getActivity().getApplication()).getMoneyApi().getMoney("expense")
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MoneyResponse>() {
                    @Override
                    public void accept(MoneyResponse moneyResponse) throws Exception {
                        Log.e("TAG", "Success " + moneyResponse);
                        //adapter.clearItems();
                        //swipeRefreshLayout.setOnRefreshListener(false);                               //поставить false после получения ответа от сервера
                        for (MoneyItem moneyItem : moneyResponse.getMoneyItemList()) {
                            items.add(Item.getInstance(moneyItem));
                        }
                        adapter.setData(items);
                                                    //
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("TAG","Error " + throwable);
                        //swipeRefreshLayout.setRefreshing(false);                                      //
                    }
                });
        compositeDisposable.add(disposable);
    }
}