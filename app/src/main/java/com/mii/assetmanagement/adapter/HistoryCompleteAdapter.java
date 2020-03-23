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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mii.assetmanagement.CustomOnItemClickListener;
import com.mii.assetmanagement.R;
import com.mii.assetmanagement.model.HistoryResult;
import com.mii.assetmanagement.view.HistoryDetailRequestActivity;

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
        View view = LayoutInflater.from(activity).inflate(R.layout.item_list_history_complete, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryCompleteAdapter.HistoryViewHolder holder, int position) {
        HistoryResult result = list.get(position);

        final SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat output = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        try {
            Date changeFormat = input.parse(result.getDate());
            holder.date.setText(output.format(Objects.requireNonNull(changeFormat)));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String status = result.getStatus();
        holder.status.setText(status);
        if (status.equals("Accepted")) holder.status.setTextColor(Color.parseColor("#4CAF50"));
        if (status.equals("Rejected")) holder.status.setTextColor(Color.parseColor("#D32F2F"));

        holder.transId.setText(result.getId());
        holder.layout.setOnClickListener(new CustomOnItemClickListener(position, (view, position1) -> {
            Toast.makeText(activity, result.getId(), Toast.LENGTH_SHORT).show();
//            if (type.equals("Request New Asset")) {
//                Intent goToReqNew = new Intent(activity, HistoryDetailRequestActivity.class);
//                goToReqNew.putExtra(HistoryDetailRequestActivity.EXTRA_HISTORY, result);
//                activity.startActivity(goToReqNew);
//            } else if (type.equals("Request Exchange")) {
//                Intent goToReqExchange = new Intent(activity, HistoryDetailExchangeActivity.class);
//                goToReqExchange.putExtra(HistoryDetailExchangeActivity.EXTRA_HISTORY, result);
//                activity.startActivity(goToReqExchange);
//            }
        }));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class HistoryViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout;
        TextView transId, date, status;

        HistoryViewHolder(@NonNull View itemView) {
            super(itemView);

            layout = itemView.findViewById(R.id.ll_item_history);
            transId = itemView.findViewById(R.id.tv_id_transaction);
            date = itemView.findViewById(R.id.tv_date_request);
            status = itemView.findViewById(R.id.tv_status);
        }
    }
}
