package com.qtechnetworks.ptplatform.Model.Beans.FoodHome;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Food {

    @SerializedName("breakfast")
    @Expose
    private List<Snack> breakfast = null;
    @SerializedName("dinner")
    @Expose
    private List<Snack> dinner = null;
    @SerializedName("snack")
    @Expose
    private List<Snack> snack = null;
    @SerializedName("supplements")
    @Expose
    private List<Snack> supplements = null;
    @SerializedName("lunch")
    @Expose
    private List<Snack> lunch = null;

    public List<Snack> getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(List<Snack> breakfast) {
        this.breakfast = breakfast;
    }

    public List<Snack> getDinner() {
        return dinner;
    }

    public void setDinner(List<Snack> dinner) {
        this.dinner = dinner;
    }

    public List<Snack> getSnack() {
        return snack;
    }

    public void setSnack(List<Snack> snack) {
        this.snack = snack;
    }

    public List<Snack> getSupplements() {
        return supplements;
    }

    public void setSupplements(List<Snack> supplements) {
        this.supplements = supplements;
    }

    public List<Snack> getLunch() {
        return lunch;
    }

    public void setLunch(List<Snack> lunch) {
        this.lunch = lunch;
    }

}
