package com.kpetrov.loftmoney;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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
            if (etPrice.getText() != null && etPrice.getText().toString().trim().length() > 0 && etTitle.getText() != null && etTitle.getText().toString().trim().length() > 0){
                buttonAdd.setEnabled(true);
                buttonAdd.setTextColor(getApplicationContext().getResources().getColor(R.color.addItemActivity_buttonAdd_colorEnable));
                Drawable img = getApplicationContext().getResources().getDrawable(R.drawable.ic_arrow_enable);
                buttonAdd.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
                value = Objects.requireNonNull(etPrice.getText()).toString();
                name = Objects.requireNonNull(etTitle.getText()).toString();
            } else {
                buttonAdd.setEnabled(false);
                buttonAdd.setTextColor(getApplicationContext().getResources().getColor(R.color.addItemActivity_buttonAdd_colorDisable));
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

        //configureInputViews();
        configureExpenseAdding();

        /*buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                String name = Objects.requireNonNull(etTitle.getText()).toString();
                String price = Objects.requireNonNull(etPrice.getText()).toString();

                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(price)) {
                    setResult(
                            RESULT_OK,
                            new Intent().putExtra("name", name).putExtra("price", price));
                    finish();
                }
            }
        });*/
    }

    private void configureExpenseAdding () {
        buttonAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                compositeDisposable.add(((LoftApp) getApplication()).getMoneyApi().addMoney(value, name, "expense")
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

    /*private void configureInputViews(){
        etTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                name = Objects.requireNonNull(etTitle.getText()).toString();
                checkInputs();
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        etPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                value = Objects.requireNonNull(etPrice.getText()).toString();
                checkInputs();
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void checkInputs() {
        boolean isEnabled = value != null && !value.isEmpty() && name != null && !name.isEmpty();

        buttonAdd.setEnabled(isEnabled);
        buttonAdd.setTextColor(ContextCompat.getColor(getApplicationContext(), isEnabled ? R.color.addItemActivity_buttonAdd_colorEnable : R.color.addItemActivity_buttonAdd_colorDisable));

    }*/
}
