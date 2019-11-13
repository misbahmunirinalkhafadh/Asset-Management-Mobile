package com.mii.assetmanagement.apihelper;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("login/{email}/{password}")
    Call<ResponseBody> loginRequest(@Path("email") String email,
                                    @Path("password") String password);
}
