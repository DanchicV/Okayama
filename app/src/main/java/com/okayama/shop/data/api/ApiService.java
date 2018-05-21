package com.okayama.shop.data.api;

import com.okayama.shop.data.models.RegistrationParam;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    String REGISTRATION = "web/registration";

    @POST(REGISTRATION)
    Call<Void> registration(@Body RegistrationParam param);
}
