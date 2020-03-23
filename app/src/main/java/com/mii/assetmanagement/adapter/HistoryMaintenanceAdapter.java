package com.mii.assetmanagement.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mii.assetmanagement.CustomOnItemClickListener;
import com.mii.assetmanagement.R;
import com.mii.assetmanagement.model.HistoryMaintenanceResult;
import com.mii.assetmanagement.view.HistoryDetailMaintenanceActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static com.mii.assetmanagement.view.HistoryDetailMaintenanceActivity.EXTRA_HISTORY;

public class HistoryMaintenanceAdapter extends RecyclerView.Adapter<HistoryMaintenanceAdapter.MaintenanceViewHolder> {
    private Activity mActivity;
    private ArrayList<HistoryMaintenanceResult> mData;

    public HistoryMaintenanceAdapter(Activity mActivity, ArrayList<HistoryMaintenanceResult> mData) {
        this.mActivity = mActivity;
        this.mData = mData;
    }

    @NonNull
    @Override
    public HistoryMaintenanceAdapter.MaintenanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.item_list_history_maintenance, parent, false);
        return new MaintenanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryMaintenanceAdapter.MaintenanceViewHolder holder, int position) {
        HistoryMaintenanceResult result = mData.get(position);

        final SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat output = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        try {
            Date dateFormat = input.parse(result.getDate());
            if (dateFormat != null) {
                holder.tvDate.setText(output.format(dateFormat));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.tvBrand.setText(result.getBrand());
        holder.tvSerial.setText(result.getSerial());
        holder.layout.setOnClickListener(new CustomOnItemClickListener(position, (view, position1) -> {
            Intent intent = new Intent(mActivity, HistoryDetailMaintenanceActivity.class);
            intent.putExtra(EXTRA_HISTORY, result);
            mActivity.startActivity(intent);
        }));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class MaintenanceViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout;
        TextView tvBrand, tvDate, tvSerial;

        MaintenanceViewHolder(@NonNull View itemView) {
            super(itemView);

            layout = itemView.findViewById(R.id.line);
            tvBrand = itemView.findViewById(R.id.tv_brand);
            tvDate = itemView.findViewById(R.id.tv_date_request);
            tvSerial = itemView.findViewById(R.id.tv_serial);
        }
    }
}
