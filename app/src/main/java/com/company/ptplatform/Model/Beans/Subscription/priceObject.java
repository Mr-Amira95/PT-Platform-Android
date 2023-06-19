package com.company.ptplatform.Model.Beans.Subscription;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class priceObject {

    @SerializedName("amount")
    @Expose
    private double amount;
    @SerializedName("formatted")
    @Expose
    private String formatted;
    @SerializedName("currency")
    @Expose
    private String currency;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getFormatted() {
        return formatted;
    }

    public void setFormatted(String formatted) {
        this.formatted = formatted;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
