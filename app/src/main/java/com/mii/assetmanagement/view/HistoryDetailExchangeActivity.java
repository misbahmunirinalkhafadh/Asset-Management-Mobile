package com.mii.assetmanagement.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.mii.assetmanagement.R;

public class HistoryDetailExchangeActivity extends AppCompatActivity {
    public static final String EXTRA_HISTORY = "extra_history";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_detail_exchange);
    }
}
