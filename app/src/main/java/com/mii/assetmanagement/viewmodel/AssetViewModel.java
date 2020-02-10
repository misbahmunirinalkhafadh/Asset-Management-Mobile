package com.mii.assetmanagement.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mii.assetmanagement.BuildConfig;
import com.mii.assetmanagement.apihelper.ApiService;
import com.mii.assetmanagement.apihelper.UtilsApi;
import com.mii.assetmanagement.model.Asset;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssetViewModel extends ViewModel {

    private static final String API_TOKEN = BuildConfig.JWT_SAKURA_TOKEN;
    private MutableLiveData<Asset> liveData = new MutableLiveData<>();

    public void setDataAsset(String serial) {
        ApiService mApiService = UtilsApi.getApiServiceJwt();
        Call<Asset> call = mApiService.assetRequest(serial, API_TOKEN);
        call.enqueue(new Callback<Asset>() {
            @Override
            public void onResponse(Call<Asset> call, Response<Asset> response) {
                Log.d("onResponse", "Asset " + response.body().toString());
                if (response.body() == null) {
                    liveData.setValue(null);
                } else {
                    liveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Asset> call, Throwable t) {
                Log.e("onFailure", t.getMessage());
            }
        });
    }

    public LiveData<Asset> getDataAsset() {
        return liveData;
    }
}
