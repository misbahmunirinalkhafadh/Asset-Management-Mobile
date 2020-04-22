package com.mii.assetmanagement.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

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
        ActionBar actionBar = getSupportActionBar();
        AppCompatTextView mTitleTextView = new AppCompatTextView(getApplicationContext());
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.START;
        if (actionBar != null) {
            actionBar.setCustomView(mTitleTextView, layoutParams);
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_HOME_AS_UP);
            actionBar.setElevation(0);
        }
        mTitleTextView.setText(getString(R.string.appbar_profile));
        mTitleTextView.setTextAppearance(getApplicationContext(), android.R.style.TextAppearance_DeviceDefault_Large);
        mTitleTextView.setTextColor(Color.WHITE);
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
