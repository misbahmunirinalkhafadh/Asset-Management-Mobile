package com.mii.assetmanagement.model;

import android.os.Parcel;
import android.os.Parcelable;

public class SalesOrder implements Parcelable {
    private String soId;
    private String customerName;

    public SalesOrder(String soId, String customerName) {
        this.soId = soId;
        this.customerName = customerName;
    }

    public SalesOrder() {
    }

    public String getSoId() {
        return soId;
    }

    public void setSoId(String soId) {
        this.soId = soId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    private SalesOrder(Parcel in) {
        soId = in.readString();
        customerName = in.readString();
    }

    public static final Creator<SalesOrder> CREATOR = new Creator<SalesOrder>() {
        @Override
        public SalesOrder createFromParcel(Parcel in) {
            return new SalesOrder(in);
        }

        @Override
        public SalesOrder[] newArray(int size) {
            return new SalesOrder[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(soId);
        dest.writeString(customerName);
    }
}
