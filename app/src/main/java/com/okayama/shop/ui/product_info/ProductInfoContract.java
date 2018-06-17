package com.okayama.shop.ui.product_info;

import com.okayama.shop.base.BasePresenter;
import com.okayama.shop.base.BaseView;

public class ProductInfoContract {

    public interface View extends BaseView<ProductInfoPresenter> {


    }

    public interface Presenter extends BasePresenter {

        void loadProducts(long id);
    }
}
