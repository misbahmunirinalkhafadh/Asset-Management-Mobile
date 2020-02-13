package com.mii.assetmanagement.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Brand {
    @SerializedName("Thin Client")
    @Expose
    private String thinClient;
    @SerializedName("Monitor LCD")
    @Expose
    private String monitorLCD;
    @SerializedName("Desktop PC")
    @Expose
    private String desktopPC;
    @SerializedName("Notebook")
    @Expose
    private String notebook;
    @SerializedName("error")
    @Expose
    private boolean error;

    public Brand() {
    }

    public String getThinClient() {
        return thinClient;
    }

    public void setThinClient(String thinClient) {
        this.thinClient = thinClient;
    }

    public String getMonitorLCD() {
        return monitorLCD;
    }

    public void setMonitorLCD(String monitorLCD) {
        this.monitorLCD = monitorLCD;
    }

    public String getDesktopPC() {
        return desktopPC;
    }

    public void setDesktopPC(String desktopPC) {
        this.desktopPC = desktopPC;
    }

    public String getNotebook() {
        return notebook;
    }

    public void setNotebook(String notebook) {
        this.notebook = notebook;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}
