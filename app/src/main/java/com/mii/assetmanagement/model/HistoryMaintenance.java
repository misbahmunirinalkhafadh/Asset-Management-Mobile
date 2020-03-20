package com.mii.assetmanagement.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HistoryMaintenance {
    @SerializedName("error")
    @Expose
    private boolean error;
    @SerializedName("result")
    @Expose
    private List<MaintenanceResult> results;

    public HistoryMaintenance() {
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<MaintenanceResult> getResults() {
        return results;
    }

    public void setResults(List<MaintenanceResult> results) {
        this.results = results;
    }

    public static class MaintenanceResult implements Parcelable  {
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

        public MaintenanceResult() {
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

        MaintenanceResult(Parcel in) {
            _id = in.readInt();
            brand = in.readString();
            serial = in.readString();
            date = in.readString();
        }

        public final Creator<MaintenanceResult> CREATOR = new Creator<MaintenanceResult>() {
            @Override
            public MaintenanceResult createFromParcel(Parcel in) {
                return new MaintenanceResult(in);
            }

            @Override
            public MaintenanceResult[] newArray(int size) {
                return new MaintenanceResult[size];
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
}