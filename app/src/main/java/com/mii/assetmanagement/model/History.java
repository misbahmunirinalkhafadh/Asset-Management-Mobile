package com.mii.assetmanagement.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class History implements Parcelable {
    @SerializedName("")
    @Expose
    private String sales;
    @SerializedName("")
    @Expose
    private String typeRequest;
    @SerializedName("")
    @Expose
    private String brand;
    @SerializedName("")
    @Expose
    private String category;
    @SerializedName("")
    @Expose
    private String date;

    public History() {
    }

    public History(String sales, String typeRequest, String brand, String category, String date) {
        this.sales = sales;
        this.typeRequest = typeRequest;
        this.brand = brand;
        this.category = category;
        this.date = date;
    }

    public String getSales() {
        return sales;
    }

    public void setSales(String sales) {
        this.sales = sales;
    }

    public String getTypeRequest() {
        return typeRequest;
    }

    public void setTypeRequest(String typeRequest) {
        this.typeRequest = typeRequest;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    protected History(Parcel in) {
        sales = in.readString();
        typeRequest = in.readString();
        brand = in.readString();
        category = in.readString();
        date = in.readString();
    }

    public static final Creator<History> CREATOR = new Creator<History>() {
        @Override
        public History createFromParcel(Parcel in) {
            return new History(in);
        }

        @Override
        public History[] newArray(int size) {
            return new History[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(sales);
        dest.writeString(typeRequest);
        dest.writeString(brand);
        dest.writeString(category);
        dest.writeString(date);
    }
}
