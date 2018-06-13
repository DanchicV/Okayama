package com.okayama.shop.ui.main.product;

import android.support.annotation.NonNull;

import com.okayama.shop.R;
import com.okayama.shop.base.BaseRepository;
import com.okayama.shop.data.models.Product;
import com.okayama.shop.data.repository.RepositoryImpl;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductPresenter implements ProductContract.Presenter {

    private ProductContract.View view;
    private BaseRepository repository;

    public void setView(ProductContract.View view) {
        this.view = view;
    }

    @Override
    public void subscribe() {
        repository = new RepositoryImpl();
    }

    @Override
    public void loadProducts(long id) {
        view.showProgress(true);
        repository.getProducts(id, new Callback<List<Product>>() {
            @Override
            public void onResponse(@NonNull Call<List<Product>> call,
                                   @NonNull Response<List<Product>> response) {
                view.showProgress(false);
                if (response.code() == 200) {
                    view.setData(response.body());
                    return;
                }
                view.showError(R.string.loading_error);
            }

            @Override
            public void onFailure(@NonNull Call<List<Product>> call,
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
