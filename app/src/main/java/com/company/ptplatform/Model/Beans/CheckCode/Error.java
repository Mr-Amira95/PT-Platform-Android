package com.company.ptplatform.Model.Beans.CheckCode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Error {

    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("code")
    @Expose
    private String code;

    public String getToken() {
        return token;
    }

    public void setToken(String data) {
        this.token = token;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
