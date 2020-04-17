package com.mii.assetmanagement.adapter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mii.assetmanagement.R;

import static android.provider.BaseColumns._ID;
import static com.mii.assetmanagement.db.AssetContract.AssetEntry.COLUMN_ITEM;
import static com.mii.assetmanagement.db.AssetContract.AssetEntry.COLUMN_QTY;

public class RequestAssetAdapter extends RecyclerView.Adapter<RequestAssetAdapter.ViewHolder> {
    private Context mContext;
    private Cursor mCursor;
    private SQLiteDatabase mDatabase;

    public RequestAssetAdapter(Context mContext, Cursor mCursor, SQLiteDatabase mDatabase) {
        this.mContext = mContext;
        this.mCursor = mCursor;
        this.mDatabase = mDatabase;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_list_request_asset, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)) {
            return;
        }
        String item = mCursor.getString(mCursor.getColumnIndex(COLUMN_ITEM));
        int qty = mCursor.getInt(mCursor.getColumnIndex(COLUMN_QTY));
        long id = mCursor.getLong(mCursor.getColumnIndex(_ID));

        holder.tvItem.setText(item);
        holder.tvQty.setText(String.valueOf(qty));
        holder.itemView.setTag(id);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        if (mCursor != null) {
            mCursor.close();
        }
        mCursor = newCursor;
        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvItem, tvQty;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvItem = itemView.findViewById(R.id.tv_item);
            tvQty = itemView.findViewById(R.id.tv_qty);

        }
    }
}
