package com.mii.assetmanagement.view;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mii.assetmanagement.R;
import com.mii.assetmanagement.SharedPrefManager;
import com.mii.assetmanagement.adapter.HistoryMaintenanceAdapter;
import com.mii.assetmanagement.model.HistoryMaintenanceResult;
import com.mii.assetmanagement.viewmodel.HistoryViewModel;

import java.util.ArrayList;
import java.util.List;

public class HistoryMaintenanceActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private HistoryMaintenanceAdapter adapter;
    private ArrayList<HistoryMaintenanceResult> resultArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_maintenance);
        actionBar();
        progressBar = findViewById(R.id.progressbar);
        recyclerView = findViewById(R.id.rv_history);

        SharedPrefManager sharedPrefManager = new SharedPrefManager(this);
        int nik = Integer.parseInt(sharedPrefManager.getSpNik());
        HistoryViewModel historyViewModel = ViewModelProviders.of(this).get(HistoryViewModel.class);
        historyViewModel.setHistoryMaintenance(nik);
        historyViewModel.getHistoryMaintenance().observe(this, historyMaintenance -> {
            if (historyMaintenance.getResults() != null) {
                List<HistoryMaintenanceResult> list = historyMaintenance.getResults();
                resultArrayList.addAll(list);
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(this, "Data Not Found" + historyMaintenance, Toast.LENGTH_SHORT).show();
            }
            progressBar.setVisibility(View.GONE);
        });

        setupRecyclerView();
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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
