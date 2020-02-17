package com.mii.assetmanagement.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.mii.assetmanagement.R;
import com.mii.assetmanagement.model.EmployeeResult;
import com.mii.assetmanagement.model.SalesOrder;
import com.mii.assetmanagement.viewmodel.RequestViewModel;

public class RequestActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etSalesOrder, etNik, etSearch;
    private TextView tvCompanyName, tvEmpName;
    private LinearLayout layoutUser;
    private Button btnBack;
    private ProgressDialog progressDialog;
    private RequestViewModel requestViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        initComponent();

        requestViewModel = ViewModelProviders.of(this).get(RequestViewModel.class);

        eventInputSo();
        eventInputNik();
        callDataSO();
        callDataEmpl();

        btnBack.setOnClickListener(this);
        etSearch.setOnClickListener(this);
    }


    private void eventInputNik() {
        etNik.setInputType(InputType.TYPE_CLASS_NUMBER);
        etNik.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String value = v.getText().toString().trim();
                int val = Integer.parseInt(value);
                if (value.isEmpty()) {
                    etNik.setError("Required");
                    tvEmpName.setText("-");
                    tvEmpName.setTextColor(Color.GRAY);
                    etNik.getText().clear();
                } else {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH
                            || actionId == EditorInfo.IME_ACTION_DONE
                            || event.getAction() == KeyEvent.ACTION_DOWN
                            && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                        showLoading();
                        tvEmpName.setText("");
                        requestViewModel.setDataEmpl(val);
                        return true;
                    }
                }
                return false;
            }
        });
    }

    private void eventInputSo() {
        etSalesOrder.setInputType(InputType.TYPE_CLASS_NUMBER);
        etSalesOrder.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String val = v.getText().toString().trim();
                if (val.isEmpty()) {
                    etSalesOrder.setError("Required");
                    tvCompanyName.setText("-");
                    tvCompanyName.setTextColor(Color.GRAY);
                    layoutUser.setVisibility(View.GONE);
                    etNik.getText().clear();
                } else {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH
                            || actionId == EditorInfo.IME_ACTION_DONE
                            || event.getAction() == KeyEvent.ACTION_DOWN
                            && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                        showLoading();
                        tvCompanyName.setText("");
                        requestViewModel.setDataSO(val);
                        return true;
                    }
                }
                return false;
            }
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
        requestViewModel.getDataEmployee().observe(this, new Observer<EmployeeResult>() {
            @Override
            public void onChanged(EmployeeResult employeeResult) {
                Log.v("test", "employeeResult name" + employeeResult.getEmplName());
                if (employeeResult.isError()) {
                    Log.i("EmployeeResult Result", "Salah");
                    tvEmpName.setText(R.string.invalid);
                    tvEmpName.setTextColor(Color.RED);
                    etNik.getText().clear();
                } else {
                    Log.i("EmployeeResult Result", "Benar");
                    tvEmpName.append(employeeResult.getEmplName());
                    tvEmpName.setTextColor(Color.BLACK);
                }
                dismissLoading();
            }
        });
    }

    private void callDataSO() {
        requestViewModel.getDataSO().observe(this, new Observer<SalesOrder>() {
            @Override
            public void onChanged(SalesOrder salesOrder) {
                String asset_type = "Resource";
                if (salesOrder.getError()) {
                    Log.i("Sales Order Result", "Kosong");
                    tvCompanyName.setText(R.string.invalid);
                    tvCompanyName.setTextColor(Color.RED);
                    layoutUser.setVisibility(View.GONE);
                    etNik.getText().clear();
                } else {
                    Log.i("Sales Order Result", "ADA");
                    tvCompanyName.append(salesOrder.getCustomerName());
                    tvCompanyName.setTextColor(Color.BLACK);
                    //Set V isibility Employee Layout
                    if (salesOrder.getAssetType().equals(asset_type)) {
                        layoutUser.setVisibility(View.VISIBLE);
                    } else {
                        layoutUser.setVisibility(View.GONE);
                        etNik.getText().clear();
                    }
                }
                dismissLoading();
            }
        });
    }

    private void dismissLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    private void initComponent() {
        etSearch = findViewById(R.id.et_search);
        etSalesOrder = findViewById(R.id.et_sales_order);
        etNik = findViewById(R.id.et_nik);
        tvCompanyName = findViewById(R.id.tv_company);
        tvEmpName = findViewById(R.id.tv_name);
        layoutUser = findViewById(R.id.ll_user);
        btnBack = findViewById(R.id.btn_back);

        layoutUser.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.et_sales_order:
//                Intent goToSearchSalesOrder = new Intent(this, SearchAssetActivity.class);
//                startActivity(goToSearchSalesOrder);
//                break;
            case R.id.et_search:
                Bundle extras = new Bundle();
                Intent goToSearchAsset = new Intent(RequestActivity.this, SearchAssetActivity.class);
                goToSearchAsset.putExtras(extras);
                startActivity(goToSearchAsset);
                break;
            case R.id.btn_back:
                onBackPressed();
                break;
        }
    }
}
