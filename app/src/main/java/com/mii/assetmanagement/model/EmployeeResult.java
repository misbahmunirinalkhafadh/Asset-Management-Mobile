package com.mii.assetmanagement.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmployeeResult {
    @SerializedName("empl_nik")
    @Expose
    private int nik;
    @SerializedName("empl_name")
    @Expose
    private String emplName;
    @SerializedName("error")
    @Expose
    private boolean error;

    public EmployeeResult() {

    }

    public int getNik() {
        return nik;
    }

    public void setNik(int nik) {
        this.nik = nik;
    }

    public String getEmplName() {
        return emplName;
    }

    public void setEmplName(String emplName) {
        this.emplName = emplName;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}
