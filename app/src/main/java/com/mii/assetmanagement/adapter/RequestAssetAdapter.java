package com.mii.assetmanagement.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mii.assetmanagement.R;
import com.mii.assetmanagement.model.AssetResult;
import com.mii.assetmanagement.view.SearchAssetActivity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class RequestAssetAdapter extends RecyclerView.Adapter<RequestAssetAdapter.ViewHolder> {
    private Context mContext;
    private List<AssetResult> assetResult;

    public RequestAssetAdapter(Context mContext, List<AssetResult> assetResult) {
        this.mContext = mContext;
        this.assetResult = assetResult;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cvBrand;
        TextView tvBrand;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cvBrand = itemView.findViewById(R.id.cv_brand);
            tvBrand = itemView.findViewById(R.id.tv_brand);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_brand, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AssetResult result = assetResult.get(position);
        holder.tvBrand.setText(result.getBrand());
        holder.cvBrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SearchAssetActivity.class);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return assetResult.size();
    }


}
