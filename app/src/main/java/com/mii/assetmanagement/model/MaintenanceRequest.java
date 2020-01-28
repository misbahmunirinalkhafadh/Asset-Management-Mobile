package com.mii.assetmanagement.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MaintenanceRequest {
    @SerializedName("ram")
    @Expose
    private boolean ram;
    @SerializedName("hdd")
    @Expose
    private boolean hdd;
    @SerializedName("ssd")
    @Expose
    private boolean ssd;
    @SerializedName("os")
    @Expose
    private boolean os;
    @SerializedName("processor")
    @Expose
    private boolean processor;
    @SerializedName("service")
    @Expose
    private boolean[] service;
    @SerializedName("names")
    @Expose
    private String[] names;
    @SerializedName("ip")
    @Expose
    private String ip;
    @SerializedName("sn")
    @Expose
    private String sn;
    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("computerName")
    @Expose
    private String computerName;
    @SerializedName("maintainer")
    @Expose
    private String maintainer; // session

    public MaintenanceRequest(boolean ram, boolean hdd, boolean ssd, boolean os, boolean processor, boolean[] service, String ip, String sn, String result, String username, String computerName, String maintainer) {
        this.ram = ram;
        this.hdd = hdd;
        this.ssd = ssd;
        this.os = os;
        this.processor = processor;
        this.service = service;
        this.ip = ip;
        this.sn = sn;
        this.result = result;
        this.username = username;
        this.computerName = computerName;
        this.maintainer = maintainer;
    }

    public MaintenanceRequest() {

    }

    public boolean isRam() {
        return ram;
    }

    public void setRam(boolean ram) {
        this.ram = ram;
    }

    public boolean isHdd() {
        return hdd;
    }

    public void setHdd(boolean hdd) {
        this.hdd = hdd;
    }

    public boolean isSsd() {
        return ssd;
    }

    public void setSsd(boolean ssd) {
        this.ssd = ssd;
    }

    public boolean isOs() {
        return os;
    }

    public void setOs(boolean os) {
        this.os = os;
    }

    public boolean isProcessor() {
        return processor;
    }

    public void setProcessor(boolean processor) {
        this.processor = processor;
    }

    public boolean[] getService() {
        return service;
    }

    public void setService(boolean[] service) {
        this.service = service;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getComputerName() {
        return computerName;
    }

    public void setComputerName(String computerName) {
        this.computerName = computerName;
    }

    public String getMaintainer() {
        return maintainer;
    }

    public void setMaintainer(String maintainer) {
        this.maintainer = maintainer;
    }

    public String[] getNames() { return names; }

    public void setNames(String[] names) { this.names = names; }

}
