package com.mii.assetmanagement.view;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mii.assetmanagement.R;
import com.mii.assetmanagement.adapter.PreorderAdapter;
import com.mii.assetmanagement.model.HistoryDetail;
import com.mii.assetmanagement.model.HistoryResult;
import com.mii.assetmanagement.viewmodel.HistoryViewModel;

import java.util.ArrayList;
import java.util.List;

public class HistoryDetailActivity extends AppCompatActivity {
    private TextView nik, name, branch, location;
    private RecyclerView recyclerView;
    private HistoryViewModel historyViewModel;
    private ArrayList<HistoryDetail.PreOrder> preOrderList = new ArrayList<>();
    public static final String EXTRA_HISTORY = "extra_history";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_detail);
        actionBar();
        initComponent();
        historyViewModel = ViewModelProviders.of(this).get(HistoryViewModel.class);

        HistoryResult result = getIntent().getParcelableExtra(EXTRA_HISTORY);
        if (result != null) {
            nik.setText(result.getNik());
            name.setText(result.getName());
            branch.setText(result.getBranch());
            location.setText(result.getLocation());
            historyViewModel.setHistoryDetail(result.getId());
        }
        callDataDetail();
        PreorderAdapter adapter = new PreorderAdapter(this, preOrderList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void callDataDetail() {
        historyViewModel.getHistoryDetail().observe(this, historyDetails -> {
            List<HistoryDetail> list = historyDetails;
            for (HistoryDetail hd : historyDetails) {
                Toast.makeText(this, "Data" + hd.getPreOrder().get(0).getBrand(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initComponent() {
        nik = findViewById(R.id.tv_nik);
        name = findViewById(R.id.tv_name);
        branch = findViewById(R.id.tv_branch);
        location = findViewById(R.id.tv_location);
//        recyclerView = findViewById(R.id.rv_preorder);
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
        mTitleTextView.setText(getString(R.string.apbar_history_detail));
        mTitleTextView.setTextAppearance(getApplicationContext(), android.R.style.TextAppearance_DeviceDefault_Large);
        mTitleTextView.setTextColor(Color.WHITE);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
