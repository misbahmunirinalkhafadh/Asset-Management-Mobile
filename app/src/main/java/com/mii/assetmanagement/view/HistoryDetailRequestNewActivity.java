package com.mii.assetmanagement.view;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.mii.assetmanagement.R;

public class HistoryDetailRequestNewActivity extends AppCompatActivity {

    private TextView tvIdTrans, tvCurrentNik, tvCurrentName, tvNewNik, tvOldSerial, tvCompany, tvBranch, tvReason;
    private RecyclerView recyclerView;
    public static final String EXTRA_HISTORY = "extra_history";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_detail_request_new);
        actionBar();
        initComponent();

    }

    private void initComponent() {
        tvIdTrans = findViewById(R.id.tv_id_request);
        tvCurrentNik = findViewById(R.id.tv_currentNik);
        tvCurrentName = findViewById(R.id.tv_currentName);
        tvNewNik = findViewById(R.id.tv_new_nik);
        tvOldSerial = findViewById(R.id.tv_old_serialNumber);
        tvCompany = findViewById(R.id.tv_company);
        tvBranch = findViewById(R.id.tv_branch);
        tvReason = findViewById(R.id.tv_reason);

        recyclerView = findViewById(R.id.rv_item_brand);
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
