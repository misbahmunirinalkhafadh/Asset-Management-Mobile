package com.mii.assetmanagement.viewmodel;

import android.util.Log;

import com.mii.assetmanagement.BuildConfig;
import com.mii.assetmanagement.apihelper.ApiService;
import com.mii.assetmanagement.apihelper.UtilsApi;
import com.mii.assetmanagement.model.Brand;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BrandViewModel extends ViewModel {

    private static final String API_TOKEN = BuildConfig.JWT_SAKURA_TOKEN;
    private MutableLiveData<Brand> liveDataBrand = new MutableLiveData<>();

    public void setDataBrand(String brand) {
        ApiService mApiService = UtilsApi.getApiServiceJwt();
        Call<Brand> call = mApiService.getBrand(brand, API_TOKEN);
        call.enqueue(new Callback<Brand>() {
            @Override
            public void onResponse(Call<Brand> call, Response<Brand> response) {
                if (response.body() == null) {
                    liveDataBrand.setValue(null);
                } else {
                    liveDataBrand.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Brand> call, Throwable t) {
                Log.e("onFailure", t.getMessage());

            }
        });
    }

}
