package com.mii.assetmanagement.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.mii.assetmanagement.R;
import com.mii.assetmanagement.viewmodel.BrandViewModel;
import com.mii.assetmanagement.viewmodel.RequestViewModel;

public class SearchAssetActivity extends AppCompatActivity implements View.OnClickListener {
//    private EditText;
    private Button btnBack;
    private BrandViewModel brandViewModel;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_asset);

        initComponent();

        brandViewModel = ViewModelProviders.of(this).get(BrandViewModel.class);

        eventInputBrand();
        callDataBrand();

        btnBack.setOnClickListener(this);
//        btn_category = (Button) findViewById(R.id.btn_category);

//        btn_category.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(getApplicationContext(), PopupActivity.class);
//                startActivity(i);
//            }
//        });
    }

    private void initComponent() {

        btnBack = findViewById(R.id.btn_back);
    }

    private void eventInputBrand() {
//        etSalesOrder.setInputType(InputType.TYPE_CLASS_NUMBER);
    }

    private void callDataBrand() {

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                onBackPressed();
                break;
        }
    }
}
