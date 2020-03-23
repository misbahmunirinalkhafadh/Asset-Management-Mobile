package com.mii.assetmanagement.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HistoryMaintenanceResult implements Parcelable {
    @SerializedName("id")
    @Expose
    private int _id;
    @SerializedName("brand")
    @Expose
    private String brand;
    @SerializedName("SN")
    @Expose
    private String serial;
    @SerializedName("tgl")
    @Expose
    private String date;

    public HistoryMaintenanceResult() {
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private HistoryMaintenanceResult(Parcel in) {
        _id = in.readInt();
        brand = in.readString();
        serial = in.readString();
        date = in.readString();
    }

    public static final Creator<HistoryMaintenanceResult> CREATOR = new Creator<HistoryMaintenanceResult>() {
        @Override
        public HistoryMaintenanceResult createFromParcel(Parcel in) {
            return new HistoryMaintenanceResult(in);
        }

        @Override
        public HistoryMaintenanceResult[] newArray(int size) {
            return new HistoryMaintenanceResult[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(_id);
        dest.writeString(brand);
        dest.writeString(serial);
        dest.writeString(date);
    }
}
