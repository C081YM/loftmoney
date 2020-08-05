package com.kpetrov.loftmoney;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class BudgetFragment extends Fragment implements ItemsAdapterListener, ActionMode.Callback {

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    SwipeRefreshLayout swipeRefreshLayout;

    ItemsAdapter adapter;

    private ActionMode mActionMode;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_budget, null);

        adapter = new ItemsAdapter();

        adapter.setListener(this);

        RecyclerView recyclerView = view.findViewById(R.id.recycler);

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                generateExpense();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(Objects.requireNonNull(getActivity()), DividerItemDecoration.VERTICAL);
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

        final String token = PreferenceManager.getDefaultSharedPreferences(getContext()).getString(Prefs.TOKEN,"");

        Disposable disposable = ((LoftApp) getActivity().getApplication()).getMoneyApi().getMoney(token,"expense")
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(new Action() {
                    @Override
                    public void run() {
                        Log.e("TAG", "Completed generate expenses");
                        swipeRefreshLayout.setRefreshing(false);
                        adapter.sortArrayList();                                                     //вызов сортировки
                    }
                })
                .subscribe(new Consumer<List<MoneyItem>>() {
                    @Override
                    public void accept(List<MoneyItem> moneyItems) throws Exception {
                        adapter.clearItems();
                        for (MoneyItem moneyItem : moneyItems) {
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


    @Override
    public void onItemClick(final Item item, int position) {
        adapter.clearItem(position);
        if (mActionMode != null) {
            mActionMode.setTitle(getString(R.string.selected, String.valueOf(adapter.getSelectedSize())));
        }
    }

    @Override
    public void onItemLongClick(final Item item, int position) {
        if (mActionMode == null) {
            getActivity().startActionMode(this);
        }
        adapter.toggleItem(position);
        if (mActionMode != null) {
            mActionMode.setTitle(getString(R.string.selected, String.valueOf(adapter.getSelectedSize())));
        }
    }

    @Override
    public boolean onCreateActionMode(final ActionMode actionMode, final Menu menu) {
        mActionMode = actionMode;
        return true;
    }

    @Override
    public boolean onPrepareActionMode(final ActionMode actionMode, final Menu menu) {
        MenuInflater menuInflater = new MenuInflater(getActivity());
        menuInflater.inflate(R.menu.menu_delete, menu);
        return true;
    }

    @Override
    public boolean onActionItemClicked(final ActionMode actionMode, final MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.remove){
            new AlertDialog.Builder(getContext())
                    .setMessage(R.string.confirmation)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            removeItems();
                            actionMode.finish();
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    }).show();
        }
        return true;
    }

    private void removeItems() {
        final String token = PreferenceManager.getDefaultSharedPreferences(getContext()).getString(Prefs.TOKEN,"");
        List<Integer> selectedItems = adapter.getSelectedItemsId();
        for (Integer itemId : selectedItems) {
            Disposable disposable = ((LoftApp) getActivity().getApplication()).getMoneyApi().removeItem(String.valueOf(itemId.intValue()), token)
                 .subscribeOn(Schedulers.computation())
                 .observeOn(AndroidSchedulers.mainThread())
                 .subscribe(new Action() {
                     @Override
                     public void run() throws Exception {
                         Log.e("TAG", "Completed remove expenses");
                         generateExpense();
                         adapter.clearSelections();
                     }
                 }, new Consumer<Throwable>() {
                     @Override
                     public void accept(Throwable throwable) throws Exception {
                         Log.e("TAG", "Error" + throwable.getLocalizedMessage());
                     }
                 });
        compositeDisposable.add(disposable);
        }
    }

    @Override
    public void onDestroyActionMode(final ActionMode actionMode) {
        mActionMode = null;
        adapter.clearSelections();
    }
}