package com.mii.assetmanagement.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mii.assetmanagement.CustomOnItemClickListener;
import com.mii.assetmanagement.R;
import com.mii.assetmanagement.model.History;
import com.mii.assetmanagement.view.HistoryDetailActivity;

import java.util.List;

import static com.mii.assetmanagement.R.drawable.ic_history_exchange;
import static com.mii.assetmanagement.R.drawable.ic_history_laptop;
import static com.mii.assetmanagement.R.drawable.ic_history_request;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private Activity activity;
    private List<History> list;

    public HistoryAdapter(Activity activity, List<History> list) {
        this.activity = activity;
        this.list = list;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_history, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.HistoryViewHolder holder, int position) {
        History history = list.get(position);

        String type = history.getTypeRequest();

        Drawable req = activity.getResources().getDrawable(ic_history_request);
        Drawable main = activity.getResources().getDrawable(ic_history_laptop);
        Drawable exc = activity.getResources().getDrawable(ic_history_exchange);
        if (type.equals("Request")) holder.icon.setImageDrawable(req);
        if (type.equals("Maintenance")) holder.icon.setImageDrawable(main);
        if (type.equals("Exchange")) holder.icon.setImageDrawable(exc);

        holder.sales.setText(history.getSales());
        holder.typeRequest.setText(type);
        holder.date.setText(history.getDate());
        holder.layout.setOnClickListener(new CustomOnItemClickListener(position, (view, position1) -> {
            Intent intent = new Intent(activity, HistoryDetailActivity.class);
            activity.startActivity(intent);
        }));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class HistoryViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout;
        ImageView icon;
        TextView sales, typeRequest, date;

        HistoryViewHolder(@NonNull View itemView) {
            super(itemView);

            layout = itemView.findViewById(R.id.ll_item_history);
            icon = itemView.findViewById(R.id.iv_history_icon);
            sales = itemView.findViewById(R.id.tv_sales);
            typeRequest = itemView.findViewById(R.id.tv_type_request);
            date = itemView.findViewById(R.id.tv_date_request);
        }
    }
}
