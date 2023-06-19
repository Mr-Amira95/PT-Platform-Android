package com.company.ptplatform.Model.Beans.CheckEmail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Error {
    @SerializedName("email")
    @Expose
    private String email;

    public String getEmail() {
        return email;
    }

    public void setToken(String data) {
        this.email = email;
    }

}
