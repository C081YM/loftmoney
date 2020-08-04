package com.kpetrov.loftmoney;

import android.app.Notification;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class BalanceFragment extends Fragment {

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    private TextView expenses;
    private TextView incomes;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_balance,null);

        expenses = view.findViewById(R.id.expenses);
        incomes = view.findViewById(R.id.incomes);

        getTotalExpenses();

        return view;
    }

    public void getTotalExpenses() {



        final String token = PreferenceManager.getDefaultSharedPreferences(getContext()).getString(Prefs.TOKEN, "");

        Disposable disposable = ((LoftApp) getActivity().getApplication()).getMoneyApi().getBalance(token)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BalanceResponse>() {
                    @Override
                    public void accept(BalanceResponse balanceResponse) throws Exception {
                        Log.e("TAG", "Completed");

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
