package com.okayama.shop.base;

import com.okayama.shop.data.models.Category;
import com.okayama.shop.data.models.Product;

import java.util.List;

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

    void getCategories(Callback<List<Category>> callback);

    void getProducts(long id, Callback<List<Product>> callback);
}
