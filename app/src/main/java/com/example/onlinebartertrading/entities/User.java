package com.example.onlinebartertrading.entities;

import com.example.onlinebartertrading.lib.LocationProvider;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.messaging.FirebaseMessaging;

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
    private double[] cacheLocation;

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
        for (String category :getPreferences().getCategories()) {
            FirebaseMessaging.getInstance().unsubscribeFromTopic(category);
        }

        this.preferences = preferences;

        for (String category : getPreferences().getCategories()) {
            FirebaseMessaging.getInstance().subscribeToTopic(category);
        }
    }

    /**
     * Grabs a location update from LocationProvider
     */
    private void updateLastLocation() {
        if (lastLocation != null && lastLocation[0] != 0) cacheLocation = lastLocation;
        this.lastLocation = this.locationProvider.getLocationUpdate();
    }

    /**
     * Gets the user's last known location
     * @return LatLng with user's latitude and longitude
     * May output 0.0 for latitude and longitude if location fetch failed
     */
    public LatLng getLocation() {
        updateLastLocation();
        if (lastLocation[0] != 0 || cacheLocation == null) return new LatLng(lastLocation[0], lastLocation[1]);
        return new LatLng(cacheLocation[0], cacheLocation[1]);
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
