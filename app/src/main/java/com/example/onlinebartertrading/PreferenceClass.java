package com.example.onlinebartertrading;

import java.util.ArrayList;
import java.util.List;

public class PreferenceClass {
    private List<Integer> tags;
    private int minValue;
    private int maxValue;
    private int distance;
    public PreferenceClass(){
        tags = new ArrayList<>();
        minValue = 0;
        maxValue = MakePostActivity.maxValue;
        distance = PreferenceActivity.MAX_DISTANCE;
    }

    public List<Integer> getTags() {
        return tags;
    }

    public int getDistance() {
        return distance;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public int getMinValue() {
        return minValue;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public void setTags(List<Integer> tags) {
        this.tags = tags;
    }
}
