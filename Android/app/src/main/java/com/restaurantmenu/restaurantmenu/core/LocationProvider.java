package com.restaurantmenu.restaurantmenu.core;

import android.app.Application;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;

import com.google.inject.Inject;
import com.restaurantmenu.restaurantmenu.activities.MainActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by dszcz_000 on 26.08.2015.
 */
public class LocationProvider implements IService{


    public interface LocationProviderListener {
        void locationRetreived(String locationName);
    }
    @Inject private Logger logger;
    @Inject private MainApplication mainApplication;

    private Handler handler = new Handler();

    private LocationManager locationManager;
    private LocationListener locationListener;

    private String locationName;
    private boolean started;

    private List<LocationProviderListener> listeners = new LinkedList<>();

    public void addListener(LocationProviderListener listener){
        listeners.add(listener);
    }

    public void removeListener(LocationProviderListener listener){
        listeners.remove(listener);
    }

    public boolean isLocationName() {
        return locationName != null && !locationName.equals("");
    }

    public void start(){
        if (started){
            throw new IllegalStateException("Location provider already started.");
        }
        if (logger.isLoggable(Level.FINE)){
            logger.log(Level.FINE, "Location provider started.");
        }
        locationManager = (LocationManager) mainApplication.getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                if (locationName == null){
                    locationName = getLocationName(mainApplication.getApplicationContext(), location.getLatitude(), location.getLongitude());

                    for (LocationProviderListener listener : listeners){
                        listener.locationRetreived(locationName);
                    }
                }
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}
        };
        handler.post(new Runnable() {
            @Override
            public void run() {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 0, locationListener);
            }
        });
        started = true;
    }

    public void stop(){
        if (logger.isLoggable(Level.FINE)){
            logger.log(Level.FINE, "Location provider stopped.");
        }
        locationManager.removeUpdates(locationListener);
        started = false;
    }

    public String getLocationName(){
        return locationName;
    }

    private String getLocationName(Context context, double lattitude, double longitude) {
        String cityName = "Not Found";
        Geocoder gcd = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = gcd.getFromLocation(lattitude, longitude, 10);
            for (Address adrs : addresses) {
                if (adrs != null) {
                    String city = adrs.getLocality();
                    if (city != null && !city.equals("")) {
                        cityName = city;
                        return cityName;
                    }
                }

            }
        } catch (IOException e) {
            if (logger.isLoggable(Level.FINE)){
                logger.log(Level.FINE, "Location provider, getLocationName failed", e);
            }
        }
        return cityName;
    }

    @Override
    public void execute(IAction action) {
        if (action instanceof StartAction){
            start();
        }
        else if (action instanceof StopAction){
            stop();
        }
    }
}
