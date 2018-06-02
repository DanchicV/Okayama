package com.okayama.shop.ui.auth;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.okayama.shop.R;
import com.okayama.shop.base.BaseActivity;
import com.okayama.shop.ui.auth.login.LoginFragment;
import com.okayama.shop.ui.auth.registration.RegistrationFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AuthActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, AuthActivity.class);
        context.startActivity(intent);
    }

    public static void startNewTask(Context context) {
        Intent intent = new Intent(context, AuthActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.registration_button)
    public void onRegistrationButtonClicked() {
        getSupportFragmentManager().beginTransaction()
                .add(android.R.id.content, RegistrationFragment.newInstance(), RegistrationFragment.class.getSimpleName())
                .addToBackStack(RegistrationFragment.class.getSimpleName())
                .commit();
    }

    @OnClick(R.id.open_login_button)
    public void onOpenLoginButtonClicked() {
        getSupportFragmentManager().beginTransaction()
                .add(android.R.id.content, LoginFragment.newInstance(), RegistrationFragment.class.getSimpleName())
                .addToBackStack(RegistrationFragment.class.getSimpleName())
                .commit();
    }
}

