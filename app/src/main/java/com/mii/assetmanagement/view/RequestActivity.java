package com.mii.assetmanagement.view;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.mii.assetmanagement.R;
import com.mii.assetmanagement.model.SalesOrder;
import com.mii.assetmanagement.viewmodel.RequestViewModel;

import java.util.ArrayList;

public class RequestActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etSalesOrder;
    private TextView tvCompanyName;
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
        loading();
        callData();

        btnBack.setOnClickListener(this);
    }

    private void callData() {
        requestViewModel.getListSalesOrder().observe(this, new Observer<ArrayList<SalesOrder>>() {
            @Override
            public void onChanged(ArrayList<SalesOrder> salesOrders) {
                boolean tes = salesOrders.isEmpty();
                if (tes) {
                    Log.i("Sales Order Result", "Kosong");
                    tvCompanyName.setText(R.string.invalid);
                    tvCompanyName.setTextColor(Color.RED);
                    progressDialog.dismiss();
                } else {
                    for (SalesOrder salesOrder : salesOrders) {
                        tvCompanyName.append(salesOrder.getCustomerName());
                    }
                    progressDialog.dismiss();
                }
            }
        });
    }

    private void loading() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
    }

    private void eventInputSo() {
        etSalesOrder.setInputType(InputType.TYPE_CLASS_NUMBER);
        etSalesOrder.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String soNumber = v.getText().toString();
                String val = etSalesOrder.getText().toString().trim();
                if (val.isEmpty()) {
                    etSalesOrder.setError("Required");
                    tvCompanyName.setText("-");
                    tvCompanyName.setTextColor(Color.GRAY);
                } else {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH
                            || actionId == EditorInfo.IME_ACTION_DONE
                            || event.getAction() == KeyEvent.ACTION_DOWN
                            && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                        progressDialog.show();
                        tvCompanyName.setText("");
                        requestViewModel.setLiveData(soNumber);

                        return true;
                    }
                }
                return false;
            }
        });
    }

    private void initComponent() {
        etSalesOrder = findViewById(R.id.et_sales_order);
        tvCompanyName = findViewById(R.id.tv_company);
        btnBack = findViewById(R.id.btn_back);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.et_sales_order:
//                Intent goToSearchSalesOrder = new Intent(this, SearchSOActivity.class);
//                startActivity(goToSearchSalesOrder);
//                break;
            case R.id.btn_back:
                onBackPressed();
                break;
        }
    }
}
