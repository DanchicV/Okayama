package com.okayama.shop.ui.auth.login;

import com.okayama.shop.base.BasePresenter;
import com.okayama.shop.base.BaseView;

public class LoginContract {

    public interface View extends BaseView<LoginPresenter> {

        void loginSuccess();
    }

    public interface Presenter extends BasePresenter {

        void login(final String email,final String password);
    }
}
