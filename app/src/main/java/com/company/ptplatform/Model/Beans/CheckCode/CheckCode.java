package com.company.ptplatform.Model.Beans.CheckCode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckCode {

    @SerializedName("data")
    @Expose
    private String data;
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("errors")
    @Expose
    private Error errors;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Error getErrors() {
        return errors;
    }

    public void setErrors(Error errors) {
        this.errors = errors;
    }

}
