package com.mii.assetmanagement.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.mii.assetmanagement.R;

public class ExchangeActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange);
        actionBar();

        LinearLayout exchangeTypeEmployee = findViewById(R.id.line_exchange_employee);
        LinearLayout exchangeTypeAsset = findViewById(R.id.line_exchange_asset);

        exchangeTypeEmployee.setOnClickListener(this);
        exchangeTypeAsset.setOnClickListener(this);
    }

    private void actionBar() {
        ActionBar actionBar = getSupportActionBar();
        AppCompatTextView mTitleTextView = new AppCompatTextView(getApplicationContext());
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        if (actionBar != null) {
            actionBar.setCustomView(mTitleTextView, layoutParams);
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_HOME_AS_UP);
        }
        mTitleTextView.setText(getString(R.string.appbar_exchange_type));
        mTitleTextView.setTextAppearance(getApplicationContext(), android.R.style.TextAppearance_DeviceDefault_Large);
        mTitleTextView.setTextColor(Color.WHITE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.line_exchange_employee:
                Intent goToTypeEmployee = new Intent(ExchangeActivity.this, GenerateExchangeEmplActivity.class);
                startActivity(goToTypeEmployee);
                break;
            case R.id.line_exchange_asset:
                Intent goToTypeAsset = new Intent(ExchangeActivity.this, GenerateExchangeAssetActivity.class);
                startActivity(goToTypeAsset);
                break;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
