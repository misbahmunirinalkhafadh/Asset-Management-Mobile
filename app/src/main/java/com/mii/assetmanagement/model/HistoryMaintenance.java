package com.mii.assetmanagement.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HistoryMaintenance {
    @SerializedName("error")
    @Expose
    private boolean error;
    @SerializedName("result")
    @Expose
    private List<HistoryMaintenanceResult> results;
    @SerializedName("detailmaintenancepart")
    @Expose
    private List<HistoryMaintenanceResult> mainPart;
    @SerializedName("detailmaintenance")
    @Expose
    private List<HistoryMaintenanceResult> mainService;

    public HistoryMaintenance() {
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<HistoryMaintenanceResult> getResults() {
        return results;
    }

    public void setResults(List<HistoryMaintenanceResult> results) {
        this.results = results;
    }

    public List<HistoryMaintenanceResult> getMainPart() {
        return mainPart;
    }

    public void setMainPart(List<HistoryMaintenanceResult> mainPart) {
        this.mainPart = mainPart;
    }

    public List<HistoryMaintenanceResult> getMainService() {
        return mainService;
    }

    public void setMainService(List<HistoryMaintenanceResult> mainService) {
        this.mainService = mainService;
    }
}
