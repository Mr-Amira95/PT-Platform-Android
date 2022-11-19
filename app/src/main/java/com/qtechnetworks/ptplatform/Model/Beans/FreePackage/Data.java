package com.qtechnetworks.ptplatform.Model.Beans.FreePackage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("success")
    @Expose
    private Boolean success;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
