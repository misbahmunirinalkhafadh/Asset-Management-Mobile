package com.mii.assetmanagement.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.ViewModelProviders;

import com.mii.assetmanagement.R;
import com.mii.assetmanagement.SharedPrefManager;
import com.mii.assetmanagement.model.Asset;
import com.mii.assetmanagement.model.Employee;
import com.mii.assetmanagement.model.ExchangeRequest;
import com.mii.assetmanagement.viewmodel.ExchangeViewModel;

import es.dmoral.toasty.Toasty;

public class ExchangeAssetActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvSales, tvCompany, tvBranch, tvSerial;
    private EditText etReason;
    private Button btnSubmit, btnClose;
    private String userNik;
    private SharedPrefManager sharedPrefManager;
    private ExchangeViewModel exchangeViewModel;
    public static final String EXTRA_ASSET = "extra_asset";
    public static final String EXTRA_EMPLOYEE = "extra_employee";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_asset);
        actionBar();
        sharedPrefManager = new SharedPrefManager(this);
        exchangeViewModel = ViewModelProviders.of(this).get(ExchangeViewModel.class);
        initComponent();

        btnSubmit.setOnClickListener(this);
        btnClose.setOnClickListener(this);
    }

    private void initComponent() {
        tvSales = findViewById(R.id.tv_sales);
        tvCompany = findViewById(R.id.tv_company);
        tvBranch = findViewById(R.id.tv_branch);
        tvSerial = findViewById(R.id.tv_serial);
        etReason = findViewById(R.id.et_reason);
        btnSubmit = findViewById(R.id.btn_submit);
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
        mTitleTextView.setText(getString(R.string.appbar_exchange_asset));
        mTitleTextView.setTextAppearance(getApplicationContext(), android.R.style.TextAppearance_DeviceDefault_Large);
        mTitleTextView.setTextColor(Color.WHITE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Asset asset = getIntent().getParcelableExtra(EXTRA_ASSET);
        tvSales.setText(asset.getSalesOrder());
        tvCompany.setText(asset.getCompany());
        tvSerial.setText(asset.getSerialNumber());

        Employee employee = getIntent().getParcelableExtra(EXTRA_EMPLOYEE);
        userNik = employee.getNik();
        tvBranch.setText(employee.getBranch());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                InputMethodManager imm = (InputMethodManager) ExchangeAssetActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(etReason.getWindowToken(), 0);
                saveState();
                break;
            case R.id.btn_close:
                Intent goToExchange = new Intent(ExchangeAssetActivity.this, ExchangeActivity.class);
                startActivity(goToExchange.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                break;
        }
    }

    private void saveState() {
        String reason = etReason.getText().toString().trim();
        if (reason.isEmpty()) {
            etReason.requestFocus();
            Toasty.error(this, "Required!, result can't empty", Toast.LENGTH_SHORT, true).show();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Confirmation")
                    .setMessage("Are you sure, want you submit this request?")
                    .setCancelable(false)
                    .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ExchangeRequest request = new ExchangeRequest();
                            request.setRequester(sharedPrefManager.getSpNik().trim());
                            request.setSales(tvSales.getText().toString().trim());
                            request.setSerial(tvSerial.getText().toString().trim());
                            request.setOldUserAsset(userNik.trim());
                            request.setReason(etReason.getText().toString().trim());

                            exchangeViewModel.saveExchangeAsset(request);
                            showDialogSuccess();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }).show();
        }
    }

    private void showDialogSuccess() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!isFinishing()) {
                    final Dialog dialog = new Dialog(ExchangeAssetActivity.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.layout_dialog_success);
                    dialog.show();
                    Button dialogButton = dialog.findViewById(R.id.btn_continue);
                    dialogButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            Intent goToMain = new Intent(ExchangeAssetActivity.this, MainActivity.class);
                            startActivity(goToMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                        }
                    });
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
