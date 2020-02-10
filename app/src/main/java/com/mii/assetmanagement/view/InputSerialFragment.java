package com.mii.assetmanagement.view;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.mii.assetmanagement.viewmodel.AssetViewModel;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class InputSerialFragment extends Fragment implements View.OnClickListener {

    private EditText etSerial;
    private Button btnSearch;
    private AssetViewModel assetViewModel;
    private ProgressDialog progressDialog;

    static InputSerialFragment newInstance() {
        // Required empty public constructor
        return new InputSerialFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_input_serial, container, false);

        initComponent(view);
        loading();

        assetViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity()), new ViewModelProvider.NewInstanceFactory()).get(AssetViewModel.class);
        btnSearch.setOnClickListener(this);

        return view;
    }

    /**
     * initialize component
     *
     * @param view
     */
    private void initComponent(View view) {
        etSerial = view.findViewById(R.id.et_serial);
        btnSearch = view.findViewById(R.id.btn_search_serial);
    }

    @Override
    public void onClick(View v) {
        String serial = etSerial.getText().toString().trim();
        if (serial.isEmpty()) {
            etSerial.setError("Enter serial number");
        } else {
            progressDialog.show();
            assetViewModel.setDataAsset(serial);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        assetViewModel.getDataAsset().observe(this, new Observer<Asset>() {
            @Override
            public void onChanged(Asset asset) {
                Log.v("CHECK", "Error " + asset.isError());
                if (asset.isError()) {
                    etSerial.getText().clear();
                    Toast.makeText(getActivity(), "Invalid serial number", Toast.LENGTH_SHORT).show();
                } else {
                    Intent goToInformation = new Intent(getActivity(), InformasiActivity.class);
                    goToInformation.putExtra(InformasiActivity.EXTRA_ASSET, asset);
                    goToInformation.putExtra(InformasiActivity.EXTRA_USER, asset.getUser());
                    goToInformation.putExtra(InformasiActivity.EXTRA_PARTS, asset.getParts());
                    startActivity(goToInformation);
                }
                progressDialog.hide();
            }
        });
    }

    private void loading() {
        //Progress Dialog
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait..."); // set message
    }
}
