package com.mii.assetmanagement.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mii.assetmanagement.BuildConfig;
import com.mii.assetmanagement.apihelper.ApiService;
import com.mii.assetmanagement.apihelper.UtilsApi;
import com.mii.assetmanagement.model.Asset;
import com.mii.assetmanagement.model.MaintenanceRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class MaintenanceViewModel extends ViewModel {
    private static final String API_TOKEN = BuildConfig.JWT_SAKURA_TOKEN;
    private MutableLiveData<Asset> liveData = new MutableLiveData<>();
    private MutableLiveData<Asset> liveDataScan = new MutableLiveData<>();
    private ApiService mApiService;

    public void setDataAsset(String serial) {
        mApiService = UtilsApi.getApiServiceJwt();
        mApiService.assetRequest(serial, API_TOKEN).enqueue(new Callback<Asset>() {
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

    public void setDataAssetScan(String serial) {
        mApiService = UtilsApi.getApiServiceJwt();
        mApiService.assetRequest(serial, API_TOKEN).enqueue(new Callback<Asset>() {
            @Override
            public void onResponse(Call<Asset> call, Response<Asset> response) {
                Log.d("onResponse", "Asset " + response.body().toString());
                if (response.body() == null) {
                    liveDataScan.setValue(null);
                } else {
                    liveDataScan.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Asset> call, Throwable t) {
                Log.e("onFailure", t.getMessage());
            }
        });
    }

    public void setMaintenance(MaintenanceRequest request) {
        mApiService = UtilsApi.getApiServiceJwt();
        mApiService.saveMaintenance(request, API_TOKEN).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i(TAG, "onResponse > Save Maintenance" + response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("onFailure > ", t.getMessage());
            }
        });
    }

    public LiveData<Asset> getDataAsset() {
        return liveData;
    }

    public LiveData<Asset> getDataAssetScan() {
        return liveDataScan;
    }

}
