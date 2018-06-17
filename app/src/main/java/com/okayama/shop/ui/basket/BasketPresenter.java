package com.okayama.shop.ui.basket;

import com.okayama.shop.OkayamaApplication;
import com.okayama.shop.base.BaseRepository;
import com.okayama.shop.data.models.Product;
import com.okayama.shop.data.repository.RepositoryImpl;
import com.okayama.shop.util.PreferenceHelper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BasketPresenter implements BasketContract.Presenter {

    private BasketContract.View view;
    private BaseRepository repository;
    private PreferenceHelper preferenceHelper;

    public void setView(BasketContract.View view) {
        this.view = view;
    }

    @Override
    public void subscribe() {
        repository = new RepositoryImpl();
        preferenceHelper = OkayamaApplication.getComponent().getPreferenceHelper();
    }

    @Override
    public List<Product> getBasket() {
        return repository.getBasket();
    }

    @Override
    public void sendBasket(String phone) {
        preferenceHelper.setPhone(phone);
        repository.createOrder(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    @Override
    public String getPhone() {
        return preferenceHelper.getPhone();
    }

    @Override
    public void unsubscribe() {
        view = null;
    }
}
