package com.okayama.shop.base;

import retrofit2.Callback;

public interface BaseRepository {

    void registration(int role,
                      String name,
                      String password,
                      String email,
                      String city,
                      String organization,
                      Callback<Void> callback);

    void login(String email, String password, Callback<Void> callback);
}
