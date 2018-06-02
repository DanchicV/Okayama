package com.okayama.shop.data.api;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {

    private static final String CONTENT_TYPE = "Content-Type";
    private static final String MULTIPART_FROM_DATA = "multipart/form-data";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalResponse = chain.request();
        Response response = chain.proceed(originalResponse.newBuilder()
                .header(CONTENT_TYPE, MULTIPART_FROM_DATA)
                .build());
        return response;
    }
}
