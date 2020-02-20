package com.mii.assetmanagement.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mii.assetmanagement.R;
import com.mii.assetmanagement.adapter.RequestAssetAdapter;
import com.mii.assetmanagement.model.AssetResult;
import com.mii.assetmanagement.viewmodel.RequestViewModel;

import java.util.ArrayList;
import java.util.List;

public class SearchAssetActivity extends AppCompatActivity implements View.OnClickListener {
    //    private EditText;
    Button action_filter;
    private RecyclerView rvAsset;
    private RequestViewModel requestViewModel;

    private RequestAssetAdapter adapter;
    private String[] brand;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_asset);


        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        requestViewModel = ViewModelProviders.of(this).get(RequestViewModel.class);
//        action_filter.setOnClickListener(this);

        initComponent();
        eventInputBrand();
        callDataBrand();
        setupRecyclerView();

    }

    private void setupRecyclerView() {
        if (adapter == null) {
            adapter = new RequestAssetAdapter(this, brand);
            rvAsset.setLayoutManager(new LinearLayoutManager(this));
            rvAsset.setAdapter(adapter);
            rvAsset.setItemAnimator(new DefaultItemAnimator());
            rvAsset.setNestedScrollingEnabled(true);
        } else {
            adapter.notifyDataSetChanged();
        }
    }


    private void initComponent() {
        action_filter = findViewById(R.id.action_filter);
        rvAsset = findViewById(R.id.rv_asset);
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
                    requestViewModel.setDataAsset(query);
                    Toast.makeText(SearchAssetActivity.this, query, Toast.LENGTH_SHORT).show();
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
    protected void onResume() {
        super.onResume();
        requestViewModel.getDataAsset().observe(this, new Observer<AssetResult>() {
            @Override
            public void onChanged(AssetResult asset) {
                String[] brandsg = asset.getBrand();
                brand = brandsg;
                adapter.notifyDataSetChanged();
            }
        });
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
