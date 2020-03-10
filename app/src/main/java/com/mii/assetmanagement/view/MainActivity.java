package com.mii.assetmanagement.view;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mii.assetmanagement.R;
import com.mii.assetmanagement.SharedPrefManager;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView ivProfile;
    private TextView tvResultName, tvResultNik;
    private LinearLayout menuScan, menuRequest, menuExchange, menuHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();

        SharedPrefManager sharedPrefManager = new SharedPrefManager(this);

        //call initializer component
        initComponent();

        //set value component
        tvResultName.setText(sharedPrefManager.getSPNama());
        tvResultNik.setText(sharedPrefManager.getSpNik());

        //event click component
        ivProfile.setOnClickListener(this);
        menuScan.setOnClickListener(this);
        menuRequest.setOnClickListener(this);
        menuExchange.setOnClickListener(this);
        menuHistory.setOnClickListener(this);
    }


    /**
     * inititalize component
     */
    private void initComponent() {
        ivProfile = findViewById(R.id.img_profile);
        tvResultName = findViewById(R.id.tv_name);
        tvResultNik = findViewById(R.id.tv_nik);
        menuScan = findViewById(R.id.menu_maintenance);
        menuRequest = findViewById(R.id.menu_request);
        menuExchange = findViewById(R.id.menu_exchange);
        menuHistory = findViewById(R.id.menu_history);
    }

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
            //move to maintenance
            case R.id.menu_maintenance:
                Intent goToScanner = new Intent(MainActivity.this, GenerateMaintenanceActivity.class);
                startActivity(goToScanner);
                break;
            //menu request assets
            case R.id.menu_request:
                Intent goToRequest = new Intent(MainActivity.this, RequestActivity.class);
                startActivity(goToRequest);
                break;
            case R.id.menu_exchange:
                Intent goToExchange = new Intent(MainActivity.this, ExchangeActivity.class);
                startActivity(goToExchange);
                break;
            case R.id.menu_history:
                Intent goToHistory = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(goToHistory);
                break;
        }
    }
}
