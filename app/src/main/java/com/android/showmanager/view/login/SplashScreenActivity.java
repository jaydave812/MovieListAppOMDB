package com.android.showmanager.view.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.android.showmanager.R;
import com.android.showmanager.utils.MySharedPrefs;
import com.android.showmanager.view.list.ShowListActivity;

public class SplashScreenActivity extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 1000;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splesh_screen_actvity);
        getSupportActionBar().hide();
        sharedPreferences = getSharedPreferences(MySharedPrefs.sharedPref, Context.MODE_PRIVATE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                /* Create an Intent that will start the Menu-Activity. */

                if (sharedPreferences.getBoolean("first_run", true)) {
                    startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
                } else {
                    if (sharedPreferences.getBoolean("is_logged_out", false)) {
                        startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
                    } else {
                        startActivity(new Intent(SplashScreenActivity.this, ShowListActivity.class));
                    }
                }
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}