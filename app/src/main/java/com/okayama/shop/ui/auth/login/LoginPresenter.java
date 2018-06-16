package com.okayama.shop.ui.auth.login;

import android.support.annotation.NonNull;

import com.okayama.shop.OkayamaApplication;
import com.okayama.shop.R;
import com.okayama.shop.base.BaseRepository;
import com.okayama.shop.data.repository.RepositoryImpl;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View view;
    private BaseRepository repository;

    public void setView(LoginContract.View view) {
        this.view = view;
    }

    @Override
    public void subscribe() {
        repository = new RepositoryImpl();
    }

    @Override
    public void login(final String email, final String password) {
        view.showProgress(true);
        repository.login(
                email,
                password,
                new Callback<Void>() {
                    @Override
                    public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                        view.showProgress(false);
                        if (response.code() == 200) {
                            setAuthorized(true);
                            saveEmail(email);
                            view.loginSuccess();
                            return;
                        }
                        setAuthorized(false);
                        view.showError(R.string.login_error);
                    }

                    @Override
                    public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                        view.showProgress(false);
                        view.showError(R.string.unknown_error);
                        setAuthorized(false);
                    }
                });
    }

    private void setAuthorized(boolean isAuthorized) {
        OkayamaApplication
                .getComponent()
                .getPreferenceHelper()
                .setAuthorized(isAuthorized);
    }

    private void saveEmail(String email) {
        OkayamaApplication
                .getComponent()
                .getPreferenceHelper()
                .setEmail(email);
    }

    @Override
    public void unsubscribe() {
        view = null;
    }
}
