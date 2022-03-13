package com.example.onlinebartertrading;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

public class User implements Serializable {

    private String uuid;
    private String email;
    private String firstName;
    private String lastName;
    private transient LocationProvider locationProvider;
    private Preferences preferences;
    private LatLng lastLocation;

    public User(String email) {
        this.email = email;
    }

    public void setLocationProvider(LocationProvider locationProvider) {
        this.locationProvider = locationProvider;
    }

    public void setLastLocation(LatLng lastLocation) {
        this.lastLocation = lastLocation;
    }

    public void setPreferences(Preferences preferences) {
        this.preferences = preferences;
    }

    private void updateLastLocation(LatLng lastLocation) {
        this.lastLocation = this.locationProvider.getLocationUpdate();
    }

    public LatLng getLocationUpdate() {
        return this.locationProvider.getLocationUpdate();
    }

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
    public Preferences getPreferences() {
        return preferences;
    }
    public LatLng getLastLocation() {return lastLocation;}



}
