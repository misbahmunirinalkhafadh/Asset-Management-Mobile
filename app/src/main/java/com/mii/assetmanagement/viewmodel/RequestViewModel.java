package com.mii.assetmanagement.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mii.assetmanagement.BuildConfig;
import com.mii.assetmanagement.apihelper.ApiService;
import com.mii.assetmanagement.apihelper.UtilsApi;
import com.mii.assetmanagement.model.SalesOrder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestViewModel extends ViewModel {

    private static final String API_TOKEN = BuildConfig.JWT_SAKURA_TOKEN;
    private MutableLiveData<SalesOrder> liveData = new MutableLiveData<>();

    public void setDataSO(String soNumber) {
        ApiService mApiService = UtilsApi.getApiServiceJwt();
        Call<SalesOrder> call = mApiService.getSalesOrder(soNumber, API_TOKEN);
        call.enqueue(new Callback<SalesOrder>() {
            @Override
            public void onResponse(Call<SalesOrder> call, Response<SalesOrder> response) {
                if (response.body() == null) {
                    liveData.setValue(null);
                } else {
                    liveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<SalesOrder> call, Throwable t) {
                Log.e("onFailure", t.getMessage());
            }
        });
    }

    public LiveData<SalesOrder> getDataSO() {
        return liveData;
    }
}
