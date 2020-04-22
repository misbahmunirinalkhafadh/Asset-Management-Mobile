package com.mii.assetmanagement.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mii.assetmanagement.BuildConfig;
import com.mii.assetmanagement.apihelper.ApiService;
import com.mii.assetmanagement.apihelper.UtilsApi;
import com.mii.assetmanagement.model.Login;
import com.mii.assetmanagement.model.User;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class LoginViewModel extends ViewModel {
    private static final String API_TOKEN = BuildConfig.JWT_SAKURA_TOKEN;
    private MutableLiveData<User> liveDataUser = new MutableLiveData<>();

    public void setLiveDataUser(Login login) {
        ApiService mApiService = UtilsApi.getApiServiceJwt();
        mApiService.reqLogin(login, API_TOKEN).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.body() != null && response.body().isError()) {
                    Log.i(TAG, "onResponse > Failed");
                    liveDataUser.setValue(null);
                } else {
                    Log.i(TAG, "onResponse > Success");
                    liveDataUser.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e(TAG, "onFailure " + Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    public LiveData<User> getLiveData() {
        return liveDataUser;
    }
}
