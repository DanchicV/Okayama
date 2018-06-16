package com.okayama.shop.ui.product;

import com.okayama.shop.base.BasePresenter;
import com.okayama.shop.base.BaseView;
import com.okayama.shop.data.models.Product;

import java.util.List;

public class ProductContract {

    public interface View extends BaseView<ProductPresenter> {

        void setData(List<Product> categories);
    }

    public interface Presenter extends BasePresenter {

        void loadProducts(long id);
    }
}
