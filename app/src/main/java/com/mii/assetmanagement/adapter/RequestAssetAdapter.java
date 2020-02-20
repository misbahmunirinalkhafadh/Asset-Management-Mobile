package com.mii.assetmanagement.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mii.assetmanagement.R;
import com.mii.assetmanagement.model.Asset;
import com.mii.assetmanagement.model.AssetResult;
import com.mii.assetmanagement.view.SearchAssetActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RequestAssetAdapter extends RecyclerView.Adapter<RequestAssetAdapter.ViewHolder> {
    private Context mContext;
    private String[] brand;

    public RequestAssetAdapter(Context mContext, String[] brand) {
        this.mContext = mContext;
        this.brand = brand;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_brand, parent, false);
        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       
    }

    @Override
    public int getItemCount() {
        return null != brand ? brand.length : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout llBrand;
        TextView tvBrand;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            llBrand = itemView.findViewById(R.id.ll_brand);
            tvBrand = itemView.findViewById(R.id.tv_brand);
        }
    }
}
