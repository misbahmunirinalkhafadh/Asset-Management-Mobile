package com.mii.assetmanagement.view;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.mii.assetmanagement.R;

public class HistoryDetailMaintenanceActivity extends AppCompatActivity {
    private TextView tvIdTrans, tvProcessor, tvRam, tvHdd, tvSsd, tvOs, tvAntivirus, tvAppStd, tvCleaning, tvFunction, tvIpAddress, tvUnameLogin, tvCompName, tvReason;
    public static final String EXTRA_HISTORY = "extra_history";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_detail_maintenance);
        actionBar();
        initComponent();
    }

    private void initComponent() {
        tvIdTrans = findViewById(R.id.tv_id_request);
        tvProcessor = findViewById(R.id.tv_processor);
        tvRam = findViewById(R.id.tv_ram);
        tvHdd = findViewById(R.id.tv_hdd);
        tvSsd = findViewById(R.id.tv_ssd);
        tvOs = findViewById(R.id.tv_os);
        tvAntivirus = findViewById(R.id.tv_antivirus);
        tvAppStd = findViewById(R.id.tv_app_standard);
        tvCleaning = findViewById(R.id.tv_cleaning);
        tvFunction = findViewById(R.id.tv_function);
        tvIpAddress = findViewById(R.id.tv_ip_address);
        tvUnameLogin = findViewById(R.id.tv_uname_login);
        tvCompName = findViewById(R.id.tv_comp_name);
    }

    private void actionBar() {
        ActionBar actionBar = getSupportActionBar();
        AppCompatTextView mTitleTextView = new AppCompatTextView(getApplicationContext());
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.START;
        if (actionBar != null) {
            actionBar.setCustomView(mTitleTextView, layoutParams);
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_HOME_AS_UP);
        }
        mTitleTextView.setText(getString(R.string.appbar_history_detail));
        mTitleTextView.setTextAppearance(getApplicationContext(), android.R.style.TextAppearance_DeviceDefault_Large);
        mTitleTextView.setTextColor(Color.WHITE);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
