package com.mii.assetmanagement.view;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mii.assetmanagement.R;
import com.mii.assetmanagement.adapter.HistoryRequestDetailAdapter;
import com.mii.assetmanagement.model.HistoryResult;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class HistoryDetailRequestActivity extends AppCompatActivity {
    private TextView textStatus, textNik, textName, textOldNik, textOldName, textSerial;
    private LinearLayout lineItem;
    private TextView tvIdTrans, tvDate, tvStatus, tvNik, tvName, tvOldNik, tvOldName, tvSerial, tvLocation, tvBranch, tvReason;
    private RecyclerView recyclerView;
    private HistoryRequestDetailAdapter adapter;
    public static final String EXTRA_HISTORY = "extra_history";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_detail_request);

        initComponent();

        HistoryResult result = getIntent().getParcelableExtra(EXTRA_HISTORY);
        if (result != null) {
            stateCondition(result);
            tvIdTrans.setText(result.getId());
            final SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            SimpleDateFormat output = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
            try {
                Date changeFormat = input.parse(result.getDate());
                tvDate.setText(output.format(Objects.requireNonNull(changeFormat)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            tvStatus.setText(result.getStatus());
            tvNik.setText(result.getNik());
            tvName.setText(result.getName());
            tvOldNik.setText(result.getOldNik());
            tvOldName.setText(result.getOldName());
            tvSerial.setText(result.getSerial());
            tvLocation.setText(result.getLocation());
            tvBranch.setText(result.getBranch());
            if (result.getReason() != null) tvReason.setText(result.getReason());
            if (result.getRemark() != null) tvReason.setText(result.getRemark());

            String[] brand = result.getBrand();
            int[] qty = result.getQuantity();
            setupRecyclerView(brand, qty);
            actionBar(result.getTypeRequest());
        }
    }

    private void setupRecyclerView(String[] brand, int[] qty) {
        if (adapter == null) {
            adapter = new HistoryRequestDetailAdapter(this, brand, qty);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            };
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    private void stateCondition(HistoryResult result) {
        if (result.getStatus().equals("inprogress")) {
            textStatus.setVisibility(View.GONE);
            tvStatus.setVisibility(View.GONE);
        }
        if (result.getBrand().length == 0 || result.getQuantity().length == 0) {
            lineItem.setVisibility(View.GONE);
        } else {
            lineItem.setVisibility(View.VISIBLE);
        }
        if (result.getSerial() == null) {
            tvSerial.setVisibility(View.GONE);
            textSerial.setVisibility(View.GONE);
        } else {
            tvSerial.setVisibility(View.VISIBLE);
            textSerial.setVisibility(View.VISIBLE);
        }

        if (result.getTypeRequest().equals("Request New Asset")) {
            textNik.setVisibility(View.GONE);
            textName.setVisibility(View.GONE);
            textOldNik.setVisibility(View.GONE);
            textOldName.setVisibility(View.GONE);
            tvOldNik.setVisibility(View.GONE);
            tvOldName.setVisibility(View.GONE);
            tvNik.setVisibility(View.GONE);
            tvName.setVisibility(View.GONE);
        }

        if (result.getTypeRequest().equals("Request Exchange Asset")) {
            textOldNik.setVisibility(View.GONE);
            textOldName.setVisibility(View.GONE);
            tvOldNik.setVisibility(View.GONE);
            tvOldName.setVisibility(View.GONE);
        }
    }

    private void initComponent() {
        // Layout
        textStatus = findViewById(R.id.textStatus);
        textNik = findViewById(R.id.textNik);
        textName = findViewById(R.id.textName);
        textOldNik = findViewById(R.id.textOldNik);
        textOldName = findViewById(R.id.textOldName);
        textSerial = findViewById(R.id.textSerial);
        lineItem = findViewById(R.id.lineItem);
        recyclerView = findViewById(R.id.rv_asset);
        // View
        tvIdTrans = findViewById(R.id.tv_id_transaction);
        tvDate = findViewById(R.id.tv_date_request);
        tvStatus = findViewById(R.id.tv_status);
        tvNik = findViewById(R.id.tv_nik);
        tvName = findViewById(R.id.tv_name);
        tvOldNik = findViewById(R.id.tv_old_nik);
        tvOldName = findViewById(R.id.tv_old_name);
        tvSerial = findViewById(R.id.tv_serial);
        tvLocation = findViewById(R.id.tv_location);
        tvBranch = findViewById(R.id.tv_branch);
        tvReason = findViewById(R.id.tv_reason);
    }

    private void actionBar(String type) {
        String tittleBar = "Detail " + type;
        ActionBar actionBar = getSupportActionBar();
        AppCompatTextView mTitleTextView = new AppCompatTextView(getApplicationContext());
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.START;
        if (actionBar != null) {
            actionBar.setCustomView(mTitleTextView, layoutParams);
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_HOME_AS_UP);
        }
        mTitleTextView.setText(tittleBar);
        mTitleTextView.setTextAppearance(getApplicationContext(), android.R.style.TextAppearance_DeviceDefault_Medium);
        mTitleTextView.setTextColor(Color.WHITE);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
