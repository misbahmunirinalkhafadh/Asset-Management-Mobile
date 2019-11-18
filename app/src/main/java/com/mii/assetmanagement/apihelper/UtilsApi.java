package com.mii.assetmanagement.apihelper;

public class UtilsApi {
    public static final String BASE_URL_API = "http://116.254.101.228:8080/APIMOBILE/";

    public static ApiService getApiService(){
        return RetrofitClient.getClient(BASE_URL_API).create(ApiService.class);
    }
}
