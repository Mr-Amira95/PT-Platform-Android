package com.qtechnetworks.ptplatform.Model.Beans.BodyMeasurement;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Datum {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("neck")
    @Expose
    private Double neck;
    @SerializedName("chest")
    @Expose
    private Double chest;
    @SerializedName("left_arm")
    @Expose
    private Double leftArm;
    @SerializedName("right_arm")
    @Expose
    private Double rightArm;
    @SerializedName("waist")
    @Expose
    private Double waist;
    @SerializedName("belly")
    @Expose
    private Double belly;
    @SerializedName("lower_belly")
    @Expose
    private Double lower_belly;
    @SerializedName("upper_belly")
    @Expose
    private Double upper_belly;
    @SerializedName("hips")
    @Expose
    private Double hips;
    @SerializedName("left_thigh")
    @Expose
    private Double leftThigh;
    @SerializedName("right_thigh")
    @Expose
    private Double rightThigh;
    @SerializedName("lift_calf")
    @Expose
    private Double liftCalf;
    @SerializedName("right_calf")
    @Expose
    private Double rightCalf;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getNeck() {
        return neck;
    }

    public void setNeck(Double neck) {
        this.neck = neck;
    }

    public Double getChest() {
        return chest;
    }

    public void setChest(Double chest) {
        this.chest = chest;
    }

    public Double getLeftArm() {
        return leftArm;
    }

    public void setLeftArm(Double leftArm) {
        this.leftArm = leftArm;
    }

    public Double getRightArm() {
        return rightArm;
    }

    public void setRightArm(Double rightArm) {
        this.rightArm = rightArm;
    }

    public Double getWaist() {
        return waist;
    }

    public void setWaist(Double waist) {
        this.waist = waist;
    }

    public Double getBelly() {
        return belly;
    }

    public void setBelly(Double belly) {
        this.belly = belly;
    }

    public Double getHips() {
        return hips;
    }

    public void setHips(Double hips) {
        this.hips = hips;
    }

    public Double getLeftThigh() {
        return leftThigh;
    }

    public void setLeftThigh(Double leftThigh) {
        this.leftThigh = leftThigh;
    }

    public Double getRightThigh() {
        return rightThigh;
    }

    public void setRightThigh(Double rightThigh) {
        this.rightThigh = rightThigh;
    }

    public Double getLiftCalf() {
        return liftCalf;
    }

    public void setLiftCalf(Double liftCalf) {
        this.liftCalf = liftCalf;
    }

    public Double getRightCalf() {
        return rightCalf;
    }

    public void setRightCalf(Double rightCalf) {
        this.rightCalf = rightCalf;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getLower_belly() {
        return lower_belly;
    }

    public void setLower_belly(Double lower_belly) {
        this.lower_belly = lower_belly;
    }

    public Double getUpper_belly() {
        return upper_belly;
    }

    public void setUpper_belly(Double upper_belly) {
        this.upper_belly = upper_belly;
    }
}