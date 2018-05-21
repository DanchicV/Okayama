package com.okayama.shop.ui.splash_screen;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.okayama.shop.OkayamaApplication;
import com.okayama.shop.R;
import com.okayama.shop.ui.auth.AuthActivity;
import com.okayama.shop.ui.main.MainActivity;

import java.util.concurrent.TimeUnit;

public class SplashScreenActivity extends AppCompatActivity {

    private static final long SPLASH_DELAY = TimeUnit.SECONDS.toMillis(3);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean isAuthorized = OkayamaApplication.getComponent()
                        .getPreferenceHelper()
                        .isAuthorized();
                if (isAuthorized) {
                    MainActivity.start(SplashScreenActivity.this);
                } else {
                    AuthActivity.startNewTask(SplashScreenActivity.this);
                }
                finish();
            }
        }, SPLASH_DELAY);
    }
}

