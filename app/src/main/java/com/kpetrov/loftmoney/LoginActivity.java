package com.kpetrov.loftmoney;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.util.Random;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {

    private Button loginButtonView;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButtonView = findViewById(R.id.loginButtonView);

        configureButton();
    }

    @Override                                                                                          // анимация выцветания
    protected void onPause() {
        super.onPause();
        if (isFinishing()) {
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        }
    }

    private void configureButton() {
        loginButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String socialUserId = String.valueOf(new Random().nextInt());
                compositeDisposable.add(((LoftApp) getApplication()).getAuthApi().performLogin(socialUserId)
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<AuthResponse>() {
                            @Override
                            public void accept(AuthResponse authResponse) throws Exception {
                                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this).edit();
                                editor.putString(Prefs.TOKEN, authResponse.getAccessToken());
                                editor.apply();

                                Intent mainIntent = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(mainIntent);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Toast.makeText(getApplicationContext(),throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }));
            }
        });
    }
}