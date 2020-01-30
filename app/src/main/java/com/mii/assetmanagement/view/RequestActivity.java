package com.mii.assetmanagement.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mii.assetmanagement.R;
import com.mii.assetmanagement.model.SalesOrder;

public class RequestActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etSalesOrder;
    private TextView tvCompanyName;
    private Button btnBack;
    public static final String EXTRA_SALES_ORDER = "extra_sales_order";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        initComponent();

        SalesOrder salesOrder = getIntent().getParcelableExtra(EXTRA_SALES_ORDER);
//        String soId = salesOrder.getSoId();
//        String company = salesOrder.getCustomerName();
//        etSalesOrder.setText(soId);
//        tvCompanyName.setText(company);

        etSalesOrder.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }

    private void initComponent() {
        etSalesOrder = findViewById(R.id.et_sales_order);
        etSalesOrder.setKeyListener(null);
        tvCompanyName = findViewById(R.id.tv_company);
        btnBack = findViewById(R.id.btn_back);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.et_sales_order:
                Intent goToSearchSalesOrder = new Intent(this, SearchSOActivity.class);
                startActivity(goToSearchSalesOrder);
                break;
            case R.id.btn_back:
                onBackPressed();
                break;
        }
    }
}
