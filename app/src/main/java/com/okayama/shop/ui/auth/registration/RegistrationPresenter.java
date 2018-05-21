package com.okayama.shop.ui.auth.registration;

import android.support.annotation.NonNull;

import com.okayama.shop.OkayamaApplication;
import com.okayama.shop.R;
import com.okayama.shop.base.BaseRepository;
import com.okayama.shop.data.models.RegistrationParam;
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
        RegistrationParam authUserParam = new RegistrationParam(role, name, email, password, city, organization);
        view.showProgress(true);
        repository.registration(authUserParam, new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                view.showProgress(false);
                if (response.code() == 200) {
                    setAuthorized(true);
                    view.registrationSuccess();
                    return;
                }
                view.showError(R.string.sign_in_error);
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
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

    @Override
    public void unsubscribe() {
        view = null;
    }
}
