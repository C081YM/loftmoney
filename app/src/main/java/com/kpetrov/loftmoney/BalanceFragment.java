package com.kpetrov.loftmoney;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class BalanceFragment extends Fragment {

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    SwipeRefreshLayout swipeRefreshLayout;
    private TextView balanceExpenses;
    private TextView balanceIncomes;
    private TextView availableFinances;
    private BalanceView balanceView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_balance,null);

        balanceExpenses = view.findViewById(R.id.expenses);
        balanceIncomes = view.findViewById(R.id.incomes);
        availableFinances = view.findViewById(R.id.availableFinances);
        balanceView = view.findViewById(R.id.balanceView);

        swipeRefreshLayout = view.findViewById(R.id.balance_swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadTotalValues();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadTotalValues();
    }

    public void loadTotalValues() {
        final String token = PreferenceManager.getDefaultSharedPreferences(getContext()).getString(Prefs.TOKEN, "");

        Disposable disposable = ((LoftApp) getActivity().getApplication()).getMoneyApi().getBalance(token)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(new Action() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                })
                .subscribe(new Consumer<BalanceResponse>() {
                    @Override
                    public void accept(BalanceResponse balanceResponse) throws Exception {
                        Log.e("TAG", "Completed load total values");

                        final String totalExpenses = balanceResponse.getTotalExpenses();
                        final String totalIncomes = balanceResponse.getTotalIncomes();

                        balanceExpenses.setText(String.valueOf(totalExpenses) + " ₽");
                        balanceIncomes.setText(String.valueOf(totalIncomes) + " ₽");

                        int totalExpensesInt = Integer.parseInt(totalExpenses);
                        int totalIncomesInt = Integer.parseInt(totalIncomes);

                        availableFinances.setText(String.valueOf(totalIncomesInt - totalExpensesInt) + " ₽");
                        balanceView.update(totalExpensesInt,totalIncomesInt);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("TAG","Error " + throwable);
                    }
                });
        compositeDisposable.add(disposable);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final BalanceView balanceView = view.findViewById(R.id.balanceView);
    }

    public static BalanceFragment getInstance() {
        return new BalanceFragment();
    }
}