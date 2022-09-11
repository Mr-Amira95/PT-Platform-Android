package com.qtechnetworks.ptplatform.Model.Beans.Personalized;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.qtechnetworks.ptplatform.Model.Beans.Exercises.Category;

import java.util.List;

public class Datum {

    @SerializedName("video")
    @Expose
    private List<Video> video = null;
    @SerializedName("image")
    @Expose
    private List<Image> image = null;
    @SerializedName("pdf")
    @Expose
    private List<Pdf> pdf = null;

    public List<Video> getVideo() {
        return video;
    }

    public void setVideo(List<Video> video) {
        this.video = video;
    }

    public List<Image> getImage() {
        return image;
    }

    public void setImage(List<Image> image) {
        this.image = image;
    }

    public List<Pdf> getPdf() {
        return pdf;
    }

    public void setPdf(List<Pdf> pdf) {
        this.pdf = pdf;
    }

}
