package com.mii.assetmanagement.apihelper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class RetrofitClient {
    private static Retrofit retrofitJwt = null;
    private static Retrofit retrofitSakuraJwt = null;

    static Retrofit getClientJwt() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(interceptor).build();

        if (retrofitJwt == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            retrofitJwt = new Retrofit.Builder()
                    .baseUrl(UtilsApi.BASE_URL_API_JWT)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(client)
                    .build();
        }
        return retrofitJwt;
    }

    static Retrofit getClientSakuraJwt() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(interceptor).build();

        if (retrofitSakuraJwt == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            retrofitSakuraJwt = new Retrofit.Builder()
                    .baseUrl(UtilsApi.BASE_URL_API_SAKURA_JWT)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(client)
                    .build();
        }
        return retrofitSakuraJwt;
    }
}
