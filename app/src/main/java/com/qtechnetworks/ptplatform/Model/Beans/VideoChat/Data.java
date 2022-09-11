
package com.qtechnetworks.ptplatform.Model.Beans.VideoChat;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Data {

    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("host_id")
    @Expose
    private String hostId;
    @SerializedName("host_email")
    @Expose
    private String hostEmail;
    @SerializedName("topic")
    @Expose
    private String topic;
    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("start_time")
    @Expose
    private String startTime;
    @SerializedName("duration")
    @Expose
    private Integer duration;
    @SerializedName("timezone")
    @Expose
    private String timezone;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("start_url")
    @Expose
    private String startUrl;
    @SerializedName("join_url")
    @Expose
    private String joinUrl;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("h323_password")
    @Expose
    private String h323Password;
    @SerializedName("pstn_password")
    @Expose
    private String pstnPassword;
    @SerializedName("encrypted_password")
    @Expose
    private String encryptedPassword;
    @SerializedName("settings")
    @Expose
    private Settings settings;
    @SerializedName("pre_schedule")
    @Expose
    private Boolean preSchedule;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public String getHostEmail() {
        return hostEmail;
    }

    public void setHostEmail(String hostEmail) {
        this.hostEmail = hostEmail;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getStartUrl() {
        return startUrl;
    }

    public void setStartUrl(String startUrl) {
        this.startUrl = startUrl;
    }

    public String getJoinUrl() {
        return joinUrl;
    }

    public void setJoinUrl(String joinUrl) {
        this.joinUrl = joinUrl;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getH323Password() {
        return h323Password;
    }

    public void setH323Password(String h323Password) {
        this.h323Password = h323Password;
    }

    public String getPstnPassword() {
        return pstnPassword;
    }

    public void setPstnPassword(String pstnPassword) {
        this.pstnPassword = pstnPassword;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public Boolean getPreSchedule() {
        return preSchedule;
    }

    public void setPreSchedule(Boolean preSchedule) {
        this.preSchedule = preSchedule;
    }

}
