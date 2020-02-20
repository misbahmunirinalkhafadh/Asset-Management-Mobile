package com.mii.assetmanagement.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.mii.assetmanagement.R;
import com.mii.assetmanagement.model.Asset;
import com.mii.assetmanagement.model.Employee;

public class ExchangeEmployeeActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvSales, tvCompany, tvBranch, tvBrand, tvOldNik, tvOldName, tvNewName;
    private EditText etNewNik, etReason;
    private Button btnSubmit, btnClose;

    public static final String EXTRA_ASSET = "extra_asset";
    public static final String EXTRA_EMPLOYEE = "extra_employee";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_employee);
        actionBar();
        initComponent();
        if (tvNewName == null){
            tvNewName.setText("-");
        }

        btnSubmit.setOnClickListener(this);
        btnClose.setOnClickListener(this);
    }

    private void initComponent() {
        tvSales = findViewById(R.id.tv_sales);
        tvCompany = findViewById(R.id.tv_company);
        tvBranch = findViewById(R.id.tv_branch);
        tvBrand = findViewById(R.id.tv_brand);
        tvOldNik = findViewById(R.id.tv_old_nik);
        tvOldName = findViewById(R.id.tv_old_name);
        tvNewName = findViewById(R.id.tv_new_name);
        etNewNik = findViewById(R.id.et_new_nik);
        etReason = findViewById(R.id.et_result);
        btnSubmit = findViewById(R.id.btn_submit);
        btnClose = findViewById(R.id.btn_close);
    }

    private void actionBar() {
        ActionBar actionBar = getSupportActionBar();
        AppCompatTextView mTitleTextView = new AppCompatTextView(getApplicationContext());
        mTitleTextView.setSingleLine();
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        actionBar.setCustomView(mTitleTextView, layoutParams);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_HOME_AS_UP);
        mTitleTextView.setText(getString(R.string.appbar_exchange_employee));
        mTitleTextView.setTextAppearance(getApplicationContext(), android.R.style.TextAppearance_DeviceDefault_Large);
        mTitleTextView.setTextColor(Color.WHITE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Asset asset = getIntent().getParcelableExtra(EXTRA_ASSET);
        tvSales.setText(asset.getSalesOrder());
        tvBrand.setText(asset.getBrand());

        Employee employee = getIntent().getParcelableExtra(EXTRA_EMPLOYEE);
        tvCompany.setText("-");
        tvBranch.setText(employee.getBranch());
        tvOldNik.setText(employee.getNik());
        tvOldName.setText(employee.getName());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_submit:
                saveState();
                break;
            case R.id.btn_close:
                Intent goToMain = new Intent(ExchangeEmployeeActivity.this, MainActivity.class);
                startActivity(goToMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                break;
        }
    }

    private void saveState() {
        String reason = etReason.getText().toString().trim();
        if (reason.isEmpty()) {
            etReason.requestFocus();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Form required!")
                    .setMessage("Field result can't empty")
                    .setPositiveButton("OKE", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .show();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Confirmation")
                    .setMessage("Are you sure, want you save this maintenance?")
                    .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

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
                    final Dialog dialog = new Dialog(ExchangeEmployeeActivity.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.layout_dialog_success);
                    dialog.show();
                    Button dialogButton = dialog.findViewById(R.id.btn_continue);
                    dialogButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            Intent goToMain = new Intent(ExchangeEmployeeActivity.this, MainActivity.class);
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
