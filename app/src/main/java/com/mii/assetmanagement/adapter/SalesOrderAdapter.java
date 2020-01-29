package com.mii.assetmanagement.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mii.assetmanagement.CustomOnItemClickListener;
import com.mii.assetmanagement.R;
import com.mii.assetmanagement.model.SalesOrder;
import com.mii.assetmanagement.view.RequestActivity;

import java.util.ArrayList;

public class SalesOrderAdapter extends RecyclerView.Adapter<SalesOrderAdapter.SoViewHolder> {
    private ArrayList<SalesOrder> listSales = new ArrayList<>();
    private Activity activity;

    public SalesOrderAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setSalesOrders(ArrayList<SalesOrder> salesOrders) {
        if (salesOrders.size() > 0) {
            this.listSales.clear();
        }
        listSales.addAll(salesOrders);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_so, viewGroup, false);
        return new SoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SoViewHolder holder, int position) {
        final SalesOrder salesOrder = listSales.get(position);

        final String soId = salesOrder.getSoId();
        String company = salesOrder.getCustomerName();
        holder.tvSoNumber.setText(soId);
        holder.tvCompanyName.setText(company);
        holder.layoutSalesOrder.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Toast.makeText(view.getContext(), "anda memilih " + listSales, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(activity, RequestActivity.class);
//                intent.putExtra(RequestActivity.EXTRA_SALES_ORDER, listSales);
                SalesOrder data = new SalesOrder();
                data.setSoId(salesOrder.getSoId());
                intent.putExtra("EXTRA_SALES_ORDER", data);
                activity.startActivity(intent);
            }
        }));
    }

    @Override
    public int getItemCount() {
        return listSales.size();
    }

    class SoViewHolder extends RecyclerView.ViewHolder {
        TextView tvSoNumber, tvCompanyName;
        LinearLayout layoutSalesOrder;

        SoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSoNumber = itemView.findViewById(R.id.tv_sonumber);
            tvCompanyName = itemView.findViewById(R.id.tv_company);
            layoutSalesOrder = itemView.findViewById(R.id.layout_sales_order);
        }
    }
}


