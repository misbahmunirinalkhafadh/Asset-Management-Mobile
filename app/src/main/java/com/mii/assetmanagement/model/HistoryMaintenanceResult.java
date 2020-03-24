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
    @SerializedName("description")
    @Expose
    private String reason;

    //detail maintenance
    @SerializedName("partname")
    @Expose
    private String partName;
    @SerializedName("parttype")
    @Expose
    private String partType;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("status")
    @Expose
    private String status;

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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public String getPartType() {
        return partType;
    }

    public void setPartType(String partType) {
        this.partType = partType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private HistoryMaintenanceResult(Parcel in) {
        _id = in.readInt();
        brand = in.readString();
        serial = in.readString();
        date = in.readString();
        reason = in.readString();
        partName = in.readString();
        partType = in.readString();
        name = in.readString();
        status = in.readString();
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
        dest.writeString(reason);
        dest.writeString(partName);
        dest.writeString(partType);
        dest.writeString(name);
        dest.writeString(status);
    }
}
