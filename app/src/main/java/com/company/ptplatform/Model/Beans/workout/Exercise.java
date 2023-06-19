package com.company.ptplatform.Model.Beans.workout;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Exercise {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("exercise")
    @Expose
    private Exercise__1 exercise;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Exercise__1 getExercise() {
        return exercise;
    }

    public void setExercise(Exercise__1 exercise) {
        this.exercise = exercise;
    }

}
