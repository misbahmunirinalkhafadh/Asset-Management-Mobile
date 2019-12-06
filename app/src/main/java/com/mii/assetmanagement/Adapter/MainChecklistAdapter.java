package com.mii.assetmanagement.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mii.assetmanagement.R;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class MainChecklistAdapter extends RecyclerView.Adapter<MainChecklistAdapter.ViewHolder> {

    private Context mContext;
    private LayoutInflater inflater;
    private List<String> serviceList;
    public boolean[] booleanList;

    public MainChecklistAdapter(Context context, List<String> serviceList, boolean[] booleanList) {
        this.mContext = context;
        this.serviceList = serviceList;
        this.booleanList = booleanList;
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

        Arrays.fill(booleanList, Boolean.FALSE);

        holder.cbService.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Array.setBoolean(booleanList, position, true);
                } else {
                    Array.setBoolean(booleanList, position, false);
                }
            }


        });

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
        }
    }
}
