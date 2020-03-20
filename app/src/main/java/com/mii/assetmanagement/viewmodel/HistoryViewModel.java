package com.mii.assetmanagement.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mii.assetmanagement.BuildConfig;
import com.mii.assetmanagement.apihelper.ApiService;
import com.mii.assetmanagement.apihelper.UtilsApi;
import com.mii.assetmanagement.model.History;
import com.mii.assetmanagement.model.HistoryDetail;
import com.mii.assetmanagement.model.HistoryMaintenance;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryViewModel extends ViewModel {

    private static final String API_TOKEN = BuildConfig.JWT_SAKURA_TOKEN;
    private MutableLiveData<HistoryMaintenance> liveDataMaintenance = new MutableLiveData<>();
    private MutableLiveData<History> reqNewAsset = new MutableLiveData<>();
    private MutableLiveData<History> exchangeAsset = new MutableLiveData<>();
    private MutableLiveData<History> exchangeEmp = new MutableLiveData<>();
    private MutableLiveData<History> liveDataProgress = new MutableLiveData<>();
    private MutableLiveData<History> liveDataComplete = new MutableLiveData<>();
    private MutableLiveData<List<HistoryDetail>> liveDataHistoryDetail = new MutableLiveData<>();

    public void setHistoryMaintenance(int nik) {
        ApiService mApiService = UtilsApi.getApiServiceJwt();
        Call<HistoryMaintenance> call = mApiService.getHistoryMaintenance(nik, API_TOKEN);
        call.enqueue(new Callback<HistoryMaintenance>() {
            @Override
            public void onResponse(Call<HistoryMaintenance> call, Response<HistoryMaintenance> response) {
                liveDataMaintenance.setValue(response.body());
            }

            @Override
            public void onFailure(Call<HistoryMaintenance> call, Throwable t) {
                Log.e("onFailure", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    public void setHistoryProgress(String type, int nik, String progress) {
        ApiService mApiService = UtilsApi.getApiServiceJwt();
        if (type.equals("Request New Asset")) {
            Call<History> call = mApiService.getHistoryReqNew(nik, progress, API_TOKEN);
            call.enqueue(new Callback<History>() {
                @Override
                public void onResponse(Call<History> call, Response<History> response) {
                    reqNewAsset.setValue(response.body());
                }

                @Override
                public void onFailure(Call<History> call, Throwable t) {
                    Log.e("onFailure", Objects.requireNonNull(t.getMessage()));
                }
            });
        }
        if (type.equals("Exchange Asset")) {
            Call<History> call = mApiService.getHistoryExcAsset(nik, progress, API_TOKEN);
            call.enqueue(new Callback<History>() {
                @Override
                public void onResponse(Call<History> call, Response<History> response) {
                    exchangeAsset.setValue(response.body());
                }

                @Override
                public void onFailure(Call<History> call, Throwable t) {
                    Log.e("onFailure", Objects.requireNonNull(t.getMessage()));
                }
            });
        }
        if (type.equals("Exchange Employee")) {
            Call<History> call = mApiService.getHistoryExcEmp(nik, progress, API_TOKEN);
            call.enqueue(new Callback<History>() {
                @Override
                public void onResponse(Call<History> call, Response<History> response) {
                    exchangeEmp.setValue(response.body());
                }

                @Override
                public void onFailure(Call<History> call, Throwable t) {
                    Log.e("onFailure", Objects.requireNonNull(t.getMessage()));
                }
            });
        }
    }

    public void setHistoryComplete(int nik, String completed) {

    }

    public void setHistoryDetail(int id) {

    }

    public LiveData<HistoryMaintenance> getHistoryMaintenance() {
        return liveDataMaintenance;
    }

    public LiveData<History> getHistoryProgress() {
        if (reqNewAsset != null) {
            liveDataProgress = reqNewAsset;
        }
        if (exchangeAsset != null) {
            liveDataProgress = exchangeAsset;
        }
        if (exchangeEmp != null) {
            liveDataProgress = exchangeEmp;
        }
        return liveDataProgress;
    }

    public LiveData<History> getHistoryComplete() {
        return liveDataComplete;
    }

    public LiveData<List<HistoryDetail>> getHistoryDetail() {
        return liveDataHistoryDetail;
    }
}
