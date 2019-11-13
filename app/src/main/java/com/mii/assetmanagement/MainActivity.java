package com.mii.assetmanagement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mii.assetmanagement.apihelper.BaseApiService;
import com.mii.assetmanagement.apihelper.UtilsApi;
import com.mii.assetmanagement.model.LoginResult;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView ivProfile;
    TextView tvResultName, tvResultNik;
    LinearLayout menuScan;

    Context mContext;
    BaseApiService mApiService;
    SharedPrefManager sharedPrefManager;
    LoginResult loginResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponent();
        mContext = this;
        mApiService = UtilsApi.getApiService();
        sharedPrefManager = new SharedPrefManager(this);
        tvResultName.setText(sharedPrefManager.getSPNama());
//        tvResultNik.setText(sharedPrefManager.getSpNik());

        ivProfile.setOnClickListener(this);
        menuScan.setOnClickListener(this);
    }

    private void initComponent() {
        ivProfile = findViewById(R.id.img_profile);
        tvResultName = findViewById(R.id.tv_name);
        tvResultNik = findViewById(R.id.tv_nik);
        menuScan = findViewById(R.id.menu_scan);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_profile:
                Intent goToProfile = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(goToProfile);
                break;
            case R.id.menu_scan:
                Intent goToScanner = new Intent(MainActivity.this, ScannerActivity.class);
                startActivity(goToScanner);
                break;
        }
    }
}
