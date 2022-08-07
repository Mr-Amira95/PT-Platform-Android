package com.qtechnetworks.ptplatform.Model.Beans.Target;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("target_calorie")
    @Expose
    private Integer targetCalorie;
    @SerializedName("target_carb")
    @Expose
    private Integer targetCarb;
    @SerializedName("target_fat")
    @Expose
    private Integer targetFat;
    @SerializedName("target_protein")
    @Expose
    private Integer targetProtein;

    public Integer getTargetCalorie() {
        return targetCalorie;
    }

    public void setTargetCalorie(Integer targetCalorie) {
        this.targetCalorie = targetCalorie;
    }

    public Integer getTargetCarb() {
        return targetCarb;
    }

    public void setTargetCarb(Integer targetCarb) {
        this.targetCarb = targetCarb;
    }

    public Integer getTargetFat() {
        return targetFat;
    }

    public void setTargetFat(Integer targetFat) {
        this.targetFat = targetFat;
    }

    public Integer getTargetProtein() {
        return targetProtein;
    }

    public void setTargetProtein(Integer targetProtein) {
        this.targetProtein = targetProtein;
    }

}
