package com.kpetrov.loftmoney;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container, new BudgetFragment());
        transaction.commit();

            TabLayout tabLayout = findViewById(R.id.tabs);
            tabLayout.addTab(tabLayout.newTab().setText(R.string.expenses));
            tabLayout.addTab(tabLayout.newTab().setText(R.string.income));
            tabLayout.addTab(tabLayout.newTab().setText(R.string.balance));

    }
}



