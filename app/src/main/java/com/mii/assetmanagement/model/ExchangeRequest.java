package com.mii.assetmanagement.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExchangeRequest {
    @SerializedName("requester")
    @Expose
    private String requester;
    @SerializedName("salesOrder")
    @Expose
    private String sales;
    @SerializedName("oldUser")
    @Expose
    private String oldUserAsset;
    @SerializedName("newUser")
    @Expose
    private String newUserAsset;
    @SerializedName("serialNumber")
    @Expose
    private String serial;
    @SerializedName("remark")
    @Expose
    private String reason;

    public ExchangeRequest(String requester, String sales, String oldUserAsset, String newUserAsset, String serial, String reason) {
        this.requester = requester;
        this.sales = sales;
        this.oldUserAsset = oldUserAsset;
        this.newUserAsset = newUserAsset;
        this.serial = serial;
        this.reason = reason;
    }

    public ExchangeRequest() {
    }

    public String getRequester() {
        return requester;
    }

    public void setRequester(String requester) {
        this.requester = requester;
    }

    public String getSales() {
        return sales;
    }

    public void setSales(String sales) {
        this.sales = sales;
    }

    public String getOldUserAsset() {
        return oldUserAsset;
    }

    public void setOldUserAsset(String oldUserAsset) {
        this.oldUserAsset = oldUserAsset;
    }

    public String getNewUserAsset() {
        return newUserAsset;
    }

    public void setNewUserAsset(String newUserAsset) {
        this.newUserAsset = newUserAsset;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
