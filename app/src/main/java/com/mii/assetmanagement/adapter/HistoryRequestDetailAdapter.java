package com.mii.assetmanagement.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mii.assetmanagement.R;

public class HistoryRequestDetailAdapter extends RecyclerView.Adapter<HistoryRequestDetailAdapter.RequestViewHolder> {
    private Activity mActivity;
    private String[] brand;
    private int[] qty;

    public HistoryRequestDetailAdapter(Activity mActivity, String[] brand, int[] qty) {
        this.mActivity = mActivity;
        this.brand = brand;
        this.qty = qty;
    }

    @NonNull
    @Override
    public HistoryRequestDetailAdapter.RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.item_list_history_detail_request, parent, false);
        return new RequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryRequestDetailAdapter.RequestViewHolder holder, int position) {
        holder.tvBrand.setText(brand[position]);
        holder.tvQty.setText(String.valueOf(qty[position]));
    }

    @Override
    public int getItemCount() {
        return brand.length;
    }

    static class RequestViewHolder extends RecyclerView.ViewHolder {
        TextView tvBrand, tvQty;

        RequestViewHolder(@NonNull View itemView) {
            super(itemView);

            tvBrand = itemView.findViewById(R.id.tv_brand);
            tvQty = itemView.findViewById(R.id.tv_qty);
        }
    }
}
