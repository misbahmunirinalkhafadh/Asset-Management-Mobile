package com.mii.assetmanagement.model;

import android.os.Parcel;
import android.os.Parcelable;

public class AssetResult implements Parcelable {
    private String category;
    private String [] brand;

    public AssetResult() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String[] getBrand() {
        return brand;
    }

    public void setBrand(String[] brand) {
        this.brand = brand;
    }

    protected AssetResult(Parcel in) {
        category = in.readString();
        brand = in.createStringArray();
    }

    public static final Creator<AssetResult> CREATOR = new Creator<AssetResult>() {
        @Override
        public AssetResult createFromParcel(Parcel in) {
            return new AssetResult(in);
        }

        @Override
        public AssetResult[] newArray(int size) {
            return new AssetResult[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(category);
        dest.writeStringArray(brand);
    }
}
