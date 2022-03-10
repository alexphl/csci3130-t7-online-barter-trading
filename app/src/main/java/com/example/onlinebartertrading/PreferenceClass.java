package com.example.onlinebartertrading;

import java.util.List;

public class PreferenceClass {

    private List<Integer> tags;
    private int minValue;
    private int maxValue;
    private int distance;
    private String localArea;

    public PreferenceClass() {
        //Needed
    }

    public PreferenceClass(List<Integer> tags, int minValue, int maxValue, int distance, String localArea){
        this.tags = tags;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.distance = distance;
        this.localArea = localArea;
    }

    public List<Integer> getTags() {
        return tags;
    }

    public int getMinValue() {
        return minValue;
    }

    public String getLocalArea(){ return localArea;}

    public int getMaxValue() {
        return maxValue;
    }

    public int getDistance() {
        return distance;
    }

}
