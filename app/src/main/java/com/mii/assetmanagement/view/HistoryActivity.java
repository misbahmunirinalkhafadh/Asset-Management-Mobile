package com.mii.assetmanagement.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.mii.assetmanagement.R;

public class HistoryActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout LayoutMaintenance, LayoutRequest, LayoutExcAsset, LayoutExcEmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        actionBar();
        initComponent();

        LayoutMaintenance.setOnClickListener(this);
        LayoutRequest.setOnClickListener(this);
        LayoutExcAsset.setOnClickListener(this);
        LayoutExcEmp.setOnClickListener(this);
    }

    private void initComponent() {
        LayoutMaintenance = findViewById(R.id.layout_his_maintenance);
        LayoutRequest = findViewById(R.id.layout_his_req_new);
        LayoutExcAsset = findViewById(R.id.layout_his_exc_asset);
        LayoutExcEmp = findViewById(R.id.layout_his_exc_emp);
    }

    private void actionBar() {
        ActionBar actionBar = getSupportActionBar();
        AppCompatTextView mTitleTextView = new AppCompatTextView(getApplicationContext());
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        if (actionBar != null) {
            actionBar.setCustomView(mTitleTextView, layoutParams);
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_HOME_AS_UP);
            actionBar.setElevation(0);
        }
        mTitleTextView.setText(R.string.appbar_history);
        mTitleTextView.setTextAppearance(getApplicationContext(), android.R.style.TextAppearance_DeviceDefault_Large);
        mTitleTextView.setTextColor(Color.WHITE);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_his_maintenance:
                Intent goToHisMaintain = new Intent(HistoryActivity.this, HistoryMaintenanceActivity.class);
                startActivity(goToHisMaintain);
                break;
            case R.id.layout_his_req_new:
                Intent goToHisReqNew = new Intent(HistoryActivity.this, HistoryRequestNewActivity.class);
                startActivity(goToHisReqNew);
                break;
            case R.id.layout_his_exc_asset:
                Intent goToHisExcAsset = new Intent(HistoryActivity.this, HistoryExcAssetActivity.class);
                startActivity(goToHisExcAsset);
                break;
            case R.id.layout_his_exc_emp:
                Intent goToHisExcEmp = new Intent(HistoryActivity.this, HistoryExcEmpActivity.class);
                startActivity(goToHisExcEmp);
                break;
        }
    }
}
