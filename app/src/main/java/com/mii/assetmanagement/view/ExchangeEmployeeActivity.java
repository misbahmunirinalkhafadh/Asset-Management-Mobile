package com.mii.assetmanagement.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.util.Objects;

import es.dmoral.toasty.Toasty;

public class ExchangeEmployeeActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvSales, tvCompany, tvBranch, tvBrand, tvOldNik, tvOldName, tvNewName;
    private EditText etNewNik, etReason;
    private ImageView ivInfo;
    private Button btnSubmit, btnClose;
    private String serialNumber;
    private String nikNewUser;
    private SharedPrefManager sharedPrefManager;
    private ExchangeViewModel exchangeViewModel;
    private ProgressDialog progressDialog;
    public static final String EXTRA_ASSET = "extra_asset";
    public static final String EXTRA_EMPLOYEE = "extra_employee";
    public static final String STRIP = "-";
    public static final String INVALID_NUMBER = "Invalid number";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_employee);
        actionBar();
        initComponent();

        sharedPrefManager = new SharedPrefManager(this);
        exchangeViewModel = ViewModelProviders.of(this).get(ExchangeViewModel.class);

        eventInputNik();
        callDataEmpl();

        ivInfo.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        btnClose.setOnClickListener(this);
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
        mTitleTextView.setText(getString(R.string.appbar_exchange_employee));
        mTitleTextView.setTextAppearance(getApplicationContext(), android.R.style.TextAppearance_DeviceDefault_Large);
        mTitleTextView.setTextColor(Color.WHITE);
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
        etReason = findViewById(R.id.et_reason);
        ivInfo = findViewById(R.id.iv_info);
        btnSubmit = findViewById(R.id.btn_submit);
        btnClose = findViewById(R.id.btn_close);

        tvNewName.setText(STRIP);
    }

    private void eventInputNik() {
        etNewNik.setInputType(InputType.TYPE_CLASS_NUMBER);
        etNewNik.setOnEditorActionListener((v, actionId, event) -> {
            String value = v.getText().toString().trim();
            if (value.isEmpty()) {
                etNewNik.setError("Required");
                tvNewName.setText(STRIP);
                tvNewName.setTextColor(Color.GRAY);
                etNewNik.getText().clear();
            } else {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || event.getAction() == KeyEvent.ACTION_DOWN
                        && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    showLoading();
                    tvNewName.setText("");
                    exchangeViewModel.setDataEmpl(Integer.parseInt(value));
                    hideSoftKeyboard(this);
                    return true;
                }
            }
            return false;
        });
    }

    private void showLoading() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Please wait...");
        }
        progressDialog.show();
    }

    private void callDataEmpl() {
        exchangeViewModel.getDataEmployee().observe(this, employeeResult -> {
            nikNewUser = String.valueOf(employeeResult.getNik()).trim();
            if (employeeResult.isError()) {
                tvNewName.setText(INVALID_NUMBER);
                tvNewName.setTextColor(Color.RED);
                etNewNik.getText().clear();
                Toasty.error(ExchangeEmployeeActivity.this, "Not Available", Toast.LENGTH_SHORT, true).show();
            } else {
                tvNewName.append(employeeResult.getEmplName());
                tvNewName.setTextColor(Color.BLACK);
                etNewNik.clearFocus();
                Toasty.success(ExchangeEmployeeActivity.this, "Available!", Toast.LENGTH_SHORT, true).show();
            }
            dismissLoading();
        });
    }

    private void dismissLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(Objects.requireNonNull(activity.getCurrentFocus()).getWindowToken(), 0);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Asset asset = getIntent().getParcelableExtra(EXTRA_ASSET);
        if (asset != null) {
            tvSales.setText(asset.getSalesOrder());
            tvCompany.setText(asset.getCompany());
            tvBrand.setText(asset.getBrand());
            serialNumber = asset.getSerialNumber();
        }

        Employee employee = getIntent().getParcelableExtra(EXTRA_EMPLOYEE);
        if (employee != null) {
            tvBranch.setText(employee.getBranch());
            tvOldNik.setText(employee.getNik());
            tvOldName.setText(employee.getName());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_info:
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle("Information");
                dialog.setMessage("Press enter on keyboard after input Employee NIK for showing Employee Name");
                dialog.setPositiveButton("OKE", (dialog1, which) -> dialog1.dismiss());
                dialog.show();
                break;
            case R.id.btn_submit:
                InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(etNewNik.getWindowToken(), 0);
                    imm.hideSoftInputFromWindow(etReason.getWindowToken(), 0);
                }
                saveState();
                break;
            case R.id.btn_close:
                Intent goToExchange = new Intent(ExchangeEmployeeActivity.this, ExchangeActivity.class);
                startActivity(goToExchange.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                break;
        }
    }

    private void saveState() {
        String reason = etReason.getText().toString().trim();
        String name = tvNewName.getText().toString().trim();
        if (name.equals(STRIP) || name.equals(INVALID_NUMBER)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("User Name Invalid!")
                    .setMessage("New user name employee invalid, please insert new user nik!")
                    .setPositiveButton("OKE", (dialog, which) -> {
                        dialog.dismiss();
                        etNewNik.requestFocus();
                    })
                    .show();
        } else if (reason.isEmpty()) {
            etReason.requestFocus();
            Toasty.error(this, "Required!, reason can't empty", Toast.LENGTH_SHORT, true).show();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Confirmation")
                    .setMessage("Are you sure, want you change user asset?")
                    .setCancelable(false)
                    .setPositiveButton("Save", (dialog, which) -> {
                        ExchangeRequest request = new ExchangeRequest();
                        request.setRequester(sharedPrefManager.getSpNik());
                        request.setSales(tvSales.getText().toString().trim());
                        request.setSerial(serialNumber);
                        request.setOldUserAsset(tvOldNik.getText().toString().trim());
                        request.setNewUserAsset(nikNewUser);
                        request.setReason(etReason.getText().toString().trim());

                        exchangeViewModel.saveExchangeEmpl(request);
                        showDialogSuccess();
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> dialog.cancel()).show();
        }
    }

    private void showDialogSuccess() {
        runOnUiThread(() -> {
            if (!isFinishing()) {
                final Dialog dialog = new Dialog(ExchangeEmployeeActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.layout_dialog_success);
                dialog.show();
                Button dialogButton = dialog.findViewById(R.id.btn_continue);
                dialogButton.setOnClickListener(v -> {
                    dialog.dismiss();
                    Intent goToMain = new Intent(ExchangeEmployeeActivity.this, MainActivity.class);
                    startActivity(goToMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
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
