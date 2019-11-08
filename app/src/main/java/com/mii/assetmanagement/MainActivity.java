package com.mii.assetmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    ImageView iv_profile;
    TextView tvResultName;

    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponent();

        sharedPrefManager = new SharedPrefManager(this);
        tvResultName.setText(sharedPrefManager.getSPNama());

        iv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoprofile = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(gotoprofile);
            }
        });
    }

    private void initComponent() {
        iv_profile = (ImageView) findViewById(R.id.img_profile);
        tvResultName = (TextView) findViewById(R.id.tv_result_name);
    }

}
