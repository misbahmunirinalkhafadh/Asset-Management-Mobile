package com.mii.assetmanagement.apihelper;

public class UtilsApi {
    public static final String BASE_URL_API = "http://116.254.101.228:8080/APIMOBILE/";

    // Mendeklarasikan Interface BaseApiService
    public static ApiService getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(ApiService.class);
    }
}
