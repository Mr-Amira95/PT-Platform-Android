package com.qtechnetworks.ptplatform.Model.Beans.FoodHome;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("user_target")
    @Expose
    private Integer userTarget;
    @SerializedName("food_target")
    @Expose
    private Integer foodTarget;
    @SerializedName("exercise_target")
    @Expose
    private Integer exerciseTarget;
    @SerializedName("food")
    @Expose
    private Food food;

    public Integer getUserTarget() {
        return userTarget;
    }

    public void setUserTarget(Integer userTarget) {
        this.userTarget = userTarget;
    }

    public Integer getFoodTarget() {
        return foodTarget;
    }

    public void setFoodTarget(Integer foodTarget) {
        this.foodTarget = foodTarget;
    }

    public Integer getExerciseTarget() {
        return exerciseTarget;
    }

    public void setExerciseTarget(Integer exerciseTarget) {
        this.exerciseTarget = exerciseTarget;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

}
