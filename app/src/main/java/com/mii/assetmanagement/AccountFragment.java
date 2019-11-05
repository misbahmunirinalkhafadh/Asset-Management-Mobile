package com.mii.assetmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AccountFragment extends Fragment {

    TextView tvResultFirstname;
    Button btnLogout;

    SharedPrefManager sharedPrefManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        tvResultFirstname = (TextView) view.findViewById(R.id.tv_result_firstname);
        btnLogout = (Button) view.findViewById(R.id.btn_logout);

//        sharedPrefManager = this.getActivity().getSharedPreferences()
//        tvResultFirstname.setText("Nama : TEST");
//
//        btnLogout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);
//                startActivity(new Intent(AccountFragment.this, LoginActivity.class)
//                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
//
//            }
//        });
    }

}
