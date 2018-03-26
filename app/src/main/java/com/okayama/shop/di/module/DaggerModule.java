package com.okayama.shop.di.module;

import android.content.Context;
import android.content.SharedPreferences;

import com.okayama.shop.BuildConfig;
import com.okayama.shop.data.api.ApiService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class DaggerModule {

    private static final String KEY_USER_SHARED_PREFERENCE = "USER_SHARED_PREFERENCE";

    private final Context context;

    public DaggerModule(Context context) {
        this.context = context;
    }

    protected SharedPreferences getSharedPreferences(){
        return context.getSharedPreferences(KEY_USER_SHARED_PREFERENCE, Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    ApiService provideRestAdapter() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.API_URL)
                .build();
        return retrofit.create(ApiService.class);
    }
}
