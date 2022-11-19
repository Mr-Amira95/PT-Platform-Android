package com.qtechnetworks.ptplatform.Model.Beans.Chat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {
    @SerializedName("chat_id")
    @Expose
    private Integer chatId;

    public Integer getChatId() {
        return chatId;
    }

    public void setChatId(Integer chatId) {
        this.chatId = chatId;
    }
}
