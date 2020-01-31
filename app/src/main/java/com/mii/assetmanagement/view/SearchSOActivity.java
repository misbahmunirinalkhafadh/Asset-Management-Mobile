package com.mii.assetmanagement.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mii.assetmanagement.R;
import com.mii.assetmanagement.adapter.SalesOrderAdapter;
import com.mii.assetmanagement.viewmodel.RequestViewModel;

public class SearchSOActivity extends AppCompatActivity implements View.OnClickListener {

    String soNumber = "2886002804";
    private Button btn_back;
    private RecyclerView recyclerView;
    private SalesOrderAdapter adapter;
    private RequestViewModel requestViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_so);

//        requestViewModel = ViewModelProviders.of(this, new ViewModelProvider.NewInstanceFactory()).get(RequestViewModel.class);
//        requestViewModel.setLiveData(soNumber);

        initComponent();


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adapter = new SalesOrderAdapter(this);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        btn_back.setOnClickListener(this);
    }

    private void initComponent() {
        recyclerView = findViewById(R.id.rv_salesorder);
        btn_back = findViewById(R.id.btn_back);
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        requestViewModel.getListSalesOrder().observe(Objects.requireNonNull(this), new Observer<ArrayList<SalesOrder>>() {
//            @Override
//            public void onChanged(ArrayList<SalesOrder> salesOrders) {
//                if (salesOrders != null) {
//                    adapter.setSalesOrders(salesOrders);
//                    Toast.makeText(SearchSOActivity.this, "Berhasil", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(SearchSOActivity.this, "GAGAL", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_back) {
            onBackPressed();
        }
    }
}
