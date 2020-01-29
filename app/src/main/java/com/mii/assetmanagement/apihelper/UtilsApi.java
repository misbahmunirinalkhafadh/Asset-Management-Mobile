package com.mii.assetmanagement.apihelper;

public class UtilsApi {
    public static final String BASE_URL_API = "http://116.254.101.228:8080/APIMOBILE/";
    public static final String BASE_URL_API_JWT = "http://116.254.101.228:8080/APISAKURAJWT/";

    public static ApiService getApiService(){
        return RetrofitClient.getClient(BASE_URL_API).create(ApiService.class);
    }

    public  static ApiService getApiServiceJwt(){
        return RetrofitClient.getClient(BASE_URL_API_JWT).create(ApiService.class);
    }
}
