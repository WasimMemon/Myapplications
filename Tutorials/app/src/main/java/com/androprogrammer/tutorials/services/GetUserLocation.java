package com.androprogrammer.tutorials.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by Wasim on 21-03-2015.
 */
public class GetUserLocation extends Service implements LocationListener
{
    private Context context;

    private static final String TAG = "GPSGetUserLocation";

    // The maximum time that should pass before the user gets a location update.
    private static final long DEFAULT_DURATION = 1000 * 60 * 1; // 1 min

    // The default search radius when searching for places nearby.
    private static final long DEFAULT_RADIUS = 20; // 20 meters

    private LocationManager mLocationManager = null;

    private double latitude;
    private double longitude;

    // flag for GPS status
    //private boolean isGPSEnabled = false;

    // flag for network status
    //private boolean isNetworkEnabled = false;


    private Location location; // location values

    //private boolean isGpsServiceRunning = false;


    public GetUserLocation(Context context, boolean isGPSEnabled, boolean isNetworkEnabled)
    {
        this.context = context;
        getUserLocation(isGPSEnabled, isNetworkEnabled);
    }


    public GetUserLocation() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");

        return START_STICKY;
    }

    public Location getUserLocation(boolean isGPSEnabled, boolean isNetworkEnabled)
    {
        try {

            /*// getting GPS status
            isGPSEnabled = mLocationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            isNetworkEnabled = mLocationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);*/

            /*if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
            }
            else
            {*/

            if (mLocationManager == null)
            {
                mLocationManager = (LocationManager) context
                        .getSystemService(Context.LOCATION_SERVICE);
            }

                // First get location from Network Provider
                if (isNetworkEnabled)
                {
                    mLocationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            DEFAULT_DURATION,
                            DEFAULT_RADIUS, this);

                    Log.d(TAG, "Network");

                    if (mLocationManager != null) {
                        location = mLocationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            setLatitude(location.getLatitude());
                            setLongitude(location.getLongitude());
                        }
                    }
                }

                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null)
                    {
                        mLocationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                DEFAULT_DURATION,
                                DEFAULT_RADIUS, this);

                        Log.d(TAG, "GPS Enabled");

                        if (mLocationManager != null) {
                            location = mLocationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                setLatitude(location.getLatitude());
                                setLongitude(location.getLongitude());
                            }
                        }
                    }
                }
            //}

        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location)
    {
        if (location != null)
        {
            setLongitude(location.getLongitude());
            setLatitude(location.getLatitude());
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        Log.d(TAG, "" + latitude);
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        Log.d(TAG, "" + longitude);
        this.longitude = longitude;
    }

    @Override
    public void onDestroy()
    {
        Log.e(TAG, "onDestroy");
        super.onDestroy();

        if (mLocationManager != null)
        {
            // Remove update listener when you don't require
            mLocationManager.removeUpdates(this);
        }
    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
