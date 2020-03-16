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
import com.mii.assetmanagement.model.HistoryResult;
import com.mii.assetmanagement.view.HistoryDetailExchangeActivity;
import com.mii.assetmanagement.view.HistoryDetailRequestNewActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import static com.mii.assetmanagement.R.drawable.ic_history_exchange;
import static com.mii.assetmanagement.R.drawable.ic_history_maintenance;
import static com.mii.assetmanagement.R.drawable.ic_history_request;

public class HistoryProgressAdapter extends RecyclerView.Adapter<HistoryProgressAdapter.HistoryViewHolder> {

    private Activity activity;
    private ArrayList<HistoryResult> list;

    public HistoryProgressAdapter(Activity activity, ArrayList<HistoryResult> list) {
        this.activity = activity;
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_history_progress, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryProgressAdapter.HistoryViewHolder holder, int position) {
        HistoryResult result = list.get(position);

        String type = result.getTypeRequest();
        String date = result.getDate();
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

        holder.typeRequest.setText(type);
        holder.layout.setOnClickListener(new CustomOnItemClickListener(position, (view, position1) -> {
            if (type.equals("Request New Asset")) {
                Intent goToReqNew = new Intent(activity, HistoryDetailRequestNewActivity.class);
                goToReqNew.putExtra(HistoryDetailRequestNewActivity.EXTRA_HISTORY, result);
                activity.startActivity(goToReqNew);
            } else if (type.equals("Request Exchange")) {
                Intent goToReqExchange = new Intent(activity, HistoryDetailExchangeActivity.class);
                goToReqExchange.putExtra(HistoryDetailExchangeActivity.EXTRA_HISTORY, result);
                activity.startActivity(goToReqExchange);
            }
        }));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class HistoryViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout;
        ImageView icon;
        TextView typeRequest, date;

        HistoryViewHolder(@NonNull View itemView) {
            super(itemView);

            layout = itemView.findViewById(R.id.ll_item_history);
            icon = itemView.findViewById(R.id.iv_history_icon);
            typeRequest = itemView.findViewById(R.id.tv_type_request);
            date = itemView.findViewById(R.id.tv_date_request);
        }
    }
}
