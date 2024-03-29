package com.mii.assetmanagement.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mii.assetmanagement.BuildConfig;
import com.mii.assetmanagement.apihelper.ApiService;
import com.mii.assetmanagement.apihelper.UtilsApi;
import com.mii.assetmanagement.model.EmployeeResult;
import com.mii.assetmanagement.model.AssetRequest;
import com.mii.assetmanagement.model.SalesOrder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestViewModel extends ViewModel {
    private static final String API_TOKEN = BuildConfig.JWT_SAKURA_TOKEN;
    private MutableLiveData<SalesOrder> liveDataSO = new MutableLiveData<>();
    private MutableLiveData<EmployeeResult> liveDataEmpl = new MutableLiveData<>();

    public void setDataSO(String soNumber) {
        ApiService mApiService = UtilsApi.getApiServiceJwt();
        mApiService.getSalesOrder(soNumber, API_TOKEN).enqueue(new Callback<SalesOrder>() {
            @Override
            public void onResponse(Call<SalesOrder> call, Response<SalesOrder> response) {
                if (response.body() == null) {
                    liveDataSO.setValue(null);
                } else {
                    liveDataSO.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<SalesOrder> call, Throwable t) {
                Log.e("onFailure", t.getMessage());
            }
        });
    }

    public void setDataEmpl(int nik) {
        ApiService mApiService = UtilsApi.getApiServiceSakuraJwt();
        mApiService.getEmployee(nik, API_TOKEN).enqueue(new Callback<EmployeeResult>() {
            @Override
            public void onResponse(Call<EmployeeResult> call, Response<EmployeeResult> response) {
                Log.v("", "Test" + response.body());
                if (response.body() == null) {
                    liveDataEmpl.setValue(null);
                } else {
                    liveDataEmpl.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<EmployeeResult> call, Throwable t) {
                Log.e("onFailure", t.getMessage());
            }
        });
    }

    public void saveDataRequestAsset(AssetRequest assetRequest){
        ApiService mApiService = UtilsApi.getApiServiceJwt();
        mApiService.saveRequestAsset(assetRequest, API_TOKEN).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("Save Request Asset", response.message());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("onFailure ", t.getMessage());
            }
        });
    }

    public LiveData<SalesOrder> getDataSO() {
        return liveDataSO;
    }

    public LiveData<EmployeeResult> getDataEmployee() {
        return liveDataEmpl;
    }

}
