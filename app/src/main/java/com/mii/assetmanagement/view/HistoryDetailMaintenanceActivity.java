package com.mii.assetmanagement.view;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.mii.assetmanagement.R;
import com.mii.assetmanagement.model.HistoryMaintenanceResult;

public class HistoryDetailMaintenanceActivity extends AppCompatActivity {
    private TextView tvIdTrans;
    public static final String EXTRA_HISTORY = "extra_history";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_detail_maintenance);
        actionBar();
        initComponent();

        HistoryMaintenanceResult result = getIntent().getParcelableExtra(EXTRA_HISTORY);
        if (result != null) {
            String _id = String.valueOf(result.get_id());
            tvIdTrans.setText(_id);
        }
    }

    private void initComponent() {
        tvIdTrans = findViewById(R.id.tv_id_transaction);
//        tvIpAddress = findViewById(R.id.tv_ip_address);
//        tvUnameLogin = findViewById(R.id.tv_uname_login);
//        tvCompName = findViewById(R.id.tv_comp_name);
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
