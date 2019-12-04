package com.mii.assetmanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainChecklistAdapter extends RecyclerView.Adapter<MainChecklistAdapter.ViewHolder> {

    private Map<String, Boolean> isChecked = new HashMap<>();
    private Context mContext;
    private LayoutInflater inflater;
    private List<String> serviceList;
    public Map<String, Boolean> punyaDimas = new HashMap<>();

    public MainChecklistAdapter(Context context, List<String> serviceList) {
        this.mContext = context;
        this.serviceList = serviceList;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_main_checklist, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.cbService.setText(serviceList.get(position));
        if (isChecked.containsKey(position)) {
            holder.cbService.setChecked(isChecked.get(position));
        } else {
            holder.cbService.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        CheckBox cbService;

        ViewHolder(@NonNull final View itemView) {
            super(itemView);

            cbService = itemView.findViewById(R.id.cb_main_checklist);
            cbService.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        // KONDISI PADA SAAT CEKLIS
                        punyaDimas.put(buttonView.getText() + "", true);
                    } else {
                        // KONDISI PADA SAAT CEKLIS DIHILANGKAN
                        punyaDimas.put(buttonView.getText() + "", false);
                    }
                    Toast.makeText(mContext, punyaDimas.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
