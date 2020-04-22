package com.mii.assetmanagement.view;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.snackbar.Snackbar;
import com.mii.assetmanagement.R;
import com.mii.assetmanagement.viewmodel.ExchangeViewModel;

import java.util.Objects;

import es.dmoral.toasty.Toasty;


/**
 * A simple {@link Fragment} subclass.
 */
public class InputSerialExchangeAssetFragment extends Fragment implements View.OnClickListener {
    private EditText etSerial;
    private Button btnSearch;
    private ConstraintLayout layout;
    private Snackbar snackbar;
    private ExchangeViewModel exchangeViewModel;
    private ProgressDialog progressDialog;

    static InputSerialExchangeAssetFragment newInstance() {
        // Required empty public constructor
        return new InputSerialExchangeAssetFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_input_serial_exchange, container, false);

        initComponent(view);

        exchangeViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity()), new ViewModelProvider.NewInstanceFactory()).get(ExchangeViewModel.class);

        btnSearch.setOnClickListener(this);

        return view;
    }

    private void initComponent(View view) {
        etSerial = view.findViewById(R.id.et_serial);
        btnSearch = view.findViewById(R.id.btn_search_serial);
        layout = view.findViewById(R.id.main_view);
    }

    @Override
    public void onClick(View v) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(etSerial.getWindowToken(), 0);
        }
        String serial = etSerial.getText().toString().trim();
        if (serial.isEmpty()) {
            etSerial.setError("Enter serial number");
        } else if (isNetworkAvailable()) {
            showLoading();
            exchangeViewModel.setDataAssetInput(serial);
        } else {
            snackbar = Snackbar
                    .make(layout, "No Internet Connection", Snackbar.LENGTH_LONG)
                    .setAction("DISMISS", v1 -> snackbar.dismiss());
            snackbar.show();
        }
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
        exchangeViewModel.getDataAssetInput().observe(this, asset -> {
            Log.v("CHECK", "Error " + asset.isError());
            if (asset.isError()) {
                etSerial.getText().clear();
                Toasty.error(Objects.requireNonNull(getActivity()), "Invalid Serial Number", Toast.LENGTH_SHORT, true).show();
            } else {
                Intent goToExchangeAsset = new Intent(getActivity(), ExchangeAssetActivity.class);
                goToExchangeAsset.putExtra(ExchangeAssetActivity.EXTRA_ASSET, asset);
                goToExchangeAsset.putExtra(ExchangeAssetActivity.EXTRA_EMPLOYEE, asset.getEmployee());
                startActivity(goToExchangeAsset);
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
