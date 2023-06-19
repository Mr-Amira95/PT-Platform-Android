package com.company.ptplatform.Model.Beans.Progress;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Percentage {

    @SerializedName("value")
    @Expose
    private Float value;
    @SerializedName("type")
    @Expose
    private String type;

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {

        this.value =  value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}