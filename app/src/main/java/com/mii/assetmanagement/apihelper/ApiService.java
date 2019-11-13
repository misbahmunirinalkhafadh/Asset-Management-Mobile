package com.mii.assetmanagement.apihelper;

import com.mii.assetmanagement.model.LoginRequest;
import com.mii.assetmanagement.model.LoginResult;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {
    @POST("login")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<LoginResult> login(@Body LoginRequest body);
}
