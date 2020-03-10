package com.mii.assetmanagement.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HistoryDetail {
    @SerializedName("detailpo")
    @Expose
    private List<PreOrder> preOrder;
    @SerializedName("reqdetail_id")
    @Expose
    private int reqDetailId;
    @SerializedName("sn")
    @Expose
    private String serial;

    public HistoryDetail() {
    }

    public List<PreOrder> getPreOrder() {
        return preOrder;
    }

    public void setPreOrder(List<PreOrder> preOrder) {
        this.preOrder = preOrder;
    }

    public int getReqDetailId() {
        return reqDetailId;
    }

    public void setReqDetailId(int reqDetailId) {
        this.reqDetailId = reqDetailId;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    /**
     * History Detail Pre Order
     */
    public static class PreOrder {
        @SerializedName("quantity")
        @Expose
        private int quantity;
        @SerializedName("brand")
        @Expose
        private String brand;

        public PreOrder() {
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }
    }
}
