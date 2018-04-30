package com.nsa.welshpharmacy.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nsa.welshpharmacy.R;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PharmacyMapActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private GoogleMap mMap;
    private SharedPreferences latLongs;
    private String pharmacyLatitudeLongitude, userLatitudeLongitude;
    private double latitude;
    private double longitude;
    private SharedPreferences currentLang;
    private String currentLocale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy_map);

        currentLang = getSharedPreferences("currentLanguage", Context.MODE_PRIVATE);
        currentLocale = currentLang.getString("state", "error");

        latLongs = getSharedPreferences("latitudeLongitudes", Context.MODE_PRIVATE);
        pharmacyLatitudeLongitude = latLongs.getString("pharmLatLong", "Error");
        userLatitudeLongitude = latLongs.getString("userLatLong", "Error");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (pharmacyLatitudeLongitude != "Error") {
            String pharmLatLong = pharmacyLatitudeLongitude;
            getLatitudeAndLongitude(pharmLatLong);
            LatLng pharmacyClicked = new LatLng(this.latitude, this.longitude);

            if (currentLocale == "cy") {
                mMap.addMarker(new MarkerOptions()
                        .position(pharmacyClicked)
                        .title("Fferyllfa")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            } else {
                mMap.addMarker(new MarkerOptions()
                        .position(pharmacyClicked)
                        .title("Pharmacy")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            }
            mMap.moveCamera(CameraUpdateFactory.newLatLng(pharmacyClicked));
        }
        this.latitude = 0;
        this.longitude = 0;
        if (userLatitudeLongitude != "Error") {
            // Users location IS printing, but is
            // currently the same as the Pharmacys.

            String userLatLong = userLatitudeLongitude;
            getLatitudeAndLongitude(userLatLong);
            LatLng userClicked = new LatLng(this.latitude, this.longitude);

            if (currentLocale == "cy") {
                mMap.addMarker(new MarkerOptions()
                        .position(userClicked)
                        .title("Chi")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));
            } else {
                mMap.addMarker(new MarkerOptions()
                        .position(userClicked)
                        .title("You")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));
            }
            mMap.moveCamera(CameraUpdateFactory.newLatLng(userClicked));
        }

        //round to 0dp.
        LatLng cardiffCityCentre = new LatLng(51.479436, -3.174422);
        mMap.addMarker(new MarkerOptions()
                .position(cardiffCityCentre)
                .title("Cardiff City Centre")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(cardiffCityCentre));

        mMap.setOnMarkerClickListener(this);
    }

    public void getLatitudeAndLongitude(String latLang) {
        //Extracting Lat Long numbers from returned string e.g. "lat/long:   (num1, num2)"

        Pattern pattern = Pattern.compile("(([0-9]+)(.{1})([0-9]+)(,{1})+?-?([0-9]+)(.{1})([0-9]+))");
        //finds 1+ digits, a decimal point, 1+ digits, comma, optional +-, 1+ digits, a decimal point, 1+ digits, end of line
        // /[1-9]+.[1-9]+,+?-?[1-9]+.[1-9]+/

        Matcher matcher = pattern.matcher(latLang);
        if (matcher.find()) {
            latLang = matcher.group(0);
        }

        List<String> latLongList = Arrays.asList(latLang.split(","));
        String latitude = latLongList.get(0);
        String longitude = latLongList.get(1);
        this.latitude = Double.parseDouble(latitude);
        this.longitude = Double.parseDouble(longitude);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        String title = marker.getTitle();

        switch (title) {
            case "Chi" :
                Toast.makeText(this, R.string.users_loc_msg, Toast.LENGTH_SHORT).show();
                break;
            case "You" :
                Toast.makeText(this, R.string.users_loc_msg, Toast.LENGTH_SHORT).show();
                break;
            default :
                break;
        }

        return false;
    }
}
