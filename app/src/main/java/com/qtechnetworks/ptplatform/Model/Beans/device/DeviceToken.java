package com.qtechnetworks.ptplatform.Model.Beans.device;

/**
 * Created by ghait on 28,January,2022
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeviceToken {

    @SerializedName("data")
    @Expose
    private String data;
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private Object message;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

}
