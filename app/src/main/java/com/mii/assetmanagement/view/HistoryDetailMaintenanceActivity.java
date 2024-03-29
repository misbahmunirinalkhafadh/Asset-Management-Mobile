package com.mii.assetmanagement.view;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.mii.assetmanagement.R;
import com.mii.assetmanagement.adapter.HistoryMainPartAdapter;
import com.mii.assetmanagement.adapter.HistoryMainServiceAdapter;
import com.mii.assetmanagement.model.HistoryMaintenanceResult;
import com.mii.assetmanagement.viewmodel.HistoryViewModel;

import java.util.ArrayList;
import java.util.List;

public class HistoryDetailMaintenanceActivity extends AppCompatActivity {
    private TextView tvIdTrans, tvSerial, tvReason;
    private RecyclerView rvMainPart, rvMainService;
    private ProgressBar progressBar;
    private ConstraintLayout layout;
    private ScrollView view;
    private Snackbar snackbar;
    private HistoryMainPartAdapter partAdapter;
    private HistoryMainServiceAdapter serviceAdapter;
    private ArrayList<HistoryMaintenanceResult> mainParts = new ArrayList<>();
    private ArrayList<HistoryMaintenanceResult> mainServices = new ArrayList<>();
    public static final String EXTRA_HISTORY = "extra_history";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_detail_maintenance);
        actionBar();
        initComponent();

        HistoryViewModel historyViewModel = ViewModelProviders.of(this).get(HistoryViewModel.class);
        HistoryMaintenanceResult result = getIntent().getParcelableExtra(EXTRA_HISTORY);
        if (result != null) {
            tvIdTrans.setText(String.valueOf(result.get_id()));
            tvSerial.setText(result.getSerial());
            tvReason.setText(result.getReason());
            if (isNetworkAvailable()) {
                showLoading(true);
                historyViewModel.setHistoryDetailMaintenance(result.get_id());
            } else {
                progressBar.setVisibility(View.GONE);
                view.setVisibility(View.GONE);
                showSnackBar();
            }
        }

        historyViewModel.getHistoryDetailMaintenance().observe(this, historyMaintenance -> {
            List<HistoryMaintenanceResult> resultMainPart = historyMaintenance.getMainPart();
            List<HistoryMaintenanceResult> resultMainService = historyMaintenance.getMainService();
            mainParts.addAll(resultMainPart);
            mainServices.addAll(resultMainService);
            partAdapter.notifyDataSetChanged();
            showLoading(false);
        });
        setDataRecyclerview();
    }

    private void setDataRecyclerview() {
        if (partAdapter == null) {
            partAdapter = new HistoryMainPartAdapter(this, mainParts);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            };
            rvMainPart.setLayoutManager(layoutManager);
            rvMainPart.setAdapter(partAdapter);
        } else {
            partAdapter.notifyDataSetChanged();
        }
        if (serviceAdapter == null) {
            serviceAdapter = new HistoryMainServiceAdapter(this, mainServices);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            };
            rvMainService.setLayoutManager(layoutManager);
            rvMainService.setAdapter(serviceAdapter);
        } else {
            serviceAdapter.notifyDataSetChanged();
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
        tvReason = findViewById(R.id.tv_reason);
        progressBar = findViewById(R.id.progressbar);
        view = findViewById(R.id.view);
        layout = findViewById(R.id.main_view);
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
        mTitleTextView.setText(getString(R.string.appbar_detail_maintenance));
        mTitleTextView.setTextAppearance(getApplicationContext(), android.R.style.TextAppearance_DeviceDefault_Large);
        mTitleTextView.setTextColor(Color.WHITE);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void showSnackBar() {
        snackbar = Snackbar
                .make(layout, "No Internet Connection", Snackbar.LENGTH_LONG)
                .setAction("DISMISS", v -> snackbar.dismiss());
        snackbar.show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
