package com.qtechnetworks.ptplatform.Model.Beans.RegisterAndLogin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Register {

    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("errors")
    @Expose
    private Object errors;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Object getErrors() {
        return errors;
    }

    public void setErrors(Object errors) {
        this.errors = errors;
    }

}
