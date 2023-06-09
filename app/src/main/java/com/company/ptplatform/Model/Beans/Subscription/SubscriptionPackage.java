package com.company.ptplatform.Model.Beans.Subscription;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubscriptionPackage  {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("date")
        @Expose
        private Integer date;
        @SerializedName("permissions")
        @Expose
        private Permissions permissions;
        @SerializedName("style")
        @Expose
        private String style;
        @SerializedName("purchase_apple_id")
        @Expose
        private String purchaseAppleId;
        @SerializedName("purchase_android_id")
        @Expose
        private String purchaseAndroidId;
        @SerializedName("is_free")
        @Expose
        private boolean isFree;
        @SerializedName("user_package")
        @Expose
        private userPackage userPackage;
        @SerializedName("price_object")
        @Expose
        private priceObject priceObject;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPurchaseAndroidId() {
            return purchaseAndroidId;
        }

        public void setPurchaseAndroidId(String purchaseAndroidId) {
            this.purchaseAndroidId = purchaseAndroidId;
        }

        public String getPurchaseAppleId() {
            return purchaseAppleId;
        }

        public void setPurchaseAppleId(String purchaseAppleId) {
            this.purchaseAppleId = purchaseAppleId;
        }

        public String getStyle() {
            return style;
        }

        public void setStyle(String style) {
            this.style = style;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public Integer getDate() {
            return date;
        }

        public void setDate(Integer date) {
            this.date = date;
        }

        public Permissions getPermissions() {
            return permissions;
        }

        public void setPermissions(Permissions permissions) {
            this.permissions = permissions;
        }

        public boolean getIsFree() {
            return isFree;
        }

        public void setIsFree(boolean isFree) {
            this.isFree = isFree;
        }

        public userPackage getUserPackage() {
            return userPackage;
        }

        public void setUserPackage(userPackage userPackage) {
            this.userPackage = userPackage;
        }

        public priceObject getPriceObject() {
            return priceObject;
        }

        public void setPriceObject(priceObject priceObject) {
            this.priceObject = priceObject;
        }

    }