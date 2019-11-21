package com.mii.assetmanagement;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class ScannerActivity extends AppCompatActivity {
    ImageView btnBack;
    TabLayout mTabs;
    View mIndicator;
    ViewPager mViewPager;

    private int indicatorWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        initComponent();

        //Set up the view pager and fragments
        TabFragmentAdapter adapter = new TabFragmentAdapter(getSupportFragmentManager());
        adapter.addFragment(CaptureFragment.newInstance(), "Scan QR");
        adapter.addFragment(InputSerialFragment.newInstance(), "Input Serial");
        mViewPager.setAdapter(adapter);
        mTabs.setupWithViewPager(mViewPager);

        //Determine indicator width at runtime
        mTabs.post(new Runnable() {
            @Override
            public void run() {
                indicatorWidth = mTabs.getWidth() / mTabs.getTabCount();

                //Assign new width
                FrameLayout.LayoutParams indicatorParams = (FrameLayout.LayoutParams) mIndicator.getLayoutParams();
                indicatorParams.width = indicatorWidth;
                mIndicator.setLayoutParams(indicatorParams);
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float positionOffset, int positionOffsetPx) {
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mIndicator.getLayoutParams();

                //Multiply positionOffset with indicatorWidth to get translation
                float translationOffset = (positionOffset + i) * indicatorWidth;
                params.leftMargin = (int) translationOffset;
                mIndicator.setLayoutParams(params);
            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initComponent() {
        btnBack = findViewById(R.id.iv_back);
        mTabs = findViewById(R.id.tab);
        mIndicator = findViewById(R.id.indicator);
        mViewPager = findViewById(R.id.viewPager);
    }
}
