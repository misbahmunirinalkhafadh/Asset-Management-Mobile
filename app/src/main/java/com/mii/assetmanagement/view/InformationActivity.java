package com.mii.assetmanagement.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.mii.assetmanagement.R;
import com.mii.assetmanagement.model.Asset;
import com.mii.assetmanagement.model.Employee;
import com.mii.assetmanagement.model.Part;

public class InformationActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvNik, tvName, tvLocation, tvBranch;
    private TextView tvSales, tvSerial, tvBrand, tvCategory;
    public TextView tvProcessor, tvSystem, tvHdd, tvSsd, tvRam;
    private String[] listService;
    private Button btnMaintenance, btnClose;
    public static final String EXTRA_ASSET = "extra_asset";
    public static final String EXTRA_EMPLOYEE = "extra_employee";
    public static final String EXTRA_PARTS = "extra_parts";
    public static final String STRIP = "-";
    public static final String NOT_AVAILABLE = "N/A";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        actionBar();

        initComponent();

        btnClose.setOnClickListener(this);
        btnMaintenance.setOnClickListener(this);
    }

    private void initComponent() {
        tvNik = findViewById(R.id.tv_nik);
        tvName = findViewById(R.id.tv_name);
        tvLocation = findViewById(R.id.tv_location);
        tvBranch = findViewById(R.id.tv_branch);
        tvSales = findViewById(R.id.tv_sales);
        tvSerial = findViewById(R.id.tv_serial);
        tvBrand = findViewById(R.id.tv_brand);
        tvCategory = findViewById(R.id.tv_category);
        tvProcessor = findViewById(R.id.tv_processor);
        tvSystem = findViewById(R.id.tv_os);
        tvHdd = findViewById(R.id.tv_hdd);
        tvSsd = findViewById(R.id.tv_ssd);
        tvRam = findViewById(R.id.tv_ram);
        btnMaintenance = findViewById(R.id.btn_maintenance);
        btnClose = findViewById(R.id.btn_close);
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
        mTitleTextView.setText(getString(R.string.apbar_information));
        mTitleTextView.setTextAppearance(getApplicationContext(), android.R.style.TextAppearance_DeviceDefault_Large);
        mTitleTextView.setTextColor(Color.WHITE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Asset asset = getIntent().getParcelableExtra(EXTRA_ASSET);
        if (asset != null) {
            tvSales.setText(asset.getSalesOrder());
            tvSerial.setText(asset.getSerialNumber());
            tvBrand.setText(asset.getBrand());
            tvCategory.setText(asset.getType());
            listService = asset.getOthers();
        }

        Employee employee = getIntent().getParcelableExtra(EXTRA_EMPLOYEE);
        if (employee != null) {
            if (employee.getNik() == null) {
                tvNik.setText(STRIP);
            } else {
                tvNik.setText(employee.getNik());
            }
            if (employee.getName().equals("")) {
                tvName.setText(STRIP);
            } else {
                tvName.setText(employee.getName());
            }

            tvLocation.setText(employee.getLocation());
            tvBranch.setText(employee.getBranch());
        }

        Part part = getIntent().getParcelableExtra(EXTRA_PARTS);
        if (part != null) {
            if (part.getHDD().equals("")) {
                tvHdd.setText(NOT_AVAILABLE);
            } else {
                tvHdd.setText(part.getHDD());
            }
            if (part.getSSD().equals("")) {
                tvSsd.setText(NOT_AVAILABLE);
            } else {
                tvSsd.setText(part.getSSD());
            }

            tvProcessor.setText(part.getProcessor());
            tvSystem.setText(part.getOS());
            tvRam.setText(part.getRAM());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_maintenance:
                Bundle extras = new Bundle();
                extras.putString("serialNumber", tvSerial.getText().toString());
                extras.putString("Processor", tvProcessor.getText().toString());
                extras.putString("OS", tvSystem.getText().toString());
                extras.putString("HDD", tvHdd.getText().toString());
                extras.putString("SSD", tvSsd.getText().toString());
                extras.putString("RAM", tvRam.getText().toString());
                extras.putStringArray("others", listService);

                Intent goToMaintenance = new Intent(InformationActivity.this, MaintenanceActivity.class);
                goToMaintenance.putExtras(extras);
                startActivity(goToMaintenance);
                break;
            case R.id.btn_close:
                Intent goToHome = new Intent(InformationActivity.this, MainActivity.class);
                startActivity(goToHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                break;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
