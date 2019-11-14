package com.mii.assetmanagement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mii.assetmanagement.apihelper.ApiService;
import com.mii.assetmanagement.apihelper.UtilsApi;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    TextView tvResultName, tvResultEmail, tvResultNik;
    Button btnLogout;
    ImageView btnBack;

    Context mContext;
    ApiService mApiService;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mContext = this;
        mApiService = UtilsApi.getAPIService();
        sharedPrefManager = new SharedPrefManager(this);

        //call initializer component
        initComponent();

        //set value component
        tvResultName.setText(sharedPrefManager.getSPNama());
        tvResultNik.setText(sharedPrefManager.getSpNik());
        tvResultEmail.setText(sharedPrefManager.getSPEmail());

        btnBack.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
    }

    /**
     * inititalize component
     */
    private void initComponent() {
        tvResultName = findViewById(R.id.tv_name);
        tvResultEmail = findViewById(R.id.tv_email);
        tvResultNik = findViewById(R.id.tv_nik);
        btnLogout = findViewById(R.id.btn_logout);
        btnBack = findViewById(R.id.iv_back);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //back to prev page
            case R.id.iv_back:
                onBackPressed();
                break;
            //move to login page
            case R.id.btn_logout:
                sharedPrefManager.saveSPString(SharedPrefManager.SP_EMAIL, sharedPrefManager.getSPEmail());

                // Shared Pref ini berfungsi untuk menjadi trigger session login
                sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);
                startActivity(new Intent(ProfileActivity.this, LoginActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
                break;
        }
    }
}
