package com.mii.assetmanagement.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HistoryResult implements Parcelable {
    @SerializedName("detailrequestid")
    @Expose
    private String id;
    @SerializedName("nik")
    @Expose
    private String nik;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("oldnik")
    @Expose
    private String oldNik;
    @SerializedName("oldname")
    @Expose
    private String oldName;
    @SerializedName("type")
    @Expose
    private String typeRequest;
    @SerializedName("branch")
    @Expose
    private String branch;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("tgl")
    @Expose
    private String date;
    @SerializedName("statusrequest")
    @Expose
    private String status;
    @SerializedName("item_asset")
    @Expose
    private int[] quantity;
    @SerializedName("brand")
    @Expose
    private String[] brand;

    public HistoryResult() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOldNik() {
        return oldNik;
    }

    public void setOldNik(String oldNik) {
        this.oldNik = oldNik;
    }

    public String getOldName() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

    public String getTypeRequest() {
        return typeRequest;
    }

    public void setTypeRequest(String typeRequest) {
        this.typeRequest = typeRequest;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int[] getQuantity() {
        return quantity;
    }

    public void setQuantity(int[] quantity) {
        this.quantity = quantity;
    }

    public String[] getBrand() {
        return brand;
    }

    public void setBrand(String[] brand) {
        this.brand = brand;
    }

    private HistoryResult(Parcel in) {
        id = in.readString();
        nik = in.readString();
        name = in.readString();
        oldNik = in.readString();
        oldName = in.readString();
        typeRequest = in.readString();
        branch = in.readString();
        location = in.readString();
        date = in.readString();
        status = in.readString();
        quantity = in.createIntArray();
        brand = in.createStringArray();
    }

    public static final Creator<HistoryResult> CREATOR = new Creator<HistoryResult>() {
        @Override
        public HistoryResult createFromParcel(Parcel in) {
            return new HistoryResult(in);
        }

        @Override
        public HistoryResult[] newArray(int size) {
            return new HistoryResult[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(nik);
        dest.writeString(name);
        dest.writeString(oldNik);
        dest.writeString(oldName);
        dest.writeString(typeRequest);
        dest.writeString(branch);
        dest.writeString(location);
        dest.writeString(date);
        dest.writeString(status);
        dest.writeIntArray(quantity);
        dest.writeStringArray(brand);
    }
}
