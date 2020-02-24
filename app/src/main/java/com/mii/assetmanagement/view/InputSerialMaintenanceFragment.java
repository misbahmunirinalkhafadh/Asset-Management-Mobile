package com.mii.assetmanagement.view;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.mii.assetmanagement.R;
import com.mii.assetmanagement.model.Asset;
import com.mii.assetmanagement.viewmodel.MaintenanceViewModel;

import java.util.Objects;

import es.dmoral.toasty.Toasty;


/**
 * A simple {@link Fragment} subclass.
 */
public class InputSerialMaintenanceFragment extends Fragment implements View.OnClickListener {

    private EditText etSerial;
    private Button btnSearch;
    private MaintenanceViewModel maintenanceViewModel;
    private ProgressDialog progressDialog;

    static InputSerialMaintenanceFragment newInstance() {
        // Required empty public constructor
        return new InputSerialMaintenanceFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_input_serial_maintenance, container, false);

        initComponent(view);

        maintenanceViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity()), new ViewModelProvider.NewInstanceFactory()).get(MaintenanceViewModel.class);

        btnSearch.setOnClickListener(this);

        return view;
    }

    private void initComponent(View view) {
        etSerial = view.findViewById(R.id.et_serial);
        btnSearch = view.findViewById(R.id.btn_search_serial);
    }

    @Override
    public void onClick(View v) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etSerial.getWindowToken(), 0);
        String serial = etSerial.getText().toString().trim();
        if (serial.isEmpty()) {
            etSerial.setError("Enter serial number");
        } else {
            showLoading();
            maintenanceViewModel.setDataAsset(serial);
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        maintenanceViewModel.getDataAsset().observe(this, new Observer<Asset>() {
            @Override
            public void onChanged(Asset asset) {
                Log.v("CHECK", "Error " + asset.isError());
                if (asset.isError()) {
                    etSerial.getText().clear();
                    etSerial.clearFocus();
                    Toasty.error(Objects.requireNonNull(getActivity()), "Invalid QR CODE", Toast.LENGTH_SHORT, true).show();
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
