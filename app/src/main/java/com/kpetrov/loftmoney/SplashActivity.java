package com.kpetrov.loftmoney;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.TextUtils;

public class SplashActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 400;                                                     //время задержки экрана заставки

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash2);
        checkToken();
    }

    private void checkToken() {
        final String token = PreferenceManager.getDefaultSharedPreferences(this).getString(Prefs.TOKEN,"");
        if (TextUtils.isEmpty(token)) {
            routeToLogin();
        } else {
            routeToMain();
        }
    }

    private void routeToMain(){

        new Handler().postDelayed(new Runnable() {                                                  //небольшая задержка логотипа для красоты
            @Override
            public void run() {
                Intent mainIntent = new Intent(SplashActivity.this,MainActivity.class);
                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);          //анимация выцветания
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    private void routeToLogin(){

        new Handler().postDelayed(new Runnable() {                                                     //небольшая задержка логотипа для красоты
            @Override
            public void run() {
                Intent loginIntent = new Intent(SplashActivity.this,LoginActivity.class);
                SplashActivity.this.startActivity(loginIntent);
                SplashActivity.this.finish();
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);             //анимация выцветания
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}