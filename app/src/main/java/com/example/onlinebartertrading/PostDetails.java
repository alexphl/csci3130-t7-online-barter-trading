package com.example.onlinebartertrading;

public class PostDetails {
    String title;
    String desc;
    float value;

    public PostDetails(String givenTitle, String givenDesc, float givenValue){
        title = givenTitle;
        desc = givenDesc;
        value = givenValue;
    }

    public float getValue() {
        return value;
    }

    public String getDesc(){
        return desc;
    }

    public String getTitle(){
        return title;
    }
}
