package com.mii.assetmanagement.apihelper;

import com.mii.assetmanagement.BuildConfig;
import com.mii.assetmanagement.model.Asset;
import com.mii.assetmanagement.model.LoginRequest;
import com.mii.assetmanagement.model.LoginResult;
import com.mii.assetmanagement.model.MaintenanceRequest;
import com.mii.assetmanagement.model.SalesOrder;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

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
    Call<MaintenanceRequest> saveMaintenance(@Body MaintenanceRequest body);

    /**
     * @param serial
     * @return
     */
    @GET("maintenance/getparts/{serial}")
    Call<Asset> assetRequest(@Path("serial") String serial);

    /**
     * Search Sales order
     *
     * @param sonumber
     * @return
     */
    @GET("allocation/{sonumber}")
    @Headers({"Authorization : " + BuildConfig.JWT_SAKURA_TOKEN})
    Call<SalesOrder> srcSalesOrder(@Path("sonumber") String sonumber);
}
