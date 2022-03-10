package com.example.onlinebartertrading;

import static androidx.core.app.ActivityCompat.requestPermissions;
import static androidx.test.InstrumentationRegistry.getContext;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

import com.google.android.gms.location.LocationCallback;

public class LocationService extends Service {
    private Activity activity;

    public LocationService(Activity activity) {
        this.activity = activity;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @SuppressLint("MissingPermission")
    public Double[] getLocationUpdate(Context context, LocationCallback callback) {
        Double[] results = new Double[2];

        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        Criteria locationMode = new Criteria();

        if (getPermissionLevel() == 2 && gpsEnabled) {
            locationMode.setAccuracy(Criteria.ACCURACY_FINE);
        }
        else if (getPermissionLevel() > 0 && networkEnabled) {
            locationMode.setAccuracy(Criteria.ACCURACY_COARSE);
        }

        locationManager.requestSingleUpdate(locationMode, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                results[0] = location.getLatitude();
                results[1] = location.getLongitude();
            }

            @Override public void onStatusChanged(String provider, int status, Bundle extras) { }
            @Override public void onProviderEnabled(String provider) { }
            @Override public void onProviderDisabled(String provider) { }
        }, null);

        return results;
    }

    private boolean requestLocationPermissions() {
        int fineLocation = getContext().checkCallingOrSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
        int coarseLocation = getContext().checkCallingOrSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION);

        if (getPermissionLevel() == 0) {
            String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
            requestPermissions(activity, permissions, 100);
        }

        return getPermissionLevel() >= 1;
    }

    public int getPermissionLevel() {
        int fineLocation = getContext().checkCallingOrSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
        int coarseLocation = getContext().checkCallingOrSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION);

        if (fineLocation == PackageManager.PERMISSION_GRANTED) return 2;
        if (coarseLocation == PackageManager.PERMISSION_GRANTED) return 1;
        return 0;
    }
}