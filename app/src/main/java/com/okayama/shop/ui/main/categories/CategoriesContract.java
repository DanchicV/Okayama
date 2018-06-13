package com.okayama.shop.ui.main.categories;

import com.okayama.shop.base.BasePresenter;
import com.okayama.shop.base.BaseView;
import com.okayama.shop.data.models.Category;

import java.util.List;

public class CategoriesContract {

    public interface View extends BaseView<CategoriesPresenter> {

        void setData(List<Category> categories);
    }

    public interface Presenter extends BasePresenter {

        void loadCategories();
    }
}
