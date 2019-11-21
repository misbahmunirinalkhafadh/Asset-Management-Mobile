package com.mii.assetmanagement;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    String resultCode;
    ApiService mApiService;

    private ZXingScannerView mScannerView;

    public static CaptureFragment newInstance() {
        // Required empty public constructor
        return new CaptureFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mScannerView = new ZXingScannerView(getActivity());

        mApiService = UtilsApi.getApiService();


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
        resultCode = rawResult.getText();
        Log.v("TAG", resultCode);

        mApiService.assetRequest(resultCode).enqueue(new Callback<Asset>() {

            @Override
            public void onResponse(Call<Asset> call, Response<Asset> response) {
                final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                // display a progress dialog
                progressDialog.setCancelable(true); // set cancelable to false
                progressDialog.setMessage("Please Wait"); // set message
                progressDialog.show(); // show progress dialog

                if (!response.body().getError()) {

                    String processor = response.body().getParts().getProcessor();
                    String system = response.body().getParts().getOS();
                    String hdd = "";
                    String ssd = "";
                    if (response.body().getParts().getSSD()  == null) ssd = "-";
                    else ssd = response.body().getParts().getSSD();
                    if (response.body().getParts().getHDD()  == null) hdd = "-";
                    else hdd = response.body().getParts().getHDD();
                    String ram = response.body().getParts().getRAM();

                    Intent goToInformation = new Intent(getActivity(), InformasiActivity.class);
                    goToInformation.putExtra("Processor", processor);
                    goToInformation.putExtra("System", system);
                    goToInformation.putExtra("HDD", hdd);
                    goToInformation.putExtra("SSD", ssd);
                    goToInformation.putExtra("RAM", ram);
                    getActivity().startActivity(goToInformation);
                    progressDialog.dismiss();

                } else {
                    Log.e("debug", "notSuccess");
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<Asset> call, Throwable t) {
                Log.e("debug", "onFailure: ERROR > " + t.toString());
            }
        });
    }
}
