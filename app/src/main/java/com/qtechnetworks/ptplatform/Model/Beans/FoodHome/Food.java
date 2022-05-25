package com.qtechnetworks.ptplatform.Model.Beans.FoodHome;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Food {

    @SerializedName("breakfast")
    @Expose
    private List<Breakfast> breakfast = null;
    @SerializedName("dinner")
    @Expose
    private List<Dinner> dinner = null;
    @SerializedName("snack")
    @Expose
    private List<Snack> snack = null;

    public List<Breakfast> getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(List<Breakfast> breakfast) {
        this.breakfast = breakfast;
    }

    public List<Dinner> getDinner() {
        return dinner;
    }

    public void setDinner(List<Dinner> dinner) {
        this.dinner = dinner;
    }

    public List<Snack> getSnack() {
        return snack;
    }

    public void setSnack(List<Snack> snack) {
        this.snack = snack;
    }

}
