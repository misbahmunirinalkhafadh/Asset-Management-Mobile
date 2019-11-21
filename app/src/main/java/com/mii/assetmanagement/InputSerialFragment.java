package com.mii.assetmanagement;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mii.assetmanagement.apihelper.ApiService;
import com.mii.assetmanagement.apihelper.UtilsApi;


/**
 * A simple {@link Fragment} subclass.
 */
public class InputSerialFragment extends Fragment implements View.OnClickListener{

    EditText etSerial;
    Button btnSearch;

    ApiService mApiService;

    public static InputSerialFragment newInstance() {
        // Required empty public constructor
        return new InputSerialFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_input_serial, container, false);

        mApiService = UtilsApi.getApiService();

        etSerial = view.findViewById(R.id.et_serial);
        btnSearch = view.findViewById(R.id.btn_search_serial);

        btnSearch.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        serialRequest();

        Intent goToInformation = new Intent(getActivity(), InformasiActivity.class);
        getActivity().startActivity(goToInformation);
    }

    private void serialRequest() {
        String serial = etSerial.getText().toString();

//        mApiService.assetRequest(serial).enqueue(new Callback<Device>() {
//            @Override
//            public void onResponse(Call<Device> call, Response<Device> response) {
//                if (response.isSuccessful()) {
//                    for (String part : response.body().getParts()) {
//                        Log.v("Response API", part);
//                    }
//                } else {
//                    Log.e("debug", "notSuccess");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Device> call, Throwable t) {
//                Log.e("debug", "onFailure: ERROR > " + t.toString());
//            }
//        });
    }
}
