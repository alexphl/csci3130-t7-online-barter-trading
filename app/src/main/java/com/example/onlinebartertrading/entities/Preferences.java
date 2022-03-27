package com.example.onlinebartertrading.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Preferences implements Serializable {

    private ArrayList<Integer> tags;
    private ArrayList<String> categories;
    private int minValue;
    private int maxValue;
    private int distance;
    private String localArea;

    public Preferences() {
        //Needed
    }

    public Preferences(List<Integer> tags, int minValue, int maxValue, int distance){
        this.tags = new ArrayList<>(tags);
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

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

}
