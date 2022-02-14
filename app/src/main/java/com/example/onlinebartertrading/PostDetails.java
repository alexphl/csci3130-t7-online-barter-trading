package com.example.onlinebartertrading;

/**
 * Class to represent post details
 */
public class PostDetails {
    private String title;
    private String desc;
    float value;

    /**
     * @param givenTitle    user provided title
     * @param givenDesc     user provided description
     * @param givenValue    user provided valuation of product
     */
    public PostDetails (String givenTitle, String givenDesc, float givenValue) {
        title = givenTitle;
        desc = givenDesc;
        value = givenValue;
    }

    /**
     * Value Getters
     */
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
