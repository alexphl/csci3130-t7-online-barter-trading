package com.example.onlinebartertrading;

public class ChatList {

    private String firstName;
    private String lastName;
    private int chatKey;
    private int userKey;
    private String date;
    private String time;

    public ChatList(String firstName, String lastName, int chatKey, int userKey, String date, String time) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.chatKey = chatKey;
        this.userKey = userKey;
        this.date = date;
        this.time = time;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getChatKey() {
        return chatKey;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public int getUserKey() {
        return userKey;
    }
}
