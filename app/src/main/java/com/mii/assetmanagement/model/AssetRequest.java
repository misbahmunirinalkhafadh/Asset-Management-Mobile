package com.mii.assetmanagement.model;

import com.google.gson.annotations.SerializedName;

public class AssetRequest {
    @SerializedName("salesOrder")
    private String salesOrder;
    @SerializedName("assignee")
    private String assignee;
    @SerializedName("requester")
    private String requester;
    @SerializedName("brands")
    private String[] brands;
    @SerializedName("quantity")
    private int[] quantity;
    @SerializedName("remarks")
    private String reason;

    public AssetRequest() {
    }

    public String getSalesOrder() {
        return salesOrder;
    }

    public void setSalesOrder(String salesOrder) {
        this.salesOrder = salesOrder;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getRequester() {
        return requester;
    }

    public void setRequester(String requester) {
        this.requester = requester;
    }

    public String[] getBrands() {
        return brands;
    }

    public void setBrands(String[] brands) {
        this.brands = brands;
    }

    public int[] getQuantity() {
        return quantity;
    }

    public void setQuantity(int[] quantity) {
        this.quantity = quantity;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
