
package com.company.ptplatform.Model.Beans.VideoChat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApprovedOrDeniedCountriesOrRegions {

    @SerializedName("enable")
    @Expose
    private Boolean enable;

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

}
