package com.okayama.shop.ui.main.categories;

import android.support.annotation.NonNull;

import com.okayama.shop.R;
import com.okayama.shop.base.BaseRepository;
import com.okayama.shop.data.models.Category;
import com.okayama.shop.data.repository.RepositoryImpl;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriesPresenter implements CategoriesContract.Presenter {

    private CategoriesContract.View view;
    private BaseRepository repository;

    public void setView(CategoriesContract.View view) {
        this.view = view;
    }

    @Override
    public void subscribe() {
        repository = new RepositoryImpl();
    }

    @Override
    public void loadCategories() {
        view.showProgress(true);
        repository.getCategories(new Callback<List<Category>>() {
            @Override
            public void onResponse(@NonNull Call<List<Category>> call,
                                   @NonNull Response<List<Category>> response) {
                view.showProgress(false);
                if (response.code() == 200) {
                    view.setData(response.body());
                    return;
                }
                view.showError(R.string.loading_error);
            }

            @Override
            public void onFailure(@NonNull Call<List<Category>> call,
                                  @NonNull Throwable t) {
                view.showProgress(false);
                view.showError(R.string.unknown_error);

            }
        });
    }

    @Override
    public void unsubscribe() {
        view = null;
    }
}
