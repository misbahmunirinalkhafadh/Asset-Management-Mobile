package com.mii.assetmanagement.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.mii.assetmanagement.R;

public class SearchAssetActivity extends AppCompatActivity {

    private Button btn_category;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_asset);

        btn_category = (Button) findViewById(R.id.btn_category);

        btn_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), PopupActivity.class);
                startActivity(i);
            }
        });
    }
}
