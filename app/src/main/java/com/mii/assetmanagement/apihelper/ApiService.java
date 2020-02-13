package com.mii.assetmanagement.apihelper;

import com.mii.assetmanagement.model.Asset;
import com.mii.assetmanagement.model.LoginRequest;
import com.mii.assetmanagement.model.LoginResult;
import com.mii.assetmanagement.model.MaintenanceRequest;
import com.mii.assetmanagement.model.SalesOrder;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    /**
     * Login
     *
     * @param body
     * @return
     */
    @POST("login")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<LoginResult> login(@Body LoginRequest body);

    /**
     * Maintenance Save
     *
     * @param body
     * @return
     */
    @POST("maintenance/save")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<String> saveMaintenance(@Body MaintenanceRequest body,
                                             @Header("Authorization") String token);

    /**
     * @param serial
     * @return
     */
    @GET("maintenance/getparts")
    @Headers({"Content-Type: application/json"})
    Call<Asset> assetRequest(@Query("serial_number") String serial,
                                   @Header("Authorization") String token);

    /**
     * Search Sales order
     * JWT
     *
     * @param sonumber
     * @return
     */
    @GET("salesorder")
    @Headers({"Content-Type: application/json"})
    Call<SalesOrder> getSalesOrder(@Query("so_number") String sonumber,
                                        @Header("Authorization") String token);
}
