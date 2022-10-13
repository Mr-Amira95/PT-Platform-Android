package com.qtechnetworks.ptplatform.Model.Beans.CheckCode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.qtechnetworks.ptplatform.Model.Beans.Challenge.ChallengeData;

import java.util.List;

public class ResendCodeResults {

    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("errors")
    @Expose
    private Data errors;

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

    public Data getErrors() {
        return errors;
    }

    public void setErrors(Data errors) {
        this.errors = errors;
    }


}
