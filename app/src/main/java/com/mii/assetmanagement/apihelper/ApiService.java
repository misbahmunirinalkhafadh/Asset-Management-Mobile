package com.mii.assetmanagement.apihelper;

import com.mii.assetmanagement.model.Asset;
import com.mii.assetmanagement.model.Brand;
import com.mii.assetmanagement.model.Employee;
import com.mii.assetmanagement.model.LoginResult;
import com.mii.assetmanagement.model.MaintenanceRequest;
import com.mii.assetmanagement.model.SalesOrder;

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
    Call<LoginResult> requestLogin(@Field("email") String email,
                                   @Field("password") String password,
                                   @Header("Authorization") String token);

    @POST("maintenance/save")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<String> saveMaintenance(@Body MaintenanceRequest body,
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
    Call<Employee> getEmployee(@Query("id") int nik,
                                 @Header("Authorization") String token);

    @GET("brand")
    @Headers({"Content-Type: application/json"})
    Call<Brand> getBrand(@Query("name") String brand,
                         @Header("Authorization") String token);

}
