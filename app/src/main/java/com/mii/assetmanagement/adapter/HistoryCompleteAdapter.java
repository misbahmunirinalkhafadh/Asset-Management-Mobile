package com.mii.assetmanagement.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
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
import com.mii.assetmanagement.model.HistoryResult;
import com.mii.assetmanagement.view.HistoryDetailActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import static com.mii.assetmanagement.R.drawable.ic_history_exchange;
import static com.mii.assetmanagement.R.drawable.ic_history_maintenance;
import static com.mii.assetmanagement.R.drawable.ic_history_request;

public class HistoryCompleteAdapter extends RecyclerView.Adapter<HistoryCompleteAdapter.HistoryViewHolder> {

    private Activity activity;
    private ArrayList<HistoryResult> list;

    public HistoryCompleteAdapter(Activity activity, ArrayList<HistoryResult> list) {
        this.activity = activity;
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_history_complete, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryCompleteAdapter.HistoryViewHolder holder, int position) {
        HistoryResult result = list.get(position);

        String id = Integer.toString(result.getId());
        String type = result.getTypeRequest();
        String date = result.getDate();
        String status = result.getStatus();

        Drawable req = activity.getResources().getDrawable(ic_history_request);
        Drawable main = activity.getResources().getDrawable(ic_history_maintenance);
        Drawable exc = activity.getResources().getDrawable(ic_history_exchange);
        if (type.equals("Request New Asset")) holder.icon.setImageDrawable(req);
        if (type.equals("Request Maintenance")) holder.icon.setImageDrawable(main);
        if (type.equals("Request Exchange")) holder.icon.setImageDrawable(exc);

        final SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat output = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        try {
            Date changeFormat = input.parse(date);
            holder.date.setText(output.format(Objects.requireNonNull(changeFormat)));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.status.setText(status);
        if (status.equals("Accepted")) holder.status.setTextColor(Color.GREEN);
        if (status.equals("Rejected")) holder.status.setTextColor(Color.RED);

        holder.id.setText(id);
        holder.typeRequest.setText(type);
        holder.layout.setOnClickListener(new CustomOnItemClickListener(position, (view, position1) -> {
            Intent intent = new Intent(activity, HistoryDetailActivity.class);
            intent.putExtra(HistoryDetailActivity.EXTRA_HISTORY, result);
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
        TextView id, typeRequest, date, status;

        HistoryViewHolder(@NonNull View itemView) {
            super(itemView);

            layout = itemView.findViewById(R.id.ll_item_history);
            icon = itemView.findViewById(R.id.iv_history_icon);
            id = itemView.findViewById(R.id.tv_id_request);
            typeRequest = itemView.findViewById(R.id.tv_type_request);
            date = itemView.findViewById(R.id.tv_date_request);
            status = itemView.findViewById(R.id.tv_status);
        }
    }
}
