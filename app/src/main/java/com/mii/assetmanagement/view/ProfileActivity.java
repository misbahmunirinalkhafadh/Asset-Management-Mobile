package com.mii.assetmanagement.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mii.assetmanagement.BuildConfig;
import com.mii.assetmanagement.R;
import com.mii.assetmanagement.SharedPrefManager;

import java.util.Objects;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvResultName, tvResultEmail, tvResultNik, tvVersion;
    private Button btnLogout;
    private SharedPrefManager sharedPrefManager;
    private static final String VERSION_NAME = "Version " + BuildConfig.VERSION_NAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        actionBar();

        sharedPrefManager = new SharedPrefManager(this);

        //call initializer component
        initComponent();

        //set value component
        tvResultName.setText(sharedPrefManager.getSPNama());
        tvResultNik.setText(sharedPrefManager.getSpNik());
        tvResultEmail.setText(sharedPrefManager.getSPEmail());
        tvVersion.setText(VERSION_NAME);
        btnLogout.setOnClickListener(this);
    }

    /**
     * inititalize component
     */
    private void initComponent() {
        tvResultName = findViewById(R.id.tv_name);
        tvResultEmail = findViewById(R.id.tv_email);
        tvResultNik = findViewById(R.id.tv_nik);
        tvVersion = findViewById(R.id.tv_version_app);
        btnLogout = findViewById(R.id.btn_logout);
    }

    private void actionBar() {
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.appbar_profile);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setElevation(0);
    }

    @Override
    public void onClick(View v) {
        Log.v("remove session", "check function");
        // Shared Pref ini berfungsi untuk menjadi trigger session login
        sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);
        startActivity(new Intent(ProfileActivity.this, LoginActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
