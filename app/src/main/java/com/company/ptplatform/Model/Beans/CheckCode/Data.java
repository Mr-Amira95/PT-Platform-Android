package com.company.ptplatform.Model.Beans.CheckCode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("token")
    @Expose
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String data) {
        this.token = token;
    }

}
