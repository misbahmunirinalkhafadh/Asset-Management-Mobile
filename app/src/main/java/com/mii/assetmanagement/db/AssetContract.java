package com.mii.assetmanagement.db;

import android.provider.BaseColumns;

public class AssetContract {
   public static final String TABLE_NAME = "request_new_asset";

    public AssetContract() {
    }

    public static class AssetEntry implements BaseColumns {
        public static final String COLUMN_ITEM = "item";
        public static final String COLUMN_QTY = "qty";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }
}
