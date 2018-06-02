package com.okayama.shop.ui.auth.registration;

import com.okayama.shop.base.BasePresenter;
import com.okayama.shop.base.BaseView;

public class RegistrationContract {

    public interface View extends BaseView<RegistrationPresenter> {

        void registrationSuccess();
    }

    public interface Presenter extends BasePresenter {

        void registration(final int role,
                          final String name,
                          final String password,
                          final String email,
                          final String city,
                          final String organization);

        void setAuthorized(boolean isAuthorized);
    }
}
