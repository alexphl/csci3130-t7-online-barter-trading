package com.example.onlinebartertrading.entities;

import com.google.android.gms.maps.model.LatLng;

/**
 * Class to represent post details
 */
public class Post {

    private String posterEmail;
    private String title;
    private String desc;
    private String category;
    private String localArea;
    private double monetaryValue;
    LatLng coordinates;

    /**
     * @param givenTitle    user provided title
     * @param givenDesc     user provided description
     * @param givenValue    user provided valuation of product
     */
    public Post (String email, String givenTitle, String givenDesc, double givenValue, String category, LatLng coordinates) {
        this.posterEmail = email;
        this.title = givenTitle;
        this.desc = givenDesc;
        this.monetaryValue = givenValue;
        this.category = category;
        this.coordinates = coordinates;
    }

    /**
     * Value Getters
     */
    public String getPosterEmail() {
        return posterEmail;
    }
    public String getDesc(){
        return desc;
    }
    public String getTitle(){
        return title;
    }
    public double getLatitude() {
        return coordinates.latitude;
    }
    public String getCategory() {
        return category;
    }
    public double getValue() {
        return monetaryValue;
    }
    public double getLongitude() {
        return coordinates.longitude;
    }
}
