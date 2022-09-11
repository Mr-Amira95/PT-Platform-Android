package com.qtechnetworks.ptplatform.Model.Beans.Challenge;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.qtechnetworks.ptplatform.Model.Beans.videoExercises.Datum;

public class VideoChallengeData {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("is_complete")
    @Expose
    private Boolean isComplete;
    @SerializedName("video")
    @Expose
    private Datum video;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getIsComplete() {
        return isComplete;
    }

    public void setIsComplete(Boolean isComplete) {
        this.isComplete = isComplete;
    }

    public Datum getVideo() {
        return video;
    }

    public void setVideo(Datum video) {
        this.video = video;
    }

}
