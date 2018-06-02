package com.okayama.shop.data.repository;

import com.okayama.shop.OkayamaApplication;
import com.okayama.shop.base.BaseRepository;
import com.okayama.shop.data.api.ApiService;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Callback;

public class RepositoryImpl implements BaseRepository {

    private ApiService apiService;

    public RepositoryImpl() {
        this.apiService = OkayamaApplication.getComponent().getApiService();
    }

    @Override
    public void registration(int role,
                             String name,
                             String password,
                             String email,
                             String city,
                             String organization,
                             Callback<Void> callback) {
        apiService.registration(
                RequestBody.create(MediaType.parse("text/plain"), String.valueOf(role)),
                RequestBody.create(MediaType.parse("text/plain"), name),
                RequestBody.create(MediaType.parse("text/plain"), email),
                RequestBody.create(MediaType.parse("text/plain"), password),
                RequestBody.create(MediaType.parse("text/plain"), city),
                RequestBody.create(MediaType.parse("text/plain"), organization)
        ).enqueue(callback);
    }

    @Override
    public void login(String email, String password,
                      Callback<Void> callback) {
        apiService.authorization(
                RequestBody.create(MediaType.parse("text/plain"), email),
                RequestBody.create(MediaType.parse("text/plain"), password)
        ).enqueue(callback);
    }
}
