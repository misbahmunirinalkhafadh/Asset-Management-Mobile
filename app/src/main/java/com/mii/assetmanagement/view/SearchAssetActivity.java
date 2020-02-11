package com.mii.assetmanagement.view;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mii.assetmanagement.R;

public class SearchAssetActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnBack;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_asset);

        initComponent();

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

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                onBackPressed();
                break;
        }
    }
}
