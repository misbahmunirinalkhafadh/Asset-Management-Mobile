package com.mii.assetmanagement.view;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.mii.assetmanagement.R;
import com.mii.assetmanagement.SharedPrefManager;
import com.mii.assetmanagement.adapter.HistoryMaintenanceAdapter;
import com.mii.assetmanagement.model.HistoryMaintenanceResult;
import com.mii.assetmanagement.viewmodel.HistoryViewModel;

import java.util.ArrayList;
import java.util.List;

public class HistoryMaintenanceActivity extends AppCompatActivity {
    private ImageView imgEmptyList;
    private ProgressBar progressBar;
    private ConstraintLayout layout;
    private Snackbar snackbar;
    private RecyclerView recyclerView;
    private HistoryMaintenanceAdapter adapter;
    private ArrayList<HistoryMaintenanceResult> resultArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_maintenance);
        actionBar();
        initComponent();

        SharedPrefManager sharedPrefManager = new SharedPrefManager(this);
        int nik = Integer.parseInt(sharedPrefManager.getSpNik());
        HistoryViewModel historyViewModel = ViewModelProviders.of(this).get(HistoryViewModel.class);
        if (isNetworkAvailable()) {
            historyViewModel.setHistoryMaintenance(nik);
            progressBar.setVisibility(View.VISIBLE);
        } else {
            showSnackBar();
            imgEmptyList.setVisibility(View.VISIBLE);
        }

        historyViewModel.getHistoryMaintenance().observe(this, historyMaintenance -> {
            if (historyMaintenance.getResults() != null) {
                List<HistoryMaintenanceResult> list = historyMaintenance.getResults();
                resultArrayList.addAll(list);
                adapter.notifyDataSetChanged();
            } else {
                imgEmptyList.setVisibility(View.VISIBLE);
            }
            progressBar.setVisibility(View.GONE);
        });

        setupRecyclerView();
    }

    private void initComponent() {
        layout = findViewById(R.id.main_view);
        imgEmptyList = findViewById(R.id.img_empty_list);
        progressBar = findViewById(R.id.progressbar);
        recyclerView = findViewById(R.id.rv_history);

        imgEmptyList.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
    }

    private void setupRecyclerView() {
        if (adapter == null) {
            adapter = new HistoryMaintenanceAdapter(this, resultArrayList);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.addItemDecoration(new CustomDividerItemDecoration(recyclerView.getContext(), LinearLayoutManager.VERTICAL, 16));
            recyclerView.setAdapter(adapter);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setNestedScrollingEnabled(true);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    private void actionBar() {
        ActionBar actionBar = getSupportActionBar();
        AppCompatTextView mTitleTextView = new AppCompatTextView(getApplicationContext());
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        if (actionBar != null) {
            actionBar.setCustomView(mTitleTextView, layoutParams);
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_HOME_AS_UP);
            actionBar.setElevation(0);
        }
        mTitleTextView.setText(R.string.appbar_his_maintenance);
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
