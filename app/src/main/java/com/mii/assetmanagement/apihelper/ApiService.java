package com.mii.assetmanagement.apihelper;

import com.mii.assetmanagement.Model.Asset;
import com.mii.assetmanagement.Model.LoginRequest;
import com.mii.assetmanagement.Model.LoginResult;
import com.mii.assetmanagement.Model.MaintenanceRequest;

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

    @POST("maintenance/save")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<MaintenanceRequest> saveMaintenance(@Body MaintenanceRequest body);

    @GET("maintenance/getparts/{serial}")
    Call<Asset> assetRequest(@Path("serial") String serial);
}
