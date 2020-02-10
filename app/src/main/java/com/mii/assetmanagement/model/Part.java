package com.mii.assetmanagement.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Part implements Parcelable {
    @SerializedName("Processor")
    @Expose
    private String Processor;
    @SerializedName("OS")
    @Expose
    private String OS;
    @SerializedName("HDD")
    @Expose
    private String HDD;
    @SerializedName("SSD")
    @Expose
    private String SSD;
    @SerializedName("RAM")
    @Expose
    private String RAM;

    public Part() {
    }

    public String getProcessor() {
        return Processor;
    }

    public void setProcessor(String processor) {
        Processor = processor;
    }

    public String getOS() {
        return OS;
    }

    public void setOS(String OS) {
        this.OS = OS;
    }

    public String getHDD() {
        return HDD;
    }

    public void setHDD(String HDD) {
        this.HDD = HDD;
    }

    public String getSSD() {
        return SSD;
    }

    public void setSSD(String SSD) {
        this.SSD = SSD;
    }

    public String getRAM() {
        return RAM;
    }

    public void setRAM(String RAM) {
        this.RAM = RAM;
    }

    private Part(Parcel in) {
        Processor = in.readString();
        OS = in.readString();
        HDD = in.readString();
        SSD = in.readString();
        RAM = in.readString();
    }

    public static final Creator<Part> CREATOR = new Creator<Part>() {
        @Override
        public Part createFromParcel(Parcel in) {
            return new Part(in);
        }

        @Override
        public Part[] newArray(int size) {
            return new Part[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Processor);
        dest.writeString(OS);
        dest.writeString(HDD);
        dest.writeString(SSD);
        dest.writeString(RAM);
    }
}
