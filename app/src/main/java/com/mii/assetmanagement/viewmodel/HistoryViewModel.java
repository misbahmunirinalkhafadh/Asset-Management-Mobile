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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryViewModel extends ViewModel {

    private static final String API_TOKEN = BuildConfig.JWT_SAKURA_TOKEN;
    private MutableLiveData<History> liveDataProgress = new MutableLiveData<>();
    private MutableLiveData<History> liveDataComplete = new MutableLiveData<>();
    private MutableLiveData<List<HistoryDetail>> liveDataHistoryDetail = new MutableLiveData<>();

    public void setHistoryProgress(int nik, String status) {
        ApiService mApiService = UtilsApi.getApiServiceJwt();
        Call<History> call = mApiService.getHistory(nik, status, API_TOKEN);
        call.enqueue(new Callback<History>() {
            @Override
            public void onResponse(Call<History> call, Response<History> response) {
                liveDataProgress.setValue(response.body());
            }

            @Override
            public void onFailure(Call<History> call, Throwable t) {
                Log.e("onFailure", t.toString());
            }
        });
    }

    public void setHistoryComplete(int nik, String status) {
        ApiService mApiService = UtilsApi.getApiServiceJwt();
        Call<History> call = mApiService.getHistory(nik, status, API_TOKEN);
        call.enqueue(new Callback<History>() {
            @Override
            public void onResponse(Call<History> call, Response<History> response) {
                liveDataComplete.setValue(response.body());
            }

            @Override
            public void onFailure(Call<History> call, Throwable t) {
                Log.e("onFailure", t.toString());
            }
        });
    }

    public void setHistoryDetail(int id) {
        ApiService mApiService = UtilsApi.getApiServiceJwt();
        Call<List<HistoryDetail>> call = mApiService.getHistoryDetail(id, API_TOKEN);
        call.enqueue(new Callback<List<HistoryDetail>>() {
            @Override
            public void onResponse(Call<List<HistoryDetail>> call, Response<List<HistoryDetail>> response) {
                liveDataHistoryDetail.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<HistoryDetail>> call, Throwable t) {
                Log.e("onFailure", t.toString());
            }
        });
    }

    public LiveData<History> getHistoryProgress() {
        return liveDataProgress;
    }

    public LiveData<History> getHistoryComplete() {
        return liveDataComplete;
    }

    public LiveData<List<HistoryDetail>> getHistoryDetail() {
        return liveDataHistoryDetail;
    }
}
