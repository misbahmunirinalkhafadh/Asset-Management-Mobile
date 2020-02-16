package com.mii.assetmanagement.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mii.assetmanagement.BuildConfig;
import com.mii.assetmanagement.apihelper.ApiService;
import com.mii.assetmanagement.apihelper.UtilsApi;
import com.mii.assetmanagement.model.LoginResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class LoginViewModel extends ViewModel {
    private static final String API_TOKEN = BuildConfig.JWT_SAKURA_TOKEN;
    private MutableLiveData<LoginResult> liveDataUser = new MutableLiveData<>();

    public void setLiveDataUser(String email, String pass) {
        ApiService mApiService = UtilsApi.getApiServiceJwt();
        Call<LoginResult> call = mApiService.requestLogin(email, pass, API_TOKEN);
        call.enqueue(new Callback<LoginResult>() {
            @Override
            public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                Log.i(TAG, "onResponse > Login " + response.body());
                if (response.body() == null) {
                    liveDataUser.setValue(null);
                } else {
                    liveDataUser.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<LoginResult> call, Throwable t) {
                Log.e("onFailure ", t.getMessage());
            }
        });
    }

    public LiveData<LoginResult> getLiveData() {
        return liveDataUser;
    }
}
