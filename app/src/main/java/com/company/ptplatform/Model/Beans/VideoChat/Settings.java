
package com.company.ptplatform.Model.Beans.VideoChat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Settings {

    @SerializedName("host_video")
    @Expose
    private Boolean hostVideo;
    @SerializedName("participant_video")
    @Expose
    private Boolean participantVideo;
    @SerializedName("cn_meeting")
    @Expose
    private Boolean cnMeeting;
    @SerializedName("in_meeting")
    @Expose
    private Boolean inMeeting;
    @SerializedName("join_before_host")
    @Expose
    private Boolean joinBeforeHost;
    @SerializedName("jbh_time")
    @Expose
    private Integer jbhTime;
    @SerializedName("mute_upon_entry")
    @Expose
    private Boolean muteUponEntry;
    @SerializedName("watermark")
    @Expose
    private Boolean watermark;
    @SerializedName("use_pmi")
    @Expose
    private Boolean usePmi;
    @SerializedName("approval_type")
    @Expose
    private Integer approvalType;
    @SerializedName("audio")
    @Expose
    private String audio;
    @SerializedName("auto_recording")
    @Expose
    private String autoRecording;
    @SerializedName("enforce_login")
    @Expose
    private Boolean enforceLogin;
    @SerializedName("enforce_login_domains")
    @Expose
    private String enforceLoginDomains;
    @SerializedName("alternative_hosts")
    @Expose
    private String alternativeHosts;
    @SerializedName("alternative_host_update_polls")
    @Expose
    private Boolean alternativeHostUpdatePolls;
    @SerializedName("close_registration")
    @Expose
    private Boolean closeRegistration;
    @SerializedName("show_share_button")
    @Expose
    private Boolean showShareButton;
    @SerializedName("allow_multiple_devices")
    @Expose
    private Boolean allowMultipleDevices;
    @SerializedName("registrants_confirmation_email")
    @Expose
    private Boolean registrantsConfirmationEmail;
    @SerializedName("waiting_room")
    @Expose
    private Boolean waitingRoom;
    @SerializedName("request_permission_to_unmute_participants")
    @Expose
    private Boolean requestPermissionToUnmuteParticipants;
    @SerializedName("registrants_email_notification")
    @Expose
    private Boolean registrantsEmailNotification;
    @SerializedName("meeting_authentication")
    @Expose
    private Boolean meetingAuthentication;
    @SerializedName("encryption_type")
    @Expose
    private String encryptionType;
    @SerializedName("approved_or_denied_countries_or_regions")
    @Expose
    private ApprovedOrDeniedCountriesOrRegions approvedOrDeniedCountriesOrRegions;
    @SerializedName("breakout_room")
    @Expose
    private BreakoutRoom breakoutRoom;
    @SerializedName("alternative_hosts_email_notification")
    @Expose
    private Boolean alternativeHostsEmailNotification;
    @SerializedName("device_testing")
    @Expose
    private Boolean deviceTesting;
    @SerializedName("focus_mode")
    @Expose
    private Boolean focusMode;
    @SerializedName("private_meeting")
    @Expose
    private Boolean privateMeeting;
    @SerializedName("email_notification")
    @Expose
    private Boolean emailNotification;
    @SerializedName("host_save_video_order")
    @Expose
    private Boolean hostSaveVideoOrder;

    public Boolean getHostVideo() {
        return hostVideo;
    }

    public void setHostVideo(Boolean hostVideo) {
        this.hostVideo = hostVideo;
    }

    public Boolean getParticipantVideo() {
        return participantVideo;
    }

    public void setParticipantVideo(Boolean participantVideo) {
        this.participantVideo = participantVideo;
    }

    public Boolean getCnMeeting() {
        return cnMeeting;
    }

    public void setCnMeeting(Boolean cnMeeting) {
        this.cnMeeting = cnMeeting;
    }

    public Boolean getInMeeting() {
        return inMeeting;
    }

    public void setInMeeting(Boolean inMeeting) {
        this.inMeeting = inMeeting;
    }

    public Boolean getJoinBeforeHost() {
        return joinBeforeHost;
    }

    public void setJoinBeforeHost(Boolean joinBeforeHost) {
        this.joinBeforeHost = joinBeforeHost;
    }

    public Integer getJbhTime() {
        return jbhTime;
    }

    public void setJbhTime(Integer jbhTime) {
        this.jbhTime = jbhTime;
    }

    public Boolean getMuteUponEntry() {
        return muteUponEntry;
    }

    public void setMuteUponEntry(Boolean muteUponEntry) {
        this.muteUponEntry = muteUponEntry;
    }

    public Boolean getWatermark() {
        return watermark;
    }

    public void setWatermark(Boolean watermark) {
        this.watermark = watermark;
    }

    public Boolean getUsePmi() {
        return usePmi;
    }

    public void setUsePmi(Boolean usePmi) {
        this.usePmi = usePmi;
    }

    public Integer getApprovalType() {
        return approvalType;
    }

    public void setApprovalType(Integer approvalType) {
        this.approvalType = approvalType;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getAutoRecording() {
        return autoRecording;
    }

    public void setAutoRecording(String autoRecording) {
        this.autoRecording = autoRecording;
    }

    public Boolean getEnforceLogin() {
        return enforceLogin;
    }

    public void setEnforceLogin(Boolean enforceLogin) {
        this.enforceLogin = enforceLogin;
    }

    public String getEnforceLoginDomains() {
        return enforceLoginDomains;
    }

    public void setEnforceLoginDomains(String enforceLoginDomains) {
        this.enforceLoginDomains = enforceLoginDomains;
    }

    public String getAlternativeHosts() {
        return alternativeHosts;
    }

    public void setAlternativeHosts(String alternativeHosts) {
        this.alternativeHosts = alternativeHosts;
    }

    public Boolean getAlternativeHostUpdatePolls() {
        return alternativeHostUpdatePolls;
    }

    public void setAlternativeHostUpdatePolls(Boolean alternativeHostUpdatePolls) {
        this.alternativeHostUpdatePolls = alternativeHostUpdatePolls;
    }

    public Boolean getCloseRegistration() {
        return closeRegistration;
    }

    public void setCloseRegistration(Boolean closeRegistration) {
        this.closeRegistration = closeRegistration;
    }

    public Boolean getShowShareButton() {
        return showShareButton;
    }

    public void setShowShareButton(Boolean showShareButton) {
        this.showShareButton = showShareButton;
    }

    public Boolean getAllowMultipleDevices() {
        return allowMultipleDevices;
    }

    public void setAllowMultipleDevices(Boolean allowMultipleDevices) {
        this.allowMultipleDevices = allowMultipleDevices;
    }

    public Boolean getRegistrantsConfirmationEmail() {
        return registrantsConfirmationEmail;
    }

    public void setRegistrantsConfirmationEmail(Boolean registrantsConfirmationEmail) {
        this.registrantsConfirmationEmail = registrantsConfirmationEmail;
    }

    public Boolean getWaitingRoom() {
        return waitingRoom;
    }

    public void setWaitingRoom(Boolean waitingRoom) {
        this.waitingRoom = waitingRoom;
    }

    public Boolean getRequestPermissionToUnmuteParticipants() {
        return requestPermissionToUnmuteParticipants;
    }

    public void setRequestPermissionToUnmuteParticipants(Boolean requestPermissionToUnmuteParticipants) {
        this.requestPermissionToUnmuteParticipants = requestPermissionToUnmuteParticipants;
    }

    public Boolean getRegistrantsEmailNotification() {
        return registrantsEmailNotification;
    }

    public void setRegistrantsEmailNotification(Boolean registrantsEmailNotification) {
        this.registrantsEmailNotification = registrantsEmailNotification;
    }

    public Boolean getMeetingAuthentication() {
        return meetingAuthentication;
    }

    public void setMeetingAuthentication(Boolean meetingAuthentication) {
        this.meetingAuthentication = meetingAuthentication;
    }

    public String getEncryptionType() {
        return encryptionType;
    }

    public void setEncryptionType(String encryptionType) {
        this.encryptionType = encryptionType;
    }

    public ApprovedOrDeniedCountriesOrRegions getApprovedOrDeniedCountriesOrRegions() {
        return approvedOrDeniedCountriesOrRegions;
    }

    public void setApprovedOrDeniedCountriesOrRegions(ApprovedOrDeniedCountriesOrRegions approvedOrDeniedCountriesOrRegions) {
        this.approvedOrDeniedCountriesOrRegions = approvedOrDeniedCountriesOrRegions;
    }

    public BreakoutRoom getBreakoutRoom() {
        return breakoutRoom;
    }

    public void setBreakoutRoom(BreakoutRoom breakoutRoom) {
        this.breakoutRoom = breakoutRoom;
    }

    public Boolean getAlternativeHostsEmailNotification() {
        return alternativeHostsEmailNotification;
    }

    public void setAlternativeHostsEmailNotification(Boolean alternativeHostsEmailNotification) {
        this.alternativeHostsEmailNotification = alternativeHostsEmailNotification;
    }

    public Boolean getDeviceTesting() {
        return deviceTesting;
    }

    public void setDeviceTesting(Boolean deviceTesting) {
        this.deviceTesting = deviceTesting;
    }

    public Boolean getFocusMode() {
        return focusMode;
    }

    public void setFocusMode(Boolean focusMode) {
        this.focusMode = focusMode;
    }

    public Boolean getPrivateMeeting() {
        return privateMeeting;
    }

    public void setPrivateMeeting(Boolean privateMeeting) {
        this.privateMeeting = privateMeeting;
    }

    public Boolean getEmailNotification() {
        return emailNotification;
    }

    public void setEmailNotification(Boolean emailNotification) {
        this.emailNotification = emailNotification;
    }

    public Boolean getHostSaveVideoOrder() {
        return hostSaveVideoOrder;
    }

    public void setHostSaveVideoOrder(Boolean hostSaveVideoOrder) {
        this.hostSaveVideoOrder = hostSaveVideoOrder;
    }

}
