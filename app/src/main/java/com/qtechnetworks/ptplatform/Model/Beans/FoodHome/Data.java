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
    @SerializedName("carb")
    @Expose
    private Integer carb;
    @SerializedName("fat")
    @Expose
    private Integer fat;
    @SerializedName("protein")
    @Expose
    private Integer protein;
    @SerializedName("user")
    @Expose
    private User user;
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

    public Integer getCarb() {
        return carb;
    }

    public void setCarb(Integer carb) {
        this.carb = carb;
    }

    public Integer getFat() {
        return fat;
    }

    public void setFat(Integer fat) {
        this.fat = fat;
    }

    public Integer getProtein() {
        return protein;
    }

    public void setProtein(Integer protein) {
        this.protein = protein;
    }

    public User getUser() {
        return user;
    }

    public void setCarbTarget(User user) {
        this.user = user;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

}
