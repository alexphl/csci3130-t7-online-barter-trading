package com.example.onlinebartertrading;

public class ChatList {

    private String sender;
    private String receiver;
    private String message;

    public ChatList(String firstName, String lastName, String chatKey, int userKey, String date, String time) {
        this.sender = firstName;
        this.receiver = lastName;
        this.message = chatKey;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.sender = sender;
    }
}
