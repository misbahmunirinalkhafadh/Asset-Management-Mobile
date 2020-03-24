package com.mii.assetmanagement.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mii.assetmanagement.R;
import com.mii.assetmanagement.model.HistoryMaintenanceResult;

import java.util.ArrayList;

public class HistoryMainServiceAdapter extends RecyclerView.Adapter<HistoryMainServiceAdapter.MainServiceViewHolder> {
    private Activity mActivity;
    private ArrayList<HistoryMaintenanceResult> list;

    public HistoryMainServiceAdapter(Activity mActivity, ArrayList<HistoryMaintenanceResult> list) {
        this.mActivity = mActivity;
        this.list = list;
    }

    @NonNull
    @Override
    public HistoryMainServiceAdapter.MainServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.item_list_history_detail_main_service, parent, false);
        return new MainServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryMainServiceAdapter.MainServiceViewHolder holder, int position) {
        HistoryMaintenanceResult result = list.get(position);

        holder.tvServiceName.setText(result.getName());
        boolean status = result.getStatus().equals("true");
        if (status){
            holder.tvStatus.setText(R.string.status_matching);
            holder.tvStatus.setTextColor(Color.parseColor("#2196F3"));
        } else {
            holder.tvStatus.setText(R.string.status_different);
            holder.tvStatus.setTextColor(Color.parseColor("#2196F3"));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class MainServiceViewHolder extends RecyclerView.ViewHolder {
        TextView tvServiceName, tvStatus;

        MainServiceViewHolder(@NonNull View itemView) {
            super(itemView);

            tvServiceName = itemView.findViewById(R.id.tv_service);
            tvStatus = itemView.findViewById(R.id.tv_status);
        }
    }
}
