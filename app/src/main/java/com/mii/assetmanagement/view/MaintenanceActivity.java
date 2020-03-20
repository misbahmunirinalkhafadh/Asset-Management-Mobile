package com.mii.assetmanagement.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mii.assetmanagement.R;
import com.mii.assetmanagement.SharedPrefManager;
import com.mii.assetmanagement.adapter.MainChecklistAdapter;
import com.mii.assetmanagement.model.MaintenanceRequest;
import com.mii.assetmanagement.viewmodel.MaintenanceViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class MaintenanceActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvProcessor, tvSystem, tvHdd, tvSsd, tvRam;
    private CheckBox cbProcessor, cbSystem, cbHdd, cbSsd, cbRam;
    private RecyclerView rvService;
    private EditText etIpAddress, etUsername, etComputer, etResult;
    private Button btnSubmit;
    private SharedPrefManager sharedPrefManager;
    private MaintenanceViewModel maintenanceViewModel;
    private String serial;
    private String[] service;
    private List<String> serviceList;
    private boolean[] booleanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance);
        actionBar();

        sharedPrefManager = new SharedPrefManager(this);
        maintenanceViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MaintenanceViewModel.class);

        initComponent();
        setVariableData();

        booleanList = new boolean[serviceList.size()];
        rvService.setLayoutManager(new LinearLayoutManager(this));
        rvService.setAdapter(new MainChecklistAdapter(this, serviceList, booleanList));

        btnSubmit.setOnClickListener(this);
    }

    private void setVariableData() {
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

        serviceList = new ArrayList<>();
        Collections.addAll(serviceList, service);
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

        btnSubmit = findViewById(R.id.btn_submit);
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
        mTitleTextView.setText(getString(R.string.apbar_maintenance));
        mTitleTextView.setTextAppearance(getApplicationContext(), android.R.style.TextAppearance_DeviceDefault_Large);
        mTitleTextView.setTextColor(Color.WHITE);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_submit) {
            InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(etResult.getWindowToken(), 0);
            }
            validation();
        }
    }

    private void validation() {
        String result = etResult.getText().toString();
        if (result.isEmpty()) {
            Toasty.error(this, "Required!, result can't empty", Toast.LENGTH_SHORT, true).show();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Confirmation")
                    .setMessage("Are you sure, want you save this maintenance?")
                    .setPositiveButton("Save", (dialog, which) -> {
                        MaintenanceRequest request = new MaintenanceRequest();
                        request.setMaintainer(sharedPrefManager.getSpNik());
                        request.setSn(serial);
                        request.setService(booleanList);
                        request.setNames(service);
                        request.setProcessor(cbProcessor.isChecked());
                        request.setRam(cbRam.isChecked());
                        request.setHdd(cbHdd.isChecked());
                        request.setSsd(cbSsd.isChecked());
                        request.setOs(cbSystem.isChecked());

                        String ipValue;
                        if (etIpAddress.getText().toString().isEmpty()) {
                            ipValue = "N/A";
                        } else {
                            ipValue = etIpAddress.getText().toString();
                        }
                        request.setIp(ipValue);

                        String unameValue;
                        if (etUsername.getText().toString().isEmpty()) {
                            unameValue = "N/A";
                        } else {
                            unameValue = etUsername.getText().toString();
                        }
                        request.setUsername(unameValue);

                        String comName;
                        if (etComputer.getText().toString().isEmpty()) {
                            comName = "N/A";
                        } else {
                            comName = etComputer.getText().toString();
                        }
                        request.setComputerName(comName);
                        request.setResult(etResult.getText().toString());
                        maintenanceViewModel.setMaintenance(request);

                        showDialogSuccess();
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> dialog.cancel()).show();
        }
    }

    private void showDialogSuccess() {
        runOnUiThread(() -> {
            if (!isFinishing()) {
                final Dialog dialog = new Dialog(MaintenanceActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.layout_dialog_success);
                dialog.show();
                Button dialogButton = dialog.findViewById(R.id.btn_continue);
                dialogButton.setOnClickListener(v -> {
                    dialog.dismiss();
                    Intent submit = new Intent(MaintenanceActivity.this, MainActivity.class);
                    startActivity(submit.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                });
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
