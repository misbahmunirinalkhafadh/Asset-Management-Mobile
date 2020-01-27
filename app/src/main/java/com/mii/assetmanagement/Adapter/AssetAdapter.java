package com.mii.assetmanagement.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.mii.assetmanagement.Model.Asset;

import java.util.ArrayList;

public class AssetAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Asset> assets = new ArrayList<>();

    public AssetAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<Asset> getAssets() {
        return assets;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
