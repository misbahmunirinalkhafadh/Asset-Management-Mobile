package com.mii.assetmanagement.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mii.assetmanagement.R;
import com.mii.assetmanagement.model.Asset;
import com.mii.assetmanagement.model.Part;
import com.mii.assetmanagement.model.Employee;

public class InformationActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvNik, tvName, tvLocation, tvBranch;
    private TextView tvSales, tvSerial, tvBrand, tvType;
    public TextView tvProcessor, tvSystem, tvHdd, tvSsd, tvRam;
    private String[] listService;
    private Button btnMaintenance, btnClose, btnBack;
    private LinearLayout llProgress;
    private ScrollView svInformation;
    public static final String EXTRA_ASSET = "extra_asset";
    public static final String EXTRA_EMPLOYEE = "extra_employee";
    public static final String EXTRA_PARTS = "extra_parts";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        initComponent();
        loading();

        getSupportActionBar().hide();

        btnBack.setOnClickListener(this);
        btnClose.setOnClickListener(this);
        btnMaintenance.setOnClickListener(this);
    }

    private void loading() {
        llProgress.setVisibility(View.VISIBLE);
        svInformation.setVisibility(View.GONE);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                llProgress.setVisibility(View.GONE);
                svInformation.setVisibility(View.VISIBLE);
            }
        }, 500);
    }

    private void initComponent() {
        llProgress = findViewById(R.id.ll_progress);
        tvNik = findViewById(R.id.tv_nik);
        tvName = findViewById(R.id.tv_name);
        tvLocation = findViewById(R.id.tv_location);
        tvBranch = findViewById(R.id.tv_branch);
        tvSales = findViewById(R.id.tv_sales);
        tvSerial = findViewById(R.id.tv_serial);
        tvBrand = findViewById(R.id.tv_brand);
        tvType = findViewById(R.id.tv_item);
        tvProcessor = findViewById(R.id.tv_processor);
        tvSystem = findViewById(R.id.tv_os);
        tvHdd = findViewById(R.id.tv_hdd);
        tvSsd = findViewById(R.id.tv_ssd);
        tvRam = findViewById(R.id.tv_ram);
        btnBack = findViewById(R.id.btn_back);
        btnMaintenance = findViewById(R.id.btn_maintenance);
        btnClose = findViewById(R.id.btn_close);
        svInformation = findViewById(R.id.sv_information);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Asset asset = getIntent().getParcelableExtra(EXTRA_ASSET);
        tvSales.setText(asset.getSalesOrder());
        tvSerial.setText(asset.getSerialNumber());
        tvBrand.setText(asset.getBrand());
        tvType.setText(asset.getType());
        listService = asset.getOthers();

        Employee employee = getIntent().getParcelableExtra(EXTRA_EMPLOYEE);
        tvNik.setText(employee.getNik());
        tvName.setText(employee.getName());
        tvLocation.setText(employee.getLocation());
        tvBranch.setText(employee.getBranch());

        Part part = getIntent().getParcelableExtra(EXTRA_PARTS);
        String hdd = part.getHDD();
        String ssd = part.getSSD();
        if (hdd.equals("")) {
            tvHdd.setText("N/A");
        } else {
            tvHdd.setText(hdd);
        }
        if (ssd.equals("")) {
            tvSsd.setText("N/A");
        } else {
            tvSsd.setText(ssd);
        }
        tvProcessor.setText(part.getProcessor());
        tvSystem.setText(part.getOS());
        tvRam.setText(part.getRAM());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                onBackPressed();
                break;
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
}
