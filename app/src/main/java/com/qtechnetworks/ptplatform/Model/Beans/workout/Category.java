package com.qtechnetworks.ptplatform.Model.Beans.workout;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Category {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("exercises")
    @Expose
    private List<Exercise__1> exercises;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Exercise__1> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise__1> exercises) {
        this.exercises = exercises;
    }


}
