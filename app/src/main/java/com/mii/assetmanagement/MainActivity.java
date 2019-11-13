package com.mii.assetmanagement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mii.assetmanagement.apihelper.BaseApiService;
import com.mii.assetmanagement.apihelper.UtilsApi;
import com.mii.assetmanagement.model.LoginResult;

public class MainActivity extends AppCompatActivity {

    ImageView ivProfile;
    TextView tvResultName, tvResultNik;

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

        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = sharedPrefManager.getSPNama();
                String email = sharedPrefManager.getSPEmail();
                sharedPrefManager.saveSPString(SharedPrefManager.SP_NAMA, name);
                sharedPrefManager.saveSPString(SharedPrefManager.SP_EMAIL, email);
                // Shared Pref ini berfungsi untuk menjadi trigger session login
                sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, true);
                startActivity(new Intent(mContext, ProfileActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
//                Intent gotoprofile = new Intent(MainActivity.this, ProfileActivity.class);
//                startActivity(gotoprofile);
            }
        });
    }

    private void initComponent() {
        ivProfile = findViewById(R.id.img_profile);
        tvResultName = findViewById(R.id.tv_name);
        tvResultNik = findViewById(R.id.tv_nik);
    }

}
