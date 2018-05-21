package com.okayama.shop.data.repository;

import com.okayama.shop.OkayamaApplication;
import com.okayama.shop.base.BaseRepository;
import com.okayama.shop.data.api.ApiService;
import com.okayama.shop.data.models.RegistrationParam;

import retrofit2.Callback;

public class RepositoryImpl implements BaseRepository {

    private ApiService apiService;

    public RepositoryImpl() {
        this.apiService = OkayamaApplication.getComponent().getApiService();
    }

    @Override
    public void registration(RegistrationParam authUserParam, Callback<Void> callback) {
        apiService.registration(authUserParam).enqueue(callback);
    }
}
