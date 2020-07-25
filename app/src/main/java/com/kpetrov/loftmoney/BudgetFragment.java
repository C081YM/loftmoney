package com.kpetrov.loftmoney;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Objects;

public class BudgetFragment extends Fragment {

    public static final int REQUEST_CODE = 20;
    public static final String COLOR_CHOICE = "colorChoice";
    private ItemsAdapter adapter;

    public static BudgetFragment newInstance (final int colorChoice) {
        BudgetFragment budgetFragment = new BudgetFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(COLOR_CHOICE, colorChoice);
        budgetFragment.setArguments(bundle);
        return budgetFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_budget,null);

        Button plusButton = view.findViewById(R.id.plusButton);
        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
            startActivityForResult(new Intent(getActivity(), AddItemActivity.class), REQUEST_CODE);
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(Objects.requireNonNull(getActivity()),DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.recyclerview_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);

        adapter = new ItemsAdapter(getArguments().getInt(COLOR_CHOICE));
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,@Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        int price;
        try {
            price=Integer.parseInt(data.getStringExtra("price"));

        } catch (NumberFormatException e) {
            price = 0;
        }

        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            adapter.addData(new Item(data.getStringExtra("name"),price));
        }
    }
}
