package com.mii.assetmanagement;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mii.assetmanagement.apihelper.ApiService;
import com.mii.assetmanagement.apihelper.UtilsApi;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView ivProfile;
    TextView tvResultName, tvResultNik;
    LinearLayout menuScan;

    Context mContext;
    ApiService mApiService;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        mApiService = UtilsApi.getApiService();
        sharedPrefManager = new SharedPrefManager(this);

        //call initializer component
        initComponent();

        //set value component
        tvResultName.setText(sharedPrefManager.getSPNama());
        tvResultNik.setText(sharedPrefManager.getSpNik());

        //event click component
        ivProfile.setOnClickListener(this);
        menuScan.setOnClickListener(this);
    }

    /**
     * inititalize component
     */
    private void initComponent() {
        ivProfile = findViewById(R.id.img_profile);
        tvResultName = findViewById(R.id.tv_name);
        tvResultNik = findViewById(R.id.tv_nik);
        menuScan = findViewById(R.id.menu_scan);
    }

    /**
     * declare event click
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //move to profile
            case R.id.img_profile:
                Intent goToProfile = new Intent(MainActivity.this, ProfileActivity.class);

                //Animate transition
                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair(ivProfile, "profileTransition");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pairs);

                startActivity(goToProfile, options.toBundle());
                break;
            //move to scanner
            case R.id.menu_scan:
                Intent goToScanner = new Intent(MainActivity.this, ScannerActivity.class);
                startActivity(goToScanner);
                break;
        }
    }
}
