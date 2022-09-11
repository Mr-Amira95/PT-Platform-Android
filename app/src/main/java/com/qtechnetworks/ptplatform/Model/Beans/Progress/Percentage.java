package com.qtechnetworks.ptplatform.Model.Beans.Progress;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.DecimalFormat;

public class Percentage {

    @SerializedName("value")
    @Expose
    private Float value;
    @SerializedName("type")
    @Expose
    private String type;

    public String getValue() {
        DecimalFormat df = new DecimalFormat("#.#");
        return df.format(value);
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