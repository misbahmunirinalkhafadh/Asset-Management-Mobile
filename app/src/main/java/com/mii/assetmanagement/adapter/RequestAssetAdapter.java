package com.mii.assetmanagement.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mii.assetmanagement.CustomOnItemClickListener;
import com.mii.assetmanagement.R;
import com.mii.assetmanagement.model.AssetResult;
import com.mii.assetmanagement.view.RequestActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import static com.mii.assetmanagement.R.drawable.ic_delete;
import static com.mii.assetmanagement.R.drawable.ic_history_request;

public class RequestAssetAdapter extends RecyclerView.Adapter<RequestAssetAdapter.ViewHolder> {
    private Activity activity;
    private List<String> requestList;

    public RequestAssetAdapter(Activity activity, List<String> requestList) {
        this.activity = activity;
        this.requestList = requestList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_result_asset, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.brand.setText(requestList.get(position));
        holder.qty.setText(requestList.get(position));
//        holder.delete.setImageDrawable();

//        holder.delete.setImageDrawable(R.drawable);


    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView delete;
        TextView brand, qty;
        Button increase, decrease;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            delete = itemView.findViewById(R.id.iv_delete);
            qty = itemView.findViewById(R.id.tv_number);
            brand = itemView.findViewById(R.id.tv_brand);
            increase = itemView.findViewById(R.id.btn_increase);
            decrease = itemView.findViewById(R.id.btn_decrease);

        }
    }
}
