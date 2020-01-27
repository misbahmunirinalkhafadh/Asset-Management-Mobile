package com.mii.assetmanagement.ViewModel;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.mii.assetmanagement.Model.MaintenanceRequest;
import com.mii.assetmanagement.apihelper.ApiService;
import com.mii.assetmanagement.apihelper.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MaintenanceViewModel extends ViewModel {

    private ApiService mApiService;

    public void apiSaveMaintenance(MaintenanceRequest request) {
        mApiService = UtilsApi.getApiService();
        mApiService.saveMaintenance(request).enqueue(new Callback<MaintenanceRequest>() {
            @Override
            public void onResponse(Call<MaintenanceRequest> call, Response<MaintenanceRequest> response) {
                if (response.isSuccessful()) {
                    Log.i("post submitted to API.", response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<MaintenanceRequest> call, Throwable t) {
                Log.e("onFailure", t.getMessage());
            }
        });
    }
}
