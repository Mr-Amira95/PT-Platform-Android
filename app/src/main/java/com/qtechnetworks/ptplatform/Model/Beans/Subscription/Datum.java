package com.qtechnetworks.ptplatform.Model.Beans.Subscription;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.security.Permissions;
import java.util.List;


public class Datum {

    @SerializedName("subscription")
    @Expose
    private List<SubscriptionPackage> subscription = null;
    @SerializedName("personal_training")
    @Expose
    private List<SubscriptionPackage> personalTraining = null;

    public List<SubscriptionPackage> getSubscription() {
        return subscription;
    }

    public void setSubscription(List<SubscriptionPackage> subscription) {
        this.subscription = subscription;
    }

    public List<SubscriptionPackage> getPersonalTraining() {
        return personalTraining;
    }

    public void setPersonalTraining(List<SubscriptionPackage> personalTraining) {
        this.personalTraining = personalTraining;
    }
}


