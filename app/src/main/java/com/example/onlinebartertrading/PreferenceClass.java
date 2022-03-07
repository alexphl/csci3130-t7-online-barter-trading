package com.example.onlinebartertrading;

import java.util.ArrayList;
import java.util.List;

public class PreferenceClass {

    private List<Integer> tags;
    private int minValue;
    private int maxValue;
    private int distance;

    public PreferenceClass(List<Integer> tags, int minValue, int maxValue, int distance){
        this.tags = tags;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.distance = distance;
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
}
