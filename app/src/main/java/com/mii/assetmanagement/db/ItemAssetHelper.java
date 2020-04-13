package com.mii.assetmanagement.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import static android.os.Build.ID;
import static com.mii.assetmanagement.db.Database.TABLE_ITEM_ASSET;

public class ItemAssetHelper {
    private static final String DATABASE_TABLE = TABLE_ITEM_ASSET;
    private static DBHelper dataBaseHelper;
    private static SQLiteDatabase database;

    public ItemAssetHelper(Context context) {
        dataBaseHelper = new DBHelper(context);
    }

    public long insert(ContentValues values) {
        database = dataBaseHelper.getWritableDatabase();
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int update(String id, ContentValues values) {
        database = dataBaseHelper.getReadableDatabase();
        return database.update(DATABASE_TABLE, values, ID + " = ?", new String[]{id});
    }

    public void deleteAll() {
        //database = dataBaseHelper.getWritableDatabase();
        database.execSQL("delete from " + DATABASE_TABLE);
        database.close();
    }
}
