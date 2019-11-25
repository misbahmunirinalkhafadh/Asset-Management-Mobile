package com.mii.assetmanagement.apihelper;

import com.mii.assetmanagement.model.Asset;
import com.mii.assetmanagement.model.LoginRequest;
import com.mii.assetmanagement.model.LoginResult;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @POST("login")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<LoginResult> login(@Body LoginRequest body);

    @GET("maintenance/getparts/{serial}")
    Call <Asset> assetRequest(@Path("serial") String serial);
}
