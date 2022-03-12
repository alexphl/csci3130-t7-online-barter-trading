package com.example.onlinebartertrading;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

public class User implements Serializable {

    private String uuid;
    private String email;
    private String firstName;
    private String lastName;
    private LatLng lastLocation;
    private Preferences preferences;

    public User(String email, LatLng lastLocation) {
        this.email = email;
        this.lastLocation = lastLocation;
    }

    public User(String email, LatLng lastLocation, Preferences preferences) {

    }

    public void setPreferences(Preferences preferences) {this.preferences = preferences;}

}
