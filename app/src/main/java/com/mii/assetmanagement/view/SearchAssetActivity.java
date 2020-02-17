package com.mii.assetmanagement.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import com.mii.assetmanagement.R;
import com.mii.assetmanagement.viewmodel.BrandViewModel;

public class SearchAssetActivity extends AppCompatActivity implements View.OnClickListener {
    //    private EditText;
    private Button btnBack;
    private BrandViewModel brandViewModel;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_asset);

//        Toolbar toolbar = findViewById(R.id.toolbar_main);
//        setSupportActionBar(toolbar);
        //   getSupportActionBar().show();
        getSupportActionBar().setTitle("Search Asset");

        initComponent();

        brandViewModel = ViewModelProviders.of(this).get(BrandViewModel.class);

        eventInputBrand();
        callDataBrand();

        //btnBack.setOnClickListener(this);
//        btn_category = (Button) findViewById(R.id.btn_category);

//        btn_category.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(getApplicationContext(), PopupActivity.class);
//                startActivity(i);
//            }
//        });
    }

    private void initComponent() {

        btnBack = findViewById(R.id.btn_back);
    }

    private void eventInputBrand() {
//        etSalesOrder.setInputType(InputType.TYPE_CLASS_NUMBER);
    }

    private void callDataBrand() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.item_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
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
        return true;
    }

    //     @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//
//        MenuItem search = menu.findItem(R.id.item_search);
//        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
//        search(searchView);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    private void search(SearchView searchView) {
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//
//                return true;
//            }
//        });
//    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                onBackPressed();
                break;
        }
    }
}
