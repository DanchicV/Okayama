package com.okayama.shop.base;

import com.okayama.shop.data.models.RegistrationParam;

import retrofit2.Callback;

public interface BaseRepository {

    void registration(RegistrationParam authUserParam, Callback<Void> callback);
}
