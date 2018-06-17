package com.okayama.shop;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.okayama.shop.data.api.ApiService;
import com.okayama.shop.data.dao.AppDatabase;
import com.okayama.shop.util.PreferenceHelper;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OkayamaApplication extends Application {

    private static AppComponent appComponent;

    private AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = new AppComponent();
    }

    public static AppComponent getComponent() {
        return appComponent;
    }

    public class AppComponent {

        private ApiService apiService;
        private Retrofit retrofit;

        private PreferenceHelper preferenceHelper;

        private AppComponent() {
            preferenceHelper = new PreferenceHelper(OkayamaApplication.this);

            database = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class,
                    "Product-database")
                    .allowMainThreadQueries()
                    .build();


            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    //.addNetworkInterceptor(new HeaderInterceptor())
                    .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
            apiService = retrofit.create(ApiService.class);
        }

        public ApiService getApiService() {
            return apiService;
        }

        public PreferenceHelper getPreferenceHelper() {
            return preferenceHelper;
        }

        public AppDatabase getDatabase() {
            return database;
        }
    }
}
