package com.qtechnetworks.ptplatform.Model.Beans.calender;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("is_auto_accept")
    @Expose
    private Boolean isAutoAccept;
    @SerializedName("is_available")
    @Expose
    private Boolean isAvailable;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Boolean getIsAutoAccept() {
        return isAutoAccept;
    }

    public void setIsAutoAccept(Boolean isAutoAccept) {
        this.isAutoAccept = isAutoAccept;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

}