package com.mii.assetmanagement.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AssetDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "AssetManagement.db";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_ASSET = String.format("CREATE TABLE %s" +
                    " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s INTEGER NOT NULL," +
                    " %s TIMESTAMP DEFAULT CURRENT_TIMESTAMP)",
            AssetContract.TABLE_NAME,
            AssetContract.AssetEntry._ID,
            AssetContract.AssetEntry.COLUMN_ITEM,
            AssetContract.AssetEntry.COLUMN_QTY,
            AssetContract.AssetEntry.COLUMN_TIMESTAMP
    );

    public AssetDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_ASSET);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + AssetContract.TABLE_NAME);
        onCreate(db);
    }
}
