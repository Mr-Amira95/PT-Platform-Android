
package com.company.ptplatform.Model.Beans.Personalized;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Personalized {

    @SerializedName("data")
    @Expose
    private Datum data = null;
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("errors")
    @Expose
    private Object errors;

    public Datum getData() {
        return data;
    }

    public void setData(Datum data) {
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