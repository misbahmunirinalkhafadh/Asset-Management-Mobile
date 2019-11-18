package com.mii.assetmanagement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

//import androidx.appcompat.widget.Toolbar;

public class InformasiActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnClose;
    ImageView btnBack;

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informasi);

        initComponent();

        btnBack.setOnClickListener(this);
        btnClose.setOnClickListener(this);
    }

    private void initComponent() {
        btnBack = findViewById(R.id.iv_back);
        btnClose = findViewById(R.id.btn_close);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.btn_close:
                Intent goToHome = new Intent(InformasiActivity.this, MainActivity.class);
                startActivity(goToHome);
                finish();
                break;
        }
    }
}
