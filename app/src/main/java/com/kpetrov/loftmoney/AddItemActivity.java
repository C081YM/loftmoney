package com.kpetrov.loftmoney;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import java.util.Objects;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class AddItemActivity extends AppCompatActivity {

    TextInputEditText etTitle;
    TextInputEditText etPrice;
    Button buttonAdd;

    String name;
    String value;

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            String changeColor = getIntent().getExtras().getString("tag");

            if (etPrice.getText() != null && etPrice.getText().toString().trim().length() > 0 && etTitle.getText() != null && etTitle.getText().toString().trim().length() > 0){
                buttonAdd.setEnabled(true);
                value = Objects.requireNonNull(etPrice.getText()).toString();
                name = Objects.requireNonNull(etTitle.getText()).toString();
                if (changeColor.equals("expense")) {
                    buttonAdd.setTextColor(getApplicationContext().getResources().getColor(R.color.colorItemPrice));
                    Drawable img = getApplicationContext().getResources().getDrawable(R.drawable.ic_arrow_expense);
                    buttonAdd.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
                } else {
                    buttonAdd.setTextColor(getApplicationContext().getResources().getColor(R.color.colorItemIncome));
                    Drawable img = getApplicationContext().getResources().getDrawable(R.drawable.ic_arrow_income);
                    buttonAdd.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
                }
            } else {
                buttonAdd.setEnabled(false);
                Drawable img = getApplicationContext().getResources().getDrawable(R.drawable.ic_arrow_disable);
                buttonAdd.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
            }
        }
        @Override
        public void afterTextChanged(Editable editable) {
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        etPrice = findViewById(R.id.editTextPrice);
        etTitle = findViewById(R.id.editTextTitle);
        buttonAdd = findViewById(R.id.add_button);

        etPrice.addTextChangedListener(textWatcher);
        etTitle.addTextChangedListener(textWatcher);

        configureExpenseAdding();

        changeColorText();
    }

    private void changeColorText() {

        String changeColor = getIntent().getExtras().getString("tag");

        if (changeColor.equals("expense"))  {
            etTitle.setTextColor(getApplicationContext().getResources().getColor(R.color.colorItemPrice));
            etPrice.setTextColor(getApplicationContext().getResources().getColor(R.color.colorItemPrice));
        } else {
            etTitle.setTextColor(getApplicationContext().getResources().getColor(R.color.colorItemIncome));
            etPrice.setTextColor(getApplicationContext().getResources().getColor(R.color.colorItemIncome));
        }
    }

    private void configureExpenseAdding () {
        buttonAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String token = getSharedPreferences(getString(R.string.app_name),0).getString(LoftApp.TOKEN_KEY,"");

                compositeDisposable.add(((LoftApp) getApplication()).getMoneyApi().addMoney(token, value, name, getIntent().getExtras().getString("tag"))
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action() {
                            @Override
                            public void run() throws Exception {
                                Log.e("TAG","Completed");
                                finish();
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Log.e("TAG", "Error" + throwable.getLocalizedMessage());
                            }
                        }));
            }
        });
    }
}
