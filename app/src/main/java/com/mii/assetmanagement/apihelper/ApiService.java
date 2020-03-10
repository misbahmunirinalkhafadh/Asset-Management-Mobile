package com.mii.assetmanagement.apihelper;

import com.mii.assetmanagement.model.Asset;
import com.mii.assetmanagement.model.AssetResult;
import com.mii.assetmanagement.model.EmployeeResult;
import com.mii.assetmanagement.model.ExchangeRequest;
import com.mii.assetmanagement.model.History;
import com.mii.assetmanagement.model.HistoryDetail;
import com.mii.assetmanagement.model.MaintenanceRequest;
import com.mii.assetmanagement.model.SalesOrder;
import com.mii.assetmanagement.model.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @FormUrlEncoded
    @POST("login/data")
    Call<User> requestLogin(@Field("email") String email,
                            @Field("password") String password,
                            @Header("Authorization") String token);

    @POST("maintenance/save")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<String> saveMaintenance(@Body MaintenanceRequest body,
                                 @Header("Authorization") String token);

    @POST("exchange/saveuser")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<String> saveExchangeEmpl(@Body ExchangeRequest body,
                                  @Header("Authorization") String token);

    @POST("exchange/saveasset")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<String> saveExchangeAsset(@Body ExchangeRequest body,
                                   @Header("Authorization") String token);

    @GET("maintenance/getparts")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<Asset> assetRequest(@Query("serial_number") String serial,
                             @Header("Authorization") String token);

    @GET("salesorder")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<SalesOrder> getSalesOrder(@Query("so_number") String sonumber,
                                   @Header("Authorization") String token);

    @GET("getEmployeebynik")
    @Headers({"Content-Type: application/json"})
    Call<EmployeeResult> getEmployee(@Query("id") int nik,
                                     @Header("Authorization") String token);

    @GET("assetrequest/brand")
    @Headers({"Content-Type: application/json"})
    Call<List<AssetResult>> getBrand(@Query("name") String brand,
                                     @Header("Authorization") String token);

    //    @GET("assetrequest/category")
//    @Headers({"Content-Type: application/json"})
//    Call<> getBrand(@Query("name") String brand,
//                               @Header("Authorization") String token);

    @GET("assetrequest/gethistoryrequestbynik")
    @Headers({"Content-Type: application/json"})
    Call<History> getHistory(@Query("id") int nik,
                             @Query("status") String status,
                             @Header("Authorization") String token);

    @GET("assetrequest/getdetailrequestbyrequestid")
    @Headers({"Content-Type: application/json"})
    Call<List<HistoryDetail>> getHistoryDetail(@Query("id") int nik,
                                                    @Header("Authorization") String token);
}
