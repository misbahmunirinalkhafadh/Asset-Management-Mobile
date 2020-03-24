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

public class HistoryMainPartAdapter extends RecyclerView.Adapter<HistoryMainPartAdapter.MainPartViewHolder> {
    private Activity mActivity;
    private ArrayList<HistoryMaintenanceResult> list;

    public HistoryMainPartAdapter(Activity mActivity, ArrayList<HistoryMaintenanceResult> list) {
        this.mActivity = mActivity;
        this.list = list;
    }

    @NonNull
    @Override
    public HistoryMainPartAdapter.MainPartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.item_list_history_detail_main_comp, parent, false);
        return new MainPartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryMainPartAdapter.MainPartViewHolder holder, int position) {
        HistoryMaintenanceResult result = list.get(position);

        holder.tvPartName.setText(result.getPartName());
        holder.tvPartType.setText(result.getPartType());
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

    static class MainPartViewHolder extends RecyclerView.ViewHolder {
        TextView tvPartName, tvPartType, tvStatus;

        MainPartViewHolder(@NonNull View itemView) {
            super(itemView);

            tvPartName = itemView.findViewById(R.id.tv_part_name);
            tvPartType = itemView.findViewById(R.id.tv_part_type);
            tvStatus = itemView.findViewById(R.id.tv_status);
        }
    }
}
