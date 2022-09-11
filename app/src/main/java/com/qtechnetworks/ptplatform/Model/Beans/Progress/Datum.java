package com.qtechnetworks.ptplatform.Model.Beans.Progress;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("weight")
    @Expose
    private Float weight;
    @SerializedName("muscle")
    @Expose
    private Float muscle;
    @SerializedName("fat")
    @Expose
    private Float fat;
    @SerializedName("water")
    @Expose
    private Float water;
    @SerializedName("active_calories")
    @Expose
    private Float active_calories;
    @SerializedName("steps")
    @Expose
    private Float steps;
    @SerializedName("percentage")
    @Expose
    private Percentage percentage;

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }
    public Percentage getPercentage() {
        return percentage;
    }
    public void setPercentage(Percentage percentage) {
        this.percentage = percentage;
    }
    public Float getMuscle() {
        return muscle;
    }

    public void setMuscle(Float muscle) {
        this.muscle = muscle;
    }


    public Float getFat() {
        return fat;
    }

    public void setFat(Float fat) {
        this.fat = fat;
    }

    public Float getWater() {
        return water;
    }

    public void setWater(Float water) {
        this.water = water;
    }

    public Float getActive_calories() {
        return active_calories;
    }

    public void setActive_calories(Float active_calories) {
        this.active_calories = active_calories;
    }

    public Float getSteps() {
        return steps;
    }

    public void setSteps(Float steps) {
        this.steps = steps;
    }
}
