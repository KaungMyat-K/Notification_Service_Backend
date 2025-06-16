package com.mbc.model;

import java.util.Date;


public class NotificationMessage {
	private String projectId;
    private String clientId;   
    private String title;
    private String text;
    private String imageUrl;
    private String notificationName;
    private String exchangeName;
    private String senderName;
    private Date timestamp;
    private String device;
    
    private NotificationMessage(Builder builder) {
        this.projectId = builder.projectId;
        this.clientId = builder.clientId;
        this.title = builder.title;
        this.text = builder.text;
        this.imageUrl = builder.imageUrl;
        this.senderName = builder.senderName;
        this.timestamp = builder.timestamp;
        this.exchangeName = builder.exchangeName;
        this.notificationName = builder.notificationName;
        this.device = builder.device; 
    }

 
    public String getProjectId() { return projectId; }
    public String getClientId() { return clientId; }
    public String getTitle() { return title; }
    public String getText() { return text; }
    public String getImageUrl() { return imageUrl; }
    public String getSenderName() { return senderName; }
    public Date getTimestamp() { return timestamp; }
    public String getExchangeName() { return exchangeName; }
    public String getNotificationName() { return notificationName; }
    public String getDevice() { return device; }  


    public static class Builder {
        private String projectId;
        private String clientId;
        private String title;
        private String text;
        private String imageUrl;
        private String senderName;
        private Date timestamp;
        private String notificationName;
        private String exchangeName;
        private String device; 

        public Builder projectId(String projectId) {
            this.projectId = projectId;
            return this;
        }

        public Builder clientId(String clientId) {
            this.clientId = clientId;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder text(String text) {
            this.text = text;
            return this;
        }

        public Builder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Builder senderName(String senderName) {
            this.senderName = senderName;
            return this;
        }

        public Builder timestamp(Date timestamp) {
            this.timestamp = timestamp;
            return this;
        }
        
        public Builder exchangeName(String exchangeName) {
            this.exchangeName = exchangeName;
            return this;
        }
        
        public Builder notificationName(String notificationName) {
            this.notificationName = notificationName;
            return this;
        }

        public Builder device(String device) {
            this.device = device;
            return this;
        }

        public NotificationMessage build() {
            return new NotificationMessage(this);
        }
    }
}
