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

    public String getUuid() {
        return uuid;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LatLng getLastLocation() {
        return lastLocation;
    }

    public Preferences getPreferences() {
        return preferences;
    }

    public User(String email) {
        this.email = email;
    }

    public void setLastLocation(LatLng lastLocation) {
        this.lastLocation = lastLocation;
    }

    public void setPreferences(Preferences preferences) {
        this.preferences = preferences;
    }

}
