package com.mii.assetmanagement;

import android.content.SharedPreferences;

public class AssetPrefManager {
    public static final String AP_SALES = "apSales";
    public static final String AP_SERIAL = "apSerial";
    public static final String AP_BRAND = "apBrand";
    public static final String AP_TYPE = "apType";

    SharedPreferences ap;

    public static String getApSales() {
        return AP_SALES;
    }

    public static String getApSerial() {
        return AP_SERIAL;
    }

    public static String getApBrand() {
        return AP_BRAND;
    }

    public static String getApType() {
        return AP_TYPE;
    }
}
