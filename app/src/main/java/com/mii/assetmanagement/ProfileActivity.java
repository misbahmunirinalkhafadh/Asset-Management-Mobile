package com.mii.assetmanagement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mii.assetmanagement.apihelper.BaseApiService;
import com.mii.assetmanagement.apihelper.UtilsApi;

public class ProfileActivity extends AppCompatActivity {

    TextView tvResultName, tvResultEmail, tvResultNik;
    Button btnLogout;

    Context mContext;
    BaseApiService mApiService;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initComponent();

        mContext = this;
        mApiService = UtilsApi.getApiService();
        sharedPrefManager = new SharedPrefManager(this);

        tvResultName.setText(sharedPrefManager.getSPNama());
        tvResultNik.setText(sharedPrefManager.getSpNik());
        tvResultEmail.setText(sharedPrefManager.getSPEmail());

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sharedPrefManager.saveSPString(SharedPrefManager.SP_EMAIL, sharedPrefManager.getSPEmail());

                // Shared Pref ini berfungsi untuk menjadi trigger session login
                sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);
                startActivity(new Intent(ProfileActivity.this, LoginActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        });
    }

    private void initComponent() {
        tvResultName = findViewById(R.id.tv_name);
        tvResultEmail = findViewById(R.id.tv_email);
        tvResultNik = findViewById(R.id.tv_nik);
        btnLogout = findViewById(R.id.btn_logout);
    }

}
