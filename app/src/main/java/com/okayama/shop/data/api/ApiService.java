package com.okayama.shop.data.api;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {

    String REGISTRATION = "web/registration";
    String AUTHORIZATION = "web/authorization";

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
}
