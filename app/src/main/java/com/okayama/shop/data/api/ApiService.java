package com.okayama.shop.data.api;

import com.okayama.shop.data.models.Category;
import com.okayama.shop.data.models.Product;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiService {

    String PATH_ID = "id";

    String REGISTRATION = "web/registration";
    String AUTHORIZATION = "web/authorization";
    String GET_CATEGORIES = "web/getCategories";
    String GET_PRODUCTS = "web/getElements/{" + PATH_ID + "}";

    String ROLE = "role";
    String NAME = "name";
    String EMAIL = "email";
    String PASSWORD = "password";
    String CITY = "city";
    String ORGANIZATION = "organization";

    @Multipart
    @POST(REGISTRATION)
    Call<Void> registration(@Part(ROLE) RequestBody role,
                            @Part(NAME) RequestBody name,
                            @Part(EMAIL) RequestBody email,
                            @Part(PASSWORD) RequestBody password,
                            @Part(CITY) RequestBody city,
                            @Part(ORGANIZATION) RequestBody organization);

    @Multipart
    @POST(AUTHORIZATION)
    Call<Void> authorization(@Part(EMAIL) RequestBody email,
                             @Part(PASSWORD) RequestBody password);

    @GET(GET_CATEGORIES)
    Call<List<Category>> getCategories();

    @GET(GET_PRODUCTS)
    Call<List<Product>> getProducts(@Path(PATH_ID) long id);
}
