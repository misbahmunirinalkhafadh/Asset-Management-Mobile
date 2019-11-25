package com.mii.assetmanagement;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

//import androidx.appcompat.widget.Toolbar;

public class InformasiActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvProcessor, tvSystem, tvHdd, tvSsd, tvRam;
    Button btnClose;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informasi);

        initComponent();

//        Intent intent = getIntent();
//        String processor = intent.getExtras().getString("Processor");
//        String system = intent.getExtras().getString("System");
//        String hdd = intent.getExtras().getString("HDD");
//        String ssd = intent.getExtras().getString("SSD");
//        String ram = intent.getExtras().getString("RAM");
//
//        tvProcessor.setText(processor);
//        tvSystem.setText(system);
//        tvHdd.setText(hdd);
//        tvSsd.setText(ssd);
//        tvRam.setText(ram);

        btnBack.setOnClickListener(this);
        btnClose.setOnClickListener(this);
    }

    private void initComponent() {
        tvProcessor = findViewById(R.id.tv_processor);
        tvSystem = findViewById(R.id.tv_system);
        tvHdd = findViewById(R.id.tv_hdd);
        tvSsd = findViewById(R.id.tv_ssd);
        tvRam = findViewById(R.id.tv_ram);
        btnBack = findViewById(R.id.btn_back);
        btnClose = findViewById(R.id.btn_close);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                onBackPressed();
                break;
            case R.id.btn_close:
                Intent goToHome = new Intent(InformasiActivity.this, MainActivity.class);
                startActivity(goToHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                break;
        }
    }
}
