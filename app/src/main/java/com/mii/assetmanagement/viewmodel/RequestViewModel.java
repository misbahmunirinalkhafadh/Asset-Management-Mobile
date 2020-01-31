package com.mii.assetmanagement.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mii.assetmanagement.BuildConfig;
import com.mii.assetmanagement.model.SalesOrder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class RequestViewModel extends ViewModel {

    private static final String API_TOKEN = BuildConfig.JWT_SAKURA_TOKEN;
    private MutableLiveData<ArrayList<SalesOrder>> liveData = new MutableLiveData<>();

    public void setLiveData(String soNumber) {
        String url = "http://116.254.101.228:8080/APISAKURAJWT/salesorder/" + soNumber;
        final ArrayList<SalesOrder> salesOrderArrayList = new ArrayList<>();
        final Request.Builder builder = new Request.Builder()
                .get()
                .url(url)
                .addHeader("Authorization", "bootcamp " + API_TOKEN);

        OkHttpClient client = new OkHttpClient();
        Request request = builder.build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                Log.e("Failure", e.getMessage());
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                String result = response.body().string();
                try {
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("data");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject object = list.getJSONObject(i);
                        SalesOrder salesOrder = new SalesOrder();
                        salesOrder.setSoId(object.getString("Sales_Order_Id"));
                        salesOrder.setCustomerName(object.getString("customername"));

                        salesOrderArrayList.add(salesOrder);
                    }
                    liveData.postValue(salesOrderArrayList);
                    Log.i("Data", "" + list);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("Exception", e.getMessage());
                }
            }
        });
    }

    public LiveData<ArrayList<SalesOrder>> getListSalesOrder() {
        return liveData;
    }
}
