package com.company.ptplatform.Model.Beans.WorkoutVideo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("video")
    @Expose
    private String video;
    @SerializedName("is_favourite")
    @Expose
    private Boolean isFavourite;
    @SerializedName("is_today_log")
    @Expose
    private Boolean isTodayLog;
    @SerializedName("is_workout")
    @Expose
    private Boolean isWorkout;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public Boolean getIsFavourite() {
        return isFavourite;
    }

    public void setIsFavourite(Boolean isFavourite) {
        this.isFavourite = isFavourite;
    }

    public Boolean getIsTodayLog() {
        return isTodayLog;
    }

    public void setIsTodayLog(Boolean isTodayLog) {
        this.isTodayLog = isTodayLog;
    }

    public Boolean getIsWorkout() {
        return isWorkout;
    }

    public void setIsWorkout(Boolean isWorkout) {
        this.isWorkout = isWorkout;
    }

}