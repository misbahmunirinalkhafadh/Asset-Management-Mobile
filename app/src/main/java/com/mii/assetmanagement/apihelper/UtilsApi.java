package com.mii.assetmanagement.apihelper;

public class UtilsApi {
    public static final String BASE_URL_API = "http://116.254.101.228:8080/APIMOBILE/";
    public static final String URL_GET_EMP = "http://dummy.restapiexample.com/api/v1/";

    public static ApiService getApiService(){
        return RetrofitClient.getClient(BASE_URL_API).create(ApiService.class);
    }

    public static ApiService getEmployee(){
        return RetrofitClient.getClient(URL_GET_EMP).create((ApiService.class));
    }
}
