package com.mii.assetmanagement.view;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
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

import com.mii.assetmanagement.R;
import com.mii.assetmanagement.apihelper.ApiService;
import com.mii.assetmanagement.apihelper.UtilsApi;
import com.mii.assetmanagement.model.Asset;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class InputSerialFragment extends Fragment implements View.OnClickListener {

    private EditText etSerial;
    private Button btnSearch;
    private ApiService mApiService;
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

        mApiService = UtilsApi.getApiService();
        //Progress Dialog
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message

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
        if (TextUtils.isEmpty(etSerial.getText())) {
            etSerial.setError("Enter serial number");
        } else {
            progressDialog.show(); // show progress dialog
            //Handler Dialog
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressDialog.dismiss();
                }
            }, 2000);

            serialRequest();
        }
    }

    private void serialRequest() {
        String serial = etSerial.getText().toString();
        Log.v("TAG", serial);
        mApiService.assetRequest(serial).enqueue(new Callback<Asset>() {
            @Override
            public void onResponse(Call<Asset> call, Response<Asset> response) {
                if (!response.body().getError()) {
                    String hdd;
                    String ssd;
                    if (response.body().getParts().getSSD() == null) ssd = "N/A";
                    else ssd = response.body().getParts().getSSD();
                    if (response.body().getParts().getHDD() == null) hdd = "N/A";
                    else hdd = response.body().getParts().getHDD();

                    //Bundle
                    Bundle extras = new Bundle();
                    // User
                    extras.putString("nik", response.body().getUser().getNik());
                    extras.putString("name", response.body().getUser().getName());
                    extras.putString("location", response.body().getUser().getLocation());
                    extras.putString("branch", response.body().getUser().getBranch());
                    // Asset
                    extras.putString("salesOrder", response.body().getSalesOrder());
                    extras.putString("serialNumber", response.body().getSerialNumber());
                    extras.putString("brand", response.body().getBrand());
                    extras.putString("type", response.body().getType());
                    extras.putStringArray("others", response.body().getOthers());
                    // Parts
                    extras.putString("Processor", response.body().getParts().getProcessor());
                    extras.putString("OS", response.body().getParts().getOS());
                    extras.putString("RAM", response.body().getParts().getRAM());
                    extras.putString("HDD", hdd);
                    extras.putString("SSD", ssd);

                    Log.v("List Others", Arrays.toString(response.body().getOthers()));

                    Intent goToInformation = new Intent(getActivity(), InformasiActivity.class);
                    goToInformation.putExtras(extras);
                    startActivity(goToInformation);

                } else {
                    Log.e("Error", String.valueOf(true));
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "Invalid serial number", Toast.LENGTH_SHORT).show();
                    etSerial.getText().clear();
                }
            }

            @Override
            public void onFailure(Call<Asset> call, Throwable t) {
                Log.e("debug", "onFailure: ERROR > " + t.toString());
            }
        });

    }
}
