package com.mii.assetmanagement.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mii.assetmanagement.R;

public class SearchAssetActivity extends AppCompatActivity implements View.OnClickListener {
    //    private EditText;

    Button action_filter;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_asset);


        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

//        action_filter.setOnClickListener(this);

        initComponent();


        eventInputBrand();
        callDataBrand();
    }

    private void initComponent() {
        action_filter = findViewById(R.id.action_filter);
//        btnBack = findViewById(R.id.btn_back);
    }

    private void eventInputBrand() {

    }

    private void callDataBrand() {

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_filter) {
            Intent intent = new Intent(this, PopupActivity.class);
            startActivity(intent);
            return true;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        if (searchManager != null) {
            SearchView searchView = (SearchView) (menu.findItem(R.id.item_search)).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.onActionViewExpanded();
            searchView.setBackground(getDrawable(R.drawable.bg_search));

            ImageView imageView = searchView.findViewById(androidx.appcompat.R.id.search_close_btn);
            imageView.setColorFilter(Color.BLACK);
            LinearLayout first = (LinearLayout) searchView.getChildAt(0);
            LinearLayout second = (LinearLayout) first.getChildAt(2);
            LinearLayout third = (LinearLayout) second.getChildAt(1);
            SearchView.SearchAutoComplete searchAutoComplete = (SearchView.SearchAutoComplete) third.getChildAt(0);
            searchAutoComplete.setHintTextColor(Color.GRAY);
            searchAutoComplete.setTextColor(Color.BLACK);

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                onBackPressed();
                break;
        }
    }
}
