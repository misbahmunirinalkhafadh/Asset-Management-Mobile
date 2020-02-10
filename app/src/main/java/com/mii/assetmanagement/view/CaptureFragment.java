package com.mii.assetmanagement.view;


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
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
import com.mii.assetmanagement.viewmodel.AssetViewModel;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class CaptureFragment extends Fragment implements ZXingScannerView.ResultHandler {

    private String resultSerial;

    Context mContext;
    private ZXingScannerView mScannerView;
    private ApiService mApiService;
    private ProgressDialog progressDialog;
    private AssetViewModel assetViewModel;

    static CaptureFragment newInstance() {
        // Required empty public constructor
        return new CaptureFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mScannerView = new ZXingScannerView(getActivity());

        mContext = getActivity();
        mApiService = UtilsApi.getApiService();
//        assetViewModel = ViewModelProviders.of(getActivity(), new ViewModelProvider.NewInstanceFactory()).get(AssetViewModel.class);
        //Progress Dialog
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message

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

        //Handler Dialog
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
            }
        }, 3000);

        resultSerial = rawResult.getText();
        Log.v("TAG", resultSerial);

//        assetViewModel.apiAssetMaintenance(resultSerial);

//        mApiService.assetRequest(resultSerial).enqueue(new Callback<Asset>() {
//            @Override
//            public void onResponse(Call<Asset> call, Response<Asset> response) {
//                if (!response.body().getError()) {
//                    String hdd;
//                    String ssd;
//                    if (response.body().getParts().getSSD() == null) ssd = "N/A";
//                    else ssd = response.body().getParts().getSSD();
//                    if (response.body().getParts().getHDD() == null) hdd = "N/A";
//                    else hdd = response.body().getParts().getHDD();
//
//                    // Bundle
//                    Bundle extras = new Bundle();
//                    // User
//                    extras.putString("nik", response.body().getUser().getNik());
//                    extras.putString("name", response.body().getUser().getName());
//                    extras.putString("location", response.body().getUser().getLocation());
//                    extras.putString("branch", response.body().getUser().getBranch());
//                    // Asset
//                    extras.putString("salesOrder", response.body().getSalesOrder());
//                    extras.putString("serialNumber", response.body().getSerialNumber());
//                    extras.putString("brand", response.body().getBrand());
//                    extras.putString("type", response.body().getType());
//                    extras.putStringArray("others", response.body().getOthers());
//                    // Parts
//                    extras.putString("Processor", response.body().getParts().getProcessor());
//                    extras.putString("OS", response.body().getParts().getOS());
//                    extras.putString("RAM", response.body().getParts().getRAM());
//                    extras.putString("HDD", hdd);
//                    extras.putString("SSD", ssd);
//
//                    //move activity
//                    Intent goToInformation = new Intent(mContext, InformasiActivity.class);
//                    goToInformation.putExtras(extras);
//                    startActivity(goToInformation);
//                } else {
//                    Log.e("debug", String.valueOf(true));
//                    progressDialog.dismiss();
//                    Toast.makeText(mContext, "Invalid QR Code", Toast.LENGTH_SHORT).show();
//
//                    //refresh page
//                    getActivity().finish();
//                    startActivity(getActivity().getIntent());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Asset> call, Throwable t) {
//                Log.e("debug", "onFailure: ERROR > " + t.toString());
//            }
//        });
    }
}
