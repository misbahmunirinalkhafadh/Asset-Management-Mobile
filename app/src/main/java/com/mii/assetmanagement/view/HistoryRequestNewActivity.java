package com.mii.assetmanagement.view;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.mii.assetmanagement.R;
import com.mii.assetmanagement.adapter.TabFragmentAdapter;

public class HistoryRequestNewActivity extends AppCompatActivity {
    public TabFragmentAdapter adapter;
    public ViewPager pager;
    public TabLayout tabs;
    private static final String REQUEST_TYPE = "Request New Asset";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_request_new);
        actionBar();
        initComponent();
        //Set up the view pager and fragments
        adapter = new TabFragmentAdapter(getSupportFragmentManager());
        adapter.addFragment(HistoryProgressFragment.newInstance(REQUEST_TYPE), "In Progress");
        adapter.addFragment(HistoryCompleteFragment.newInstance(REQUEST_TYPE), "Completed");

        pager.setAdapter(adapter);
        tabs.setupWithViewPager(pager);
    }

    private void initComponent() {
        pager = findViewById(R.id.viewPager);
        tabs = findViewById(R.id.tab);
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
        mTitleTextView.setText(R.string.appbar_his_req_new);
        mTitleTextView.setTextAppearance(getApplicationContext(), android.R.style.TextAppearance_DeviceDefault_Large);
        mTitleTextView.setTextColor(Color.WHITE);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
