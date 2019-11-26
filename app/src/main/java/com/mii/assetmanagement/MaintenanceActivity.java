package com.mii.assetmanagement;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MaintenanceActivity extends AppCompatActivity {

    TextView tvProcessor, tvSystem, tvHdd, tvSsd, tvRam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance);
    }
}
