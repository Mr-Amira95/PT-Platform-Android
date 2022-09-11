package com.qtechnetworks.ptplatform.Model.Beans.Challenge;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VideoChallenge {
    @SerializedName("data")
    @Expose
    private List<VideoChallengeData> data = null;
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("errors")
    @Expose
    private Object errors;

    public List<VideoChallengeData> getData() {
        return data;
    }

    public void setData(List<VideoChallengeData> data) {
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
