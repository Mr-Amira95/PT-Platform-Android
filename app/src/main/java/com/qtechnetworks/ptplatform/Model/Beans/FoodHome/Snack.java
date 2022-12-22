package com.qtechnetworks.ptplatform.Model.Beans.FoodHome;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Snack {

    @SerializedName("user_food_id")
    @Expose
    private Integer userFoodID;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("sku")
    @Expose
    private String sku;
    @SerializedName("calorie")
    @Expose
    private Integer calorie;
    @SerializedName("carb")
    @Expose
    private double carb;
    @SerializedName("fat")
    @Expose
    private double fat;
    @SerializedName("protein")
    @Expose
    private double protein;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("title")
    @Expose
    private String title;

    public Integer getUserFoodID() {
        return userFoodID;
    }

    public void setUserFoodID(Integer userFoodID) {
        this.userFoodID = userFoodID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getCalorie() {
        return calorie;
    }

    public void setCalorie(Integer calorie) {
        this.calorie = calorie;
    }

    public double getCarb() {
        return carb;
    }

    public void setCarb(double carb) {
        this.carb = carb;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
