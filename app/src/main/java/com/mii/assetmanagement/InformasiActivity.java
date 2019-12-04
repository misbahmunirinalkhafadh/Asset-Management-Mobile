package com.mii.assetmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;

public class InformasiActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvNik, tvName, tvLocation, tvBranch;
    TextView tvSales, tvSerial, tvBrand, tvType;
    TextView tvProcessor, tvSystem, tvHdd, tvSsd, tvRam;
    Button btnMaintenance, btnClose, btnBack;
    String[] listService;
    ScrollView llInformation;
    private ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informasi);

        initComponent();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loading.setVisibility(View.GONE);
                llInformation.setVisibility(View.VISIBLE);
            }
        }, 1000);

        loadInformation();

        Log.v("Information Act Others", Arrays.toString(listService));

        btnBack.setOnClickListener(this);
        btnClose.setOnClickListener(this);
        btnMaintenance.setOnClickListener(this);
    }

    private void loadInformation() {
        loading.setVisibility(View.VISIBLE);
        llInformation.setVisibility(View.GONE);

        Intent intent = getIntent();
        tvNik.setText(intent.getStringExtra("nik"));
        tvName.setText(intent.getStringExtra("name"));
        tvLocation.setText(intent.getStringExtra("location"));
        tvBranch.setText(intent.getStringExtra("branch"));
        tvSales.setText(intent.getStringExtra("salesOrder"));
        tvSerial.setText(intent.getStringExtra("serialNumber"));
        tvBrand.setText(intent.getStringExtra("brand"));
        tvType.setText(intent.getStringExtra("type"));
        tvProcessor.setText(intent.getStringExtra("Processor"));
        tvSystem.setText(intent.getStringExtra("OS"));
        tvHdd.setText(intent.getStringExtra("HDD"));
        tvSsd.setText(intent.getStringExtra("SSD"));
        tvRam.setText(intent.getStringExtra("RAM"));
        listService = intent.getStringArrayExtra("others");
    }

    private void initComponent() {
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
        loading = findViewById(R.id.loading);
        llInformation = findViewById(R.id.ll_information);
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

                Intent goToMaintenance = new Intent(InformasiActivity.this, MaintenanceActivity.class);
                goToMaintenance.putExtras(extras);
                startActivity(goToMaintenance);
                break;
            case R.id.btn_close:
                Intent goToHome = new Intent(InformasiActivity.this, MainActivity.class);
                startActivity(goToHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                break;
        }
    }
}
