package com.example.onlinebartertrading;

import static androidx.core.app.ActivityCompat.requestPermissions;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

/**
 * Represents a user location provider
 */
public class LocationProvider {
    private final Context context;
    private final LocationManager locationManager;
    private double[] userLocation = {0.0, 0.0};

    /**
     * TODO: figure out if this is all we need
     */
    public LocationProvider(Context context) {
        this.context = context;
        this.locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        getLocationPermissions();
        updateLocation();
    }

    public LocationProvider(Context context, double[] lastLocation) {
        this.userLocation = lastLocation;
        this.context = context;
        this.locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        getLocationPermissions();
        updateLocation();
    }

    /**
     * Requests a single location update.
     * @return Latitude in Double[0], Longitude in Double[1], null if unavailable
     */
    @SuppressLint("MissingPermission") // we do it ourselves
    public void updateLocation() {
        boolean networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        Criteria locationMode = new Criteria();

        // Establish location accuracy mode
        if (getAccuracyLevel() == 2 && gpsEnabled) {
            locationMode.setAccuracy(Criteria.ACCURACY_FINE);
        } else if (getAccuracyLevel() > 0 && networkEnabled) {
            locationMode.setAccuracy(Criteria.ACCURACY_COARSE);
        } else return;

        // Get location update and store latitude and longitude in results
        locationManager.requestSingleUpdate(locationMode, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                System.out.println("Location requested/n" + location.getLatitude() + "; " + location.getLongitude());
                userLocation[0] = location.getLatitude();
                userLocation[1] = location.getLongitude();
            }

            @Override public void onStatusChanged(String provider, int status, Bundle extras) { }
            @Override public void onProviderEnabled(String provider) { }
            @Override public void onProviderDisabled(String provider) { }
        }, null);
    }

    public double[] getLocationUpdate() {
        updateLocation();
        return userLocation;
    }

    /**
     * Best guess approach to getting coordinates from an address string
     * Uses Google Play Services
     * @return Latitude in Double[0], Longitude in Double[1], null if unavailable
     *
     * adapted from: https://stackoverflow.com/a/27834110 TODO: cite this
     */
    public double[] getLocationFromAddress(String strAddress) {
        // This requires us to have INTERNET and COARSE LOCATION permissions
        if (!getPermission(Manifest.permission.INTERNET) ||
                getAccuracyLevel() == 0) return null;

        double[] coordinates = new double[2];
        Geocoder coder = new Geocoder(context);
        List<Address> address;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) return null;

            Address location = address.get(0);
            coordinates[0] = location.getLatitude();
            coordinates[1] = location.getLongitude();
        } catch (IOException ex) { ex.printStackTrace(); }

        return coordinates;
    }

    /**
     * Requests runtime location permissions,
     * only if none have been granted so far.
     * @return true if at least coarse location permission has been granted
     */
    private boolean getLocationPermissions() {
        if (getAccuracyLevel() > 0) return true;

        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION};

        requestPermissions((Activity) context, permissions, 100);
        return getAccuracyLevel() >= 1;
    }

    /**
     * Requests background location permissions if not granted
     * @return true if granted
     */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    private boolean getBackgroundPermissions() {
        return getPermission(Manifest.permission.ACCESS_BACKGROUND_LOCATION);
    }

    /**
     * Requests a permission if not granted
     * @return true if granted
     */
    private boolean getPermission(String permission) {
        int bgLocation = context.checkCallingOrSelfPermission(permission);
        if (bgLocation == PackageManager.PERMISSION_GRANTED) return true;

        String[] permissions = {permission};

        requestPermissions((Activity) context, permissions,100);

        return context.checkCallingOrSelfPermission(permission)
                == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Checks the maximum allowed location precision
     * @return 2 for FINE, 1 for COARSE, 0 for NONE
     */
    public int getAccuracyLevel() {
        int fineLocation = context.checkCallingOrSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
        int coarseLocation = context.checkCallingOrSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION);

        if (fineLocation == PackageManager.PERMISSION_GRANTED) return 2;
        if (coarseLocation == PackageManager.PERMISSION_GRANTED) return 1;
        return 0;
    }

}