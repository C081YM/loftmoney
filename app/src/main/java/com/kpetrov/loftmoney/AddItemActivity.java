package com.kpetrov.loftmoney;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import java.util.Objects;

public class AddItemActivity extends AppCompatActivity {

    private TextInputEditText etTitle;
    private TextInputEditText etPrice;
    private Button buttonAdd;

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {

            if (etPrice.getText() != null && etPrice.getText().toString().trim().length() > 0 && etTitle.getText() != null && etTitle.getText().toString().trim().length() > 0){
                buttonAdd.setEnabled(true);
                buttonAdd.setTextColor(getApplicationContext().getResources().getColor(R.color.addItemActivity_buttonAdd_colorEnable));
                Drawable img = getApplicationContext().getResources().getDrawable(R.drawable.ic_arrow_enable);
                buttonAdd.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
            } else {
                buttonAdd.setEnabled(false);
                buttonAdd.setTextColor(getApplicationContext().getResources().getColor(R.color.addItemActivity_buttonAdd_colorDisable));
                Drawable img = getApplicationContext().getResources().getDrawable(R.drawable.ic_arrow_disable);
                buttonAdd.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
            }
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

        buttonAdd.setOnClickListener(new View.OnClickListener() {
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
        });
    }
}
