package com.kpetrov.loftmoney;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import android.view.ActionMode;
import android.view.View;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton floatingActionButton;

    private TabLayout mTabLayout;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTabLayout = findViewById(R.id.tabs);
        mToolbar = findViewById(R.id.toolbar);

        final ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new BudgetPagerAdapter(getSupportFragmentManager(),FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT));

        mTabLayout.setupWithViewPager(viewPager);
        mTabLayout.getTabAt(0).setText(R.string.expenses);
        mTabLayout.getTabAt(1).setText(R.string.income);

        floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                String tag;
                if (viewPager.getCurrentItem() == 0) {
                    tag = "expense";
                } else {
                    tag = "income";
                }
                startActivity(new Intent(MainActivity.this, AddItemActivity.class).putExtra("tag", tag));
            }
        });
    }

    @Override
    public void onActionModeStarted(ActionMode mode) {
        super.onActionModeStarted(mode);
        mTabLayout.setBackgroundColor(ContextCompat.getColor(this,R.color.actionModeBackground));
        mToolbar.setBackgroundColor(ContextCompat.getColor(this,R.color.actionModeBackground));
        floatingActionButton.setVisibility(View.INVISIBLE);                                          // floatingActionButton не видна
    }

    @Override
    public void onActionModeFinished(ActionMode mode) {
        super.onActionModeFinished(mode);
        mTabLayout.setBackgroundColor(ContextCompat.getColor(this,R.color.colorPrimary));
        mToolbar.setBackgroundColor(ContextCompat.getColor(this,R.color.colorPrimary));
        floatingActionButton.setVisibility(View.VISIBLE);                                            // floatingActionButton видна
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
                    return new BudgetFragment();
                case 1:
                    return new IncomeFragment();
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


