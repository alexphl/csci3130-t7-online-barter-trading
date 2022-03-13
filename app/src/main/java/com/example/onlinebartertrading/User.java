package com.example.onlinebartertrading;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

/**
 * Represents an app user
 */
public class User implements Serializable {

    private String uuid;
    private String email;
    private String firstName;
    private String lastName;
    private transient LocationProvider locationProvider;
    private Preferences preferences;
    private double[] lastLocation;

    public User(String email) {
        this.email = email;
    }

    /**
     * Assigns LocationProvider to a user
     * It is currently critical to do this in every activity
     * as LocationProvider is transient and wont be passed along with the intent.
     *
     * TODO: adapt the app for users who deny location permissions
     *
     * @param locationProvider a LocationProvider initialized under activity's context
     */
    public void setLocationProvider(LocationProvider locationProvider) {
        this.locationProvider = locationProvider;
        updateLastLocation();
    }

    /**
     * Assigns Preferences to a user.
     * @param preferences user's post filter preferences
     */
    public void setPreferences(Preferences preferences) {
        this.preferences = preferences;
    }

    /**
     * Grabs a location update from LocationProvider
     */
    private void updateLastLocation() {
        this.lastLocation = this.locationProvider.getLocationUpdate();
    }

    /**
     * Gets the user's last known location
     * @return LatLng with user's latitude and longitude
     * May output 0.0 for latitude and longitude if location fetch failed
     */
    public LatLng getLocation() {
        LatLng cacheLocation = new LatLng(lastLocation[0], lastLocation[1]);
        updateLastLocation();
        if (lastLocation[0] != 0) return new LatLng(lastLocation[0], lastLocation[1]);
        return cacheLocation;
    }

    /**
     * Basic getters
     */
    public String getUuid() {return uuid;}
    public String getEmail() {return email;}
    public String getFirstName() {return firstName;}
    public String getLastName() {return lastName;}
    public Preferences getPreferences() {return preferences;}
}
