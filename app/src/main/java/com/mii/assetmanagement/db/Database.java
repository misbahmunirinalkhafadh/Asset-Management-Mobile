package com.mii.assetmanagement.db;

import android.provider.BaseColumns;

public class Database {
    public static String TABLE_ITEM_ASSET = "item_asset";

    public Database() {
    }

    public static final class ItemAssetColumns implements BaseColumns {
        public static String ID = "id";
        public static String BRAND = "brand";
        public static String QTY = "qty";
    }
}
