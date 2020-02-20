package com.mii.assetmanagement.view;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.google.zxing.Result;
import com.mii.assetmanagement.model.Asset;
import com.mii.assetmanagement.viewmodel.MaintenanceViewModel;

import java.util.Objects;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class CaptureExchangeAssetFragment extends Fragment implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;
    private MaintenanceViewModel maintenanceViewModel;
    private ProgressDialog progressDialog;

    static CaptureExchangeAssetFragment newInstance() {
        // Required empty public constructor
        return new CaptureExchangeAssetFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //set permission version
        if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getActivity()), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, 5);
            }
        }
        maintenanceViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity()), new ViewModelProvider.NewInstanceFactory()).get(MaintenanceViewModel.class);

        mScannerView = new ZXingScannerView(getActivity());
        Window window = getActivity().getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

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
    public void handleResult(Result rawResult) {
        showLoading();
        String serial = rawResult.getText();
        maintenanceViewModel.setDataAssetScan(serial);

        mScannerView.resumeCameraPreview(this);
    }

    private void showLoading() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Please wait...");
        }
        progressDialog.show();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        maintenanceViewModel.getDataAssetScan().observe(this, new Observer<Asset>() {
            @Override
            public void onChanged(Asset asset) {
                Log.v("CHECK", "Error " + asset.isError());
                if (asset.isError()) {
                    Toast.makeText(getActivity(), "Invalid QR CODE", Toast.LENGTH_SHORT).show();
                } else {
                    Intent goToInformation = new Intent(getActivity(), InformationActivity.class);
                    goToInformation.putExtra(InformationActivity.EXTRA_ASSET, asset);
                    goToInformation.putExtra(InformationActivity.EXTRA_EMPLOYEE, asset.getEmployee());
                    goToInformation.putExtra(InformationActivity.EXTRA_PARTS, asset.getParts());
                    startActivity(goToInformation);
                }
                dismissLoading();
            }
        });
    }

    private void dismissLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
