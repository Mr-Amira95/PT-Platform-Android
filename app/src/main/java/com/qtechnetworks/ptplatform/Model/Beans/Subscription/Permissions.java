package com.qtechnetworks.ptplatform.Model.Beans.Subscription;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Permissions {

    @SerializedName("call_video")
    @Expose
    private Integer callVideo;
    @SerializedName("workout_schedule")
    @Expose
    private Boolean workoutSchedule;
    @SerializedName("food_plan")
    @Expose
    private Boolean foodPlan;

    public Integer getCallVideo() {
        return callVideo;
    }

    public void setCallVideo(Integer callVideo) {
        this.callVideo = callVideo;
    }

    public Boolean getWorkoutSchedule() {
        return workoutSchedule;
    }

    public void setWorkoutSchedule(Boolean workoutSchedule) {
        this.workoutSchedule = workoutSchedule;
    }

    public Boolean getFoodPlan() {
        return foodPlan;
    }

    public void setFoodPlan(Boolean foodPlan) {
        this.foodPlan = foodPlan;
    }

}
