package com.mii.assetmanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mii.assetmanagement.R;
import com.mii.assetmanagement.model.HistoryDetail;

import java.util.ArrayList;
import java.util.List;

public class PreorderAdapter extends RecyclerView.Adapter<PreorderAdapter.PreorderViewHolder> {
    private Context mContext;
    private ArrayList<HistoryDetail.PreOrder> orderList;

    public PreorderAdapter(Context mContext, ArrayList<HistoryDetail.PreOrder> orderList) {
        this.mContext = mContext;
        this.orderList = orderList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PreorderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_list_preorder, parent, false);
        return new PreorderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PreorderAdapter.PreorderViewHolder holder, int position) {
        HistoryDetail.PreOrder order = orderList.get(position);
        holder.brand.setText(order.getBrand());
        holder.qty.setText(order.getQuantity());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    static class PreorderViewHolder extends RecyclerView.ViewHolder {
        TextView brand, qty;

        PreorderViewHolder(@NonNull View itemView) {
            super(itemView);
            brand = itemView.findViewById(R.id.tv_brand);
            qty = itemView.findViewById(R.id.tv_qty);
        }
    }
}
