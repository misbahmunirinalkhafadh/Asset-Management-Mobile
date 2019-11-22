package com.mii.assetmanagement;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.zxing.Result;
import com.mii.assetmanagement.apihelper.ApiService;
import com.mii.assetmanagement.apihelper.UtilsApi;
import com.mii.assetmanagement.model.Asset;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CaptureFragment extends Fragment implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;
    private ApiService mApiService;
    private ProgressDialog progressDialog;

    static CaptureFragment newInstance() {
        // Required empty public constructor
        return new CaptureFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mScannerView = new ZXingScannerView(getActivity());

        mApiService = UtilsApi.getApiService();
        //Progress Dialog
        progressDialog = new ProgressDialog(getActivity());

        return mScannerView;
    }


    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.setSquareViewFinder(true);
        mScannerView.setBorderColor(Color.parseColor("#35B781"));
        mScannerView.setLaserEnabled(true);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(final Result rawResult) {
        progressDialog.show(); // show progress dialog
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message

        //Handler Dialog
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
            }
        }, 300);

        String resultCode = rawResult.getText();
        Log.v("TAG", resultCode);

        mApiService.assetRequest(resultCode).enqueue(new Callback<Asset>() {
            @Override
            public void onResponse(Call<Asset> call, Response<Asset> response) {
                if (!response.body().getError()) {
                    String hdd;
                    String ssd;
                    if (response.body().getParts().getSSD() == null) ssd = "-";
                    else ssd = response.body().getParts().getSSD();
                    if (response.body().getParts().getHDD() == null) hdd = "-";
                    else hdd = response.body().getParts().getHDD();

                    // Bundle
                    Bundle extras = new Bundle();
                    extras.putString("Processor", response.body().getParts().getProcessor());
                    extras.putString("System", response.body().getParts().getOS());
                    extras.putString("RAM", response.body().getParts().getRAM());
                    extras.putString("HDD", hdd);
                    extras.putString("SSD", ssd);

                    //move activity
                    Intent goToInformation = new Intent(getActivity(), InformasiActivity.class);
                    goToInformation.putExtras(extras);
                    startActivity(goToInformation);
                } else {
                    Log.e("debug", String.valueOf(true));
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "Invalid QR Code", Toast.LENGTH_SHORT).show();

                    //refresh page
                    getActivity().finish();
                    startActivity(getActivity().getIntent());
                }
            }

            @Override
            public void onFailure(Call<Asset> call, Throwable t) {
                Log.e("debug", "onFailure: ERROR > " + t.toString());
            }
        });
    }
}
