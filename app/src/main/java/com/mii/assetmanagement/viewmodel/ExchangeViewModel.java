package com.mii.assetmanagement.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mii.assetmanagement.BuildConfig;
import com.mii.assetmanagement.apihelper.ApiService;
import com.mii.assetmanagement.apihelper.UtilsApi;
import com.mii.assetmanagement.model.Asset;
import com.mii.assetmanagement.model.EmployeeResult;
import com.mii.assetmanagement.model.ExchangeRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExchangeViewModel extends ViewModel {

    private static final String API_TOKEN = BuildConfig.JWT_SAKURA_TOKEN;
    private MutableLiveData<Asset> liveDataAssetInput = new MutableLiveData<>();
    private MutableLiveData<Asset> liveDataAssetScan = new MutableLiveData<>();
    private MutableLiveData<EmployeeResult> liveDataEmpl = new MutableLiveData<>();
    private ApiService mApiService;

    public void setDataAssetInput(String serial) {
        mApiService = UtilsApi.getApiServiceJwt();
        Call<Asset> call = mApiService.assetRequest(serial, API_TOKEN);
        call.enqueue(new Callback<Asset>() {
            @Override
            public void onResponse(Call<Asset> call, Response<Asset> response) {
                Log.d("onResponse", "Asset " + response.body().toString());
                if (response.body() == null) {
                    liveDataAssetInput.setValue(null);
                } else {
                    liveDataAssetInput.setValue(response.body());
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
        Call<Asset> call = mApiService.assetRequest(serial, API_TOKEN);
        call.enqueue(new Callback<Asset>() {
            @Override
            public void onResponse(Call<Asset> call, Response<Asset> response) {
                Log.d("onResponse", "Asset " + response.body().toString());
                if (response.body() == null) {
                    liveDataAssetScan.setValue(null);
                } else {
                    liveDataAssetScan.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Asset> call, Throwable t) {
                Log.e("onFailure", t.getMessage());
            }
        });
    }

    public void setDataEmpl(int nik) {
        mApiService = UtilsApi.getApiServiceSakuraJwt();
        Call<EmployeeResult> call = mApiService.getEmployee(nik, API_TOKEN);
        call.enqueue(new Callback<EmployeeResult>() {
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

    public void saveExchangeEmpl(ExchangeRequest request) {
        mApiService = UtilsApi.getApiServiceJwt();
        Call<String> call = mApiService.saveExchangeEmpl(request, API_TOKEN);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("Save exchange employee ", response.message());

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("onFailure ", t.getMessage());
            }
        });
    }

    public void saveExchangeAsset(ExchangeRequest request) {
        mApiService = UtilsApi.getApiServiceJwt();
        Call<String> call = mApiService.saveExchangeAsset(request, API_TOKEN);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("Save exchange Asset ", response.message());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("onFailure ", t.getMessage());
            }
        });
    }

    public LiveData<Asset> getDataAssetInput() {
        return liveDataAssetInput;
    }

    public LiveData<Asset> getDataAssetScan() {
        return liveDataAssetScan;
    }

    public LiveData<EmployeeResult> getDataEmployee() {
        return liveDataEmpl;
    }
}
