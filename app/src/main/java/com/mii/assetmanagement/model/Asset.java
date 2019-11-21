package com.mii.assetmanagement.model;

public class Asset {
    private Part parts;
    private Boolean error;
    private String assetItemName;
    private String serialNumber;

    public Part getParts() {
        return parts;
    }

    public void setParts(Part parts) {
        this.parts = parts;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getAssetItemName() {
        return assetItemName;
    }

    public void setAssetItemName(String assetItemName) {
        this.assetItemName = assetItemName;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
}
