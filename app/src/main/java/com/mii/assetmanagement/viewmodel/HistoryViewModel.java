package com.mii.assetmanagement.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mii.assetmanagement.BuildConfig;
import com.mii.assetmanagement.apihelper.ApiService;
import com.mii.assetmanagement.apihelper.UtilsApi;
import com.mii.assetmanagement.model.HistoryMaintenance;
import com.mii.assetmanagement.model.HistoryRequest;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryViewModel extends ViewModel {
    private static final String API_TOKEN = BuildConfig.JWT_SAKURA_TOKEN;
    private MutableLiveData<HistoryMaintenance> liveDataMaintenance = new MutableLiveData<>();
    private MutableLiveData<HistoryMaintenance> liveDataMainDetailPart = new MutableLiveData<>();
    private MutableLiveData<HistoryRequest> liveDataProgress = new MutableLiveData<>();
    private MutableLiveData<HistoryRequest> liveDataComplete = new MutableLiveData<>();
    private ApiService mApiService;

    public void setHistoryMaintenance(int nik) {
        mApiService = UtilsApi.getApiServiceJwt();
        mApiService.getHistoryMaintenance(nik, API_TOKEN).enqueue(new Callback<HistoryMaintenance>() {
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

    public void setHistoryDetailMaintenance(int _id) {
        mApiService = UtilsApi.getApiServiceJwt();
        mApiService.getHistoryDetailMaintenance(_id, API_TOKEN).enqueue(new Callback<HistoryMaintenance>() {
            @Override
            public void onResponse(Call<HistoryMaintenance> call, Response<HistoryMaintenance> response) {
                liveDataMainDetailPart.setValue(response.body());
            }

            @Override
            public void onFailure(Call<HistoryMaintenance> call, Throwable t) {
                Log.e("onFailure", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    public void setHistoryProgress(String type, int nik, String progress) {
        mApiService = UtilsApi.getApiServiceJwt();
        if (type.equals("Request New Asset")) {
            mApiService.getHistoryReqNew(nik, progress, API_TOKEN).enqueue(new Callback<HistoryRequest>() {
                @Override
                public void onResponse(Call<HistoryRequest> call, Response<HistoryRequest> response) {
                    liveDataProgress.setValue(response.body());
                }

                @Override
                public void onFailure(Call<HistoryRequest> call, Throwable t) {
                    Log.e("onFailure", Objects.requireNonNull(t.getMessage()));
                }
            });
        }
        if (type.equals("Exchange Asset")) {
            mApiService.getHistoryExcAsset(nik, progress, API_TOKEN).enqueue(new Callback<HistoryRequest>() {
                @Override
                public void onResponse(Call<HistoryRequest> call, Response<HistoryRequest> response) {
                    liveDataProgress.setValue(response.body());
                }

                @Override
                public void onFailure(Call<HistoryRequest> call, Throwable t) {
                    Log.e("onFailure", Objects.requireNonNull(t.getMessage()));
                }
            });
        }
        if (type.equals("Exchange Employee")) {
            mApiService.getHistoryExcEmp(nik, progress, API_TOKEN).enqueue(new Callback<HistoryRequest>() {
                @Override
                public void onResponse(Call<HistoryRequest> call, Response<HistoryRequest> response) {
                    liveDataProgress.setValue(response.body());
                }

                @Override
                public void onFailure(Call<HistoryRequest> call, Throwable t) {
                    Log.e("onFailure", Objects.requireNonNull(t.getMessage()));
                }
            });
        }
    }

    public void setHistoryComplete(String type, int nik, String completed) {
        mApiService = UtilsApi.getApiServiceJwt();
        if (type.equals("Request New Asset")) {
            mApiService.getHistoryReqNew(nik, completed, API_TOKEN).enqueue(new Callback<HistoryRequest>() {
                @Override
                public void onResponse(Call<HistoryRequest> call, Response<HistoryRequest> response) {
                    liveDataComplete.setValue(response.body());
                }

                @Override
                public void onFailure(Call<HistoryRequest> call, Throwable t) {
                    Log.e("onFailure", Objects.requireNonNull(t.getMessage()));
                }
            });
        }
        if (type.equals("Exchange Asset")) {
            mApiService.getHistoryExcAsset(nik, completed, API_TOKEN).enqueue(new Callback<HistoryRequest>() {
                @Override
                public void onResponse(Call<HistoryRequest> call, Response<HistoryRequest> response) {
                    liveDataComplete.setValue(response.body());
                }

                @Override
                public void onFailure(Call<HistoryRequest> call, Throwable t) {
                    Log.e("onFailure", Objects.requireNonNull(t.getMessage()));
                }
            });
        }
        if (type.equals("Exchange Employee")) {
            mApiService.getHistoryExcEmp(nik, completed, API_TOKEN).enqueue(new Callback<HistoryRequest>() {
                @Override
                public void onResponse(Call<HistoryRequest> call, Response<HistoryRequest> response) {
                    liveDataComplete.setValue(response.body());
                }

                @Override
                public void onFailure(Call<HistoryRequest> call, Throwable t) {
                    Log.e("onFailure", Objects.requireNonNull(t.getMessage()));
                }
            });
        }
    }

    public LiveData<HistoryMaintenance> getHistoryMaintenance() {
        return liveDataMaintenance;
    }

    public LiveData<HistoryMaintenance> getHistoryDetailMaintenance() {
        return liveDataMainDetailPart;
    }

    public LiveData<HistoryRequest> getHistoryProgress() {
        return liveDataProgress;
    }

    public LiveData<HistoryRequest> getHistoryComplete() {
        return liveDataComplete;
    }
}
