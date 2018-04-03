package com.okayama.shop.ui;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.okayama.shop.R;
import com.okayama.shop.ui.main.MainActivity;

public class SplashScreenActivity extends AppCompatActivity {

    private static final int SPLASH_DELAY = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MainActivity.start(SplashScreenActivity.this);
                finish();
            }
        }, SPLASH_DELAY);
    }
}
