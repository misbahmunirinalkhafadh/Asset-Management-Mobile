package com.mii.assetmanagement.view;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.google.zxing.Result;
import com.mii.assetmanagement.viewmodel.ExchangeViewModel;

import java.util.Objects;

import es.dmoral.toasty.Toasty;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class CaptureExchangeEmplFragment extends Fragment implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;
    private ExchangeViewModel exchangeViewModel;
    private ProgressDialog progressDialog;

    static CaptureExchangeEmplFragment newInstance() {
        // Required empty public constructor
        return new CaptureExchangeEmplFragment();
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
        exchangeViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity()), new ViewModelProvider.NewInstanceFactory()).get(ExchangeViewModel.class);

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
        if (isNetworkAvailable()) {
            showLoading();
            String serial = rawResult.getText();
            exchangeViewModel.setDataAssetScan(serial);
        } else {
            Toasty.warning(Objects.requireNonNull(getActivity()), "No Internet Connection", Toasty.LENGTH_LONG).show();
        }

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

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        exchangeViewModel.getDataAssetScan().observe(this, asset -> {
            Log.v("CHECK", "Error " + asset.isError());
            if (asset.isError()) {
                Toasty.error(Objects.requireNonNull(getActivity()), "Invalid QR CODE", Toast.LENGTH_SHORT, true).show();
            } else if (asset.getTypeSerialNumber().equals("Asset Only")) {
                Toasty.warning(Objects.requireNonNull(getActivity()), "Serial Number Type is Asset Only", Toast.LENGTH_SHORT, true).show();
            } else {
                Intent goToExchangeEmpl = new Intent(getActivity(), ExchangeEmployeeActivity.class);
                goToExchangeEmpl.putExtra(ExchangeEmployeeActivity.EXTRA_ASSET, asset);
                goToExchangeEmpl.putExtra(ExchangeEmployeeActivity.EXTRA_EMPLOYEE, asset.getEmployee());
                startActivity(goToExchangeEmpl);
            }
            dismissLoading();
        });
    }

    private void dismissLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
