package com.company.ptplatform.Model.Beans.VideoChat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {


    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("is_auto_accept")
    @Expose
    private Integer isAutoAccept;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("deleted_at")
    @Expose
    private Object deletedAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("coach_time_reservation_accept")
    @Expose
    private CoachTimeReservation coachTimeReservation;
    @SerializedName("coach_time_reservation")
    @Expose
    private CoachTimeReservation coachReservation;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getIsAutoAccept() {
        return isAutoAccept;
    }

    public void setIsAutoAccept(Integer isAutoAccept) {
        this.isAutoAccept = isAutoAccept;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Object getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Object deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public CoachTimeReservation getCoachTimeReservation() {
        return coachTimeReservation;
    }

    public void setCoachTimeReservation(CoachTimeReservation coachTimeReservation) {
        this.coachTimeReservation = coachTimeReservation;
    }

    public CoachTimeReservation getCoachReservation() {
        return coachReservation;
    }

    public void setCoachReservation(CoachTimeReservation coachReservation) {
        this.coachReservation = coachReservation;
    }

}
