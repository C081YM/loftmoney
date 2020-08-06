package com.kpetrov.loftmoney;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash2);
        checkToken();
    }

    @Override                                                                                          // анимация выцветания
    protected void onPause() {
        super.onPause();
        if (isFinishing()) {
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        }
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
        Intent mainIntent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(mainIntent);
    }

    private void routeToLogin(){
        Intent loginIntent = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(loginIntent);
    }
}