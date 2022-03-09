package com.example.onlinebartertrading;

import java.util.List;

public class Preference {

    private List<Integer> tags;
    private int minValue;
    private int maxValue;
    private int distance;
    private String localArea;

    public Preference(List<Integer> tags, int minValue, int maxValue, int distance, String localArea){
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

    public int getMaxValue() {
        return maxValue;
    }

    public int getDistance() {
        return distance;
    }

    public String getLocalArea() {
        return localArea;
    }
}
