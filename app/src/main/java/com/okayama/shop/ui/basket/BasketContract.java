package com.okayama.shop.ui.basket;

import com.okayama.shop.base.BasePresenter;
import com.okayama.shop.base.BaseView;
import com.okayama.shop.data.models.Product;

import java.util.List;

public class BasketContract {

    public interface View extends BaseView<BasketPresenter> {

    }

    public interface Presenter extends BasePresenter {

        List<Product> getBasket();

        void sendBasket(String phone);

        String getPhone();
    }
}
