package com.mii.assetmanagement.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HistoryRequest {
    @SerializedName("result")
    @Expose
    private List<HistoryResult> result;
    @SerializedName("error")
    @Expose
    private boolean error;

    public HistoryRequest() {
    }

    public List<HistoryResult> getResult() {
        return result;
    }

    public void setResult(List<HistoryResult> result) {
        this.result = result;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}
