package com.mii.assetmanagement.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mii.assetmanagement.adapter.MainChecklistAdapter;
import com.mii.assetmanagement.model.MaintenanceRequest;
import com.mii.assetmanagement.R;
import com.mii.assetmanagement.SharedPrefManager;
import com.mii.assetmanagement.viewmodel.MaintenanceViewModel;

import java.util.ArrayList;
import java.util.List;

public class MaintenanceActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvProcessor, tvSystem, tvHdd, tvSsd, tvRam;
    CheckBox cbProcessor, cbSystem, cbHdd, cbSsd, cbRam;
    RecyclerView rvService;
    EditText etIpAddress, etUsername, etComputer, etResult;
    Button btnBack, btnSubmit;

    Context mContext;
    SharedPrefManager sharedPrefManager;

    private MaintenanceViewModel maintenanceViewModel;
    private String serial;
    private String[] service;
    private List<String> serviceList;
    private boolean[] booleanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance);

        mContext = this;
        sharedPrefManager = new SharedPrefManager(this);
        maintenanceViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MaintenanceViewModel.class);

        initComponent();

        Intent intent = getIntent();
        serial = intent.getStringExtra("serialNumber");
        service = intent.getStringArrayExtra("others");
        tvProcessor.setText(intent.getStringExtra("Processor"));
        tvSystem.setText(intent.getStringExtra("OS"));
        tvHdd.setText(intent.getStringExtra("HDD"));
        if (tvHdd.getText().equals("N/A")) {
            tvHdd.setVisibility(View.GONE);
            cbHdd.setVisibility(View.GONE);
        }
        tvSsd.setText(intent.getStringExtra("SSD"));
        if (tvSsd.getText().equals("N/A")) {
            tvSsd.setVisibility(View.GONE);
            cbSsd.setVisibility(View.GONE);
        }
        tvRam.setText(intent.getStringExtra("RAM"));

        fillItems();

        booleanList = new boolean[serviceList.size()];
        rvService.setLayoutManager(new LinearLayoutManager(this));
        rvService.setAdapter(new MainChecklistAdapter(this, serviceList, booleanList));

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
//        StringBuffer result = new StringBuffer();
//        result.append("Maintainer : ").append(sharedPrefManager.getSPNama());
//        result.append("\nSerial Number : ").append(serial);
//
//        Toast.makeText(mContext, result.toString(), Toast.LENGTH_LONG).show();

        MaintenanceRequest request = new MaintenanceRequest();
        request.setMaintainer(sharedPrefManager.getSPNama());
        request.setSn(serial);
        request.setService(booleanList);
        request.setNames(service);
        request.setProcessor(cbProcessor.isChecked());
        request.setRam(cbRam.isChecked());
        request.setHdd(cbHdd.isChecked());
        request.setSsd(cbSsd.isChecked());
        request.setOs(cbSystem.isChecked());
        request.setIp(etIpAddress.getText().toString());
        request.setUsername(etUsername.getText().toString());
        request.setComputerName(etComputer.getText().toString());
        request.setResult(etResult.getText().toString());

        maintenanceViewModel.apiSaveMaintenance(request);
    }

    private void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Confirmation").setMessage("Are you sure, want you save this maintenance?")
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        maintenanceRequest();

                        showAlertSuccess();



                        Toast.makeText(mContext, "Successfull", Toast.LENGTH_LONG).show();

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();
    }

    private void showAlertSuccess() {
        final Dialog alert = new Dialog(mContext, android.R.style.Theme_Light);
        alert.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alert.setContentView(R.layout.layout_dialog_success);
        alert.setCancelable(false);
        alert.show();
        
        Button btnClose = alert.findViewById(R.id.btn_close);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent submit = new Intent(MaintenanceActivity.this, MainActivity.class);
                startActivity(submit.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
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
                confirmDialog();
                break;
        }
    }
}
