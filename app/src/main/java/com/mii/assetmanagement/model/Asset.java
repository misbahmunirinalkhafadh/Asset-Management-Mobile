package com.mii.assetmanagement.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Asset implements Parcelable {
    @SerializedName("parts")
    @Expose
    private Part parts;
    @SerializedName("user")
    @Expose
    private Employee employee;
    @SerializedName("salesOrder")
    @Expose
    private String salesOrder;
    @SerializedName("companyname")
    @Expose
    private String company;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("brand")
    @Expose
    private String brand;
    @SerializedName("serialNumber")
    @Expose
    private String serialNumber;
    @SerializedName("type_serialnumberName")
    @Expose
    private String typeSerialNumber;
    @SerializedName("others")
    @Expose
    private String[] others;
    @SerializedName("error")
    @Expose
    private boolean error;

    public Asset() {
    }

    public Part getParts() {
        return parts;
    }

    public void setParts(Part parts) {
        this.parts = parts;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getSalesOrder() {
        return salesOrder;
    }

    public void setSalesOrder(String salesOrder) {
        this.salesOrder = salesOrder;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getTypeSerialNumber() {
        return typeSerialNumber;
    }

    public void setTypeSerialNumber(String typeSerialNumber) {
        this.typeSerialNumber = typeSerialNumber;
    }

    public String[] getOthers() {
        return others;
    }

    public void setOthers(String[] others) {
        this.others = others;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    protected Asset(Parcel in) {
        salesOrder = in.readString();
        company = in.readString();
        type = in.readString();
        brand = in.readString();
        serialNumber = in.readString();
        typeSerialNumber = in.readString();
        others = in.createStringArray();
        error = in.readByte() != 0;
    }

    public static final Creator<Asset> CREATOR = new Creator<Asset>() {
        @Override
        public Asset createFromParcel(Parcel in) {
            return new Asset(in);
        }

        @Override
        public Asset[] newArray(int size) {
            return new Asset[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(salesOrder);
        dest.writeString(company);
        dest.writeString(type);
        dest.writeString(brand);
        dest.writeString(serialNumber);
        dest.writeString(typeSerialNumber);
        dest.writeStringArray(others);
        dest.writeByte((byte) (error ? 1 : 0));
    }
}

