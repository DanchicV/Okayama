package com.okayama.shop.ui.auth.registration;

import android.support.annotation.NonNull;

import com.okayama.shop.OkayamaApplication;
import com.okayama.shop.R;
import com.okayama.shop.base.BaseRepository;
import com.okayama.shop.data.repository.RepositoryImpl;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationPresenter implements RegistrationContract.Presenter {

    private RegistrationContract.View view;
    private BaseRepository repository;

    public void setView(RegistrationContract.View view) {
        this.view = view;
    }

    @Override
    public void subscribe() {
        repository = new RepositoryImpl();
    }

    @Override
    public void registration(final int role,
                             final String name,
                             final String password,
                             final String email,
                             final String city,
                             final String organization) {
        view.showProgress(true);
        repository.registration(role,
                name,
                password,
                email,
                city,
                organization,
                new Callback<Void>() {
                    @Override
                    public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                        view.showProgress(false);
                        if (response.code() == 200) {
                            setAuthorized(true);
                            saveEmail(email);
                            view.registrationSuccess();
                            return;
                        }
                        setAuthorized(false);
                        view.showError(R.string.sign_in_error);
                    }

                    @Override
                    public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                        setAuthorized(false);
                        view.showProgress(false);
                        view.showError(R.string.unknown_error);
                    }
                });
    }

    @Override
    public void setAuthorized(boolean isAuthorized) {
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
