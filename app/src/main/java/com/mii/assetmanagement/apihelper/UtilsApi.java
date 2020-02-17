package com.mii.assetmanagement.apihelper;

public class UtilsApi {

    static final String BASE_URL_API = "http://116.254.101.228:8080/APIMOBILE/";
    static final String BASE_URL_API_JWT = "http://116.254.101.228:8080/APIMOBILEJWT/";
    static final String BASE_URL_API_SAKURA_JWT = "http://116.254.101.228:8080/APISAKURAJWT/";

    public static ApiService getApiService(){
        return RetrofitClient.getClient().create(ApiService.class);
    }

    public static ApiService getApiServiceJwt(){
        return RetrofitClient.getClientJwt().create(ApiService.class);
    }

    public static ApiService getApiServiceSakuraJwt(){
        return RetrofitClient.getClientSakuraJwt().create(ApiService.class);
    }
}
