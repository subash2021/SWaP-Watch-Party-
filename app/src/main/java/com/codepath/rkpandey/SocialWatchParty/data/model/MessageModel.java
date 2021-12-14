package com.codepath.rkpandey.SocialWatchParty.data.model;

public class MessageModel {
    String UId, message, messageId;
    Long timestamp;

    public MessageModel(String UId, String message, Long timestamp) {
        this.UId = UId;
        this.message = message;
        this.timestamp = timestamp;
    }

    public MessageModel(String UId, String message) {
        this.UId = UId;
        this.message = message;
    }
    public MessageModel(){}

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getUId() {
        return UId;
    }

    public void setUId(String UId) {
        this.UId = UId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
