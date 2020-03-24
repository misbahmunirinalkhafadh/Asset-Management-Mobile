package com.mii.assetmanagement.view;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mii.assetmanagement.R;
import com.mii.assetmanagement.adapter.HistoryMainPartAdapter;
import com.mii.assetmanagement.model.HistoryMaintenanceResult;
import com.mii.assetmanagement.viewmodel.HistoryViewModel;

import java.util.ArrayList;
import java.util.List;

public class HistoryDetailMaintenanceActivity extends AppCompatActivity {
    private TextView tvIdTrans, tvSerial, tvReason;
    private RecyclerView rvMainPart, rvMainService;
    private ProgressBar progressBar;
    private ScrollView view;
    private HistoryViewModel historyViewModel;
    private HistoryMainPartAdapter mainPartAdapter;
    private ArrayList<HistoryMaintenanceResult> mainParts = new ArrayList<>();
    private ArrayList<HistoryMaintenanceResult> mainServices = new ArrayList<>();
    public static final String EXTRA_HISTORY = "extra_history";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_detail_maintenance);
        actionBar();
        initComponent();
        showLoading(true);

        historyViewModel = ViewModelProviders.of(this).get(HistoryViewModel.class);
        HistoryMaintenanceResult result = getIntent().getParcelableExtra(EXTRA_HISTORY);
        if (result != null) {
            tvIdTrans.setText(String.valueOf(result.get_id()));
            tvSerial.setText(result.getSerial());
            tvReason.setText(result.getReason());
            historyViewModel.setHistoryDetailMainPart(result.get_id());
        }
        getDataMainPart();
    }

    private void getDataMainPart() {
        historyViewModel.getHistoryDetailMainPart().observe(this, historyMaintenance -> {
            List<HistoryMaintenanceResult> results = historyMaintenance.getMainPart();
            mainParts.addAll(results);
            mainPartAdapter.notifyDataSetChanged();
            showLoading(false);
        });
        if (mainPartAdapter == null) {
            mainPartAdapter = new HistoryMainPartAdapter(this, mainParts);
            rvMainPart.setLayoutManager(new LinearLayoutManager(this));
            rvMainPart.setAdapter(mainPartAdapter);
            rvMainPart.setItemAnimator(new DefaultItemAnimator());
        } else {
            mainPartAdapter.notifyDataSetChanged();
        }
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
            view.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            view.setVisibility(View.VISIBLE);
        }
    }

    private void initComponent() {
        tvIdTrans = findViewById(R.id.tv_id_transaction);
        tvSerial = findViewById(R.id.tv_serial);
        rvMainPart = findViewById(R.id.rv_item_brand);
        rvMainService = findViewById(R.id.rv_main_checklist);
//        tvIpAddress = findViewById(R.id.tv_ip_address);
//        tvUnameLogin = findViewById(R.id.tv_uname_login);
//        tvCompName = findViewById(R.id.tv_comp_name);
        tvReason = findViewById(R.id.tv_reason);
        progressBar = findViewById(R.id.progressbar);
        view = findViewById(R.id.view);
    }

    private void actionBar() {
        ActionBar actionBar = getSupportActionBar();
        AppCompatTextView mTitleTextView = new AppCompatTextView(getApplicationContext());
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.START;
        if (actionBar != null) {
            actionBar.setCustomView(mTitleTextView, layoutParams);
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_HOME_AS_UP);
        }
        mTitleTextView.setText(getString(R.string.appbar_history_detail));
        mTitleTextView.setTextAppearance(getApplicationContext(), android.R.style.TextAppearance_DeviceDefault_Large);
        mTitleTextView.setTextColor(Color.WHITE);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
