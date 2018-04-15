package com.nsa.welshpharmacy.services;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

/**
 * This service allows the location of the user to be retrieved whether they enter a valid postcode
 * or opt to allow their phone network to be used
 *
 * Created by c1712480 on 03/04/2018.
 */

public class LocationServices {
    private static LatLng userLocation = null;

    public static LatLng getUserLocation() {
        return userLocation;
    }

    public static LatLng loadPhoneLocationViaNetwork(Context context){
        // Documentation: https://developer.android.com/guide/topics/location/strategies.html
        // Retrieve the users last known location via their network
        // Acquire a reference to the system Location Manager
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        String locationProvider = LocationManager.NETWORK_PROVIDER;
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        if(locationManager != null){
            Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
            if (lastKnownLocation != null) {
                userLocation = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
                return userLocation;
            }
        }
        return null;
    }

    public static LatLng loadPhoneLocationViaPostcode(Context context, String userPostcode) throws IOException {

        //Adapted from: https://stackoverflow.com/a/4833943 Retrieved: 3/4/18
        //Create a list of type Address using the user inputted postcode and then converting it into LatLng
        final Geocoder geocoder = new Geocoder(context);
        try {
            List<Address> addresses = geocoder.getFromLocationName(userPostcode, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                userLocation = new LatLng(address.getLatitude(), address.getLongitude());
                return userLocation;
            }
        } catch (IOException e) {
            throw e;
        }
        return userLocation;
    }

    public static boolean isNetworkEnabled(Context context){
        //Adapted from: http://hmkcode.com/android-check-enable-location-service/
        LocationManager locationManager = null;
        boolean network_enabled = false;

        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        if(locationManager != null){
            network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        }else{
            //TODO dialog box
            Toast.makeText(context, "Turn on location in settings", Toast.LENGTH_SHORT).show();
        }
        return network_enabled;
    }
}
