package com.mii.assetmanagement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mii.assetmanagement.apihelper.ApiService;
import com.mii.assetmanagement.apihelper.UtilsApi;
import com.mii.assetmanagement.model.MaintenanceRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MaintenanceActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvProcessor, tvSystem, tvHdd, tvSsd, tvRam;
    CheckBox cbProcessor, cbSystem, cbHdd, cbSsd, cbRam;
    RecyclerView rvService;
    EditText etIpAddress, etUsername, etComputer, etResult;
    Button btnBack, btnSubmit;

    private String serial;
    private String[] service;
    private boolean[] valueService;
    private List<String> serviceList;
    private Map<String, Boolean> punyaDimas;

    Context mContext;
    ApiService mApiService;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance);

        mContext = this;
        mApiService = UtilsApi.getApiService();
        sharedPrefManager = new SharedPrefManager(this);

        initComponent();

        Intent intent = getIntent();
        serial = intent.getStringExtra("serialNumber");
        service = intent.getStringArrayExtra("others");
        tvProcessor.setText(intent.getStringExtra("Processor"));
        tvSystem.setText(intent.getStringExtra("OS"));
        tvHdd.setText(intent.getStringExtra("HDD"));
        tvSsd.setText(intent.getStringExtra("SSD"));
        tvRam.setText(intent.getStringExtra("RAM"));

        fillItems();

        rvService.setLayoutManager(new LinearLayoutManager(this));
        rvService.setAdapter(new MainChecklistAdapter(this, serviceList));

        btnBack.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
    }

    private void fillItems() {
        serviceList = new ArrayList<>();
        for (String isi : service) {
            serviceList.add(isi);
        }
    }

    private void initComponent() {
        tvProcessor = findViewById(R.id.tv_processor);
        tvSystem = findViewById(R.id.tv_os);
        tvHdd = findViewById(R.id.tv_hdd);
        tvSsd = findViewById(R.id.tv_ssd);
        tvRam = findViewById(R.id.tv_ram);
        cbProcessor = findViewById(R.id.cb_processor);
        cbSystem = findViewById(R.id.cb_os);
        cbHdd = findViewById(R.id.cb_hdd);
        cbSsd = findViewById(R.id.cb_ssd);
        cbRam = findViewById(R.id.cb_ram);
        rvService = findViewById(R.id.rv_main_checklist);
        etIpAddress = findViewById(R.id.et_ipAddress);
        etUsername = findViewById(R.id.et_username);
        etComputer = findViewById(R.id.et_computerName);
        etResult = findViewById(R.id.et_result);

        btnBack = findViewById(R.id.btn_back);
        btnSubmit = findViewById(R.id.btn_submit);
    }

    private void maintenanceRequest() {
        StringBuffer result = new StringBuffer();
        result.append("Maintainer : ").append(sharedPrefManager.getSPNama());
        result.append("\nSerial Number : ").append(serial);

        Toast.makeText(MaintenanceActivity.this, result.toString(),
                Toast.LENGTH_LONG).show();

        MaintenanceRequest request = new MaintenanceRequest();
        request.setMaintainer(sharedPrefManager.getSPNama());
        request.setSn(serial);
        request.setService(valueService);
        request.setProcessor(cbProcessor.isChecked());
        request.setRam(cbRam.isChecked());
        request.setHdd(cbHdd.isChecked());
        request.setSsd(cbSsd.isChecked());
        request.setOs(cbSystem.isChecked());
        request.setIp(etIpAddress.getText().toString());
        request.setUsername(etUsername.getText().toString());
        request.setComputerName(etComputer.getText().toString());
        request.setResult(etResult.getText().toString());


        mApiService.saveMaintenance(request).enqueue(new Callback<MaintenanceRequest>() {
            @Override
            public void onResponse(Call<MaintenanceRequest> call, Response<MaintenanceRequest> response) {
                if (response.isSuccessful()) {
//                    showResponse(response.body().toString());
                    Log.i("post submitted to API.", response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<MaintenanceRequest> call, Throwable t) {
                Log.e("onFailure", t.getMessage());
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                onBackPressed();
                break;
            case R.id.btn_submit:
                maintenanceRequest();
//                Intent submit = new Intent(MaintenanceActivity.this, MainActivity.class);
//                startActivity(submit);
                break;
        }
    }

}
