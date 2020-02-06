package com.mii.assetmanagement.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SalesOrder implements Parcelable {
    @SerializedName("sales_order")
    @Expose
    private String soId;
    @SerializedName("customer_name")
    @Expose
    private String customerName;
    @SerializedName("asset_type")
    @Expose
    private String assetType;
    @SerializedName("error")
    @Expose
    private boolean error;

    public SalesOrder() {
    }

    /**
     * Setter Getter Generate
     *
     * @return
     */
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

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public boolean getError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    /**
     * Parcelable Generate
     *
     * @param in
     */
    private SalesOrder(Parcel in) {
        soId = in.readString();
        customerName = in.readString();
        assetType = in.readString();
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
        dest.writeString(assetType);
    }
}
