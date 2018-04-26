package com.nsa.welshpharmacy.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nsa.welshpharmacy.R;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PharmacyMapActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private SharedPreferences pharmacyLatLong;
    private String pharmacyLatitudeLongitude;
    private double latitude;
    private double longitude;
    private SharedPreferences currentLang;
    private String currentLocale;

    //Coding ideas....
    //retrieve pharmacies lats and longs and place into ....
    //pinpoint as markers on map.
    //Use pharmacy.getPharmacyLatLang, to separate out Latitude
    // and Longitude and ensure it is rounded to 0dp, and then
    // pop this into a marker.

    //Ensure mocked data for pharmacy's comes from google maps
    //and we take the numbers beneath NS/, E/W latitude/longitude...
    //https://developers.google.com/maps/documentation/android-api/marker
    //https://developers.google.com/maps/documentation/android-api/views

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy_map);

        currentLang = getSharedPreferences("currentLanguage", Context.MODE_PRIVATE);
        currentLocale = currentLang.getString("state", "error");

        pharmacyLatLong = getSharedPreferences("pharmacyLatLang", Context.MODE_PRIVATE);
        pharmacyLatitudeLongitude = pharmacyLatLong.getString("LatitudeLongitude", "Error");

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

        // Add a marker in Sydney and move the camera
        LatLng cardiffCityCentre = new LatLng(51.479436, -3.174422);
        if (pharmacyLatitudeLongitude != "Error") {
            getLatitudeAndLongitude();
            LatLng pharmacyClicked = new LatLng(this.latitude, this.longitude);

            if (currentLocale == "cy") {
                mMap.addMarker(new MarkerOptions().position(pharmacyClicked).title("Fferyllfa"));
            } else {
                mMap.addMarker(new MarkerOptions().position(pharmacyClicked).title("Pharmacy"));
            }
            mMap.moveCamera(CameraUpdateFactory.newLatLng(pharmacyClicked));
        }

        //round to 0dp.
        mMap.addMarker(new MarkerOptions().position(cardiffCityCentre).title("Cardiff City Centre"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(cardiffCityCentre));
    }


    public void getLatitudeAndLongitude() {
        //need to extract numbers from ""lat/lng: (51.5036723,-3.1821333999999997)""
        String latitude = "";
        String longitude = "";
        String both = pharmacyLatitudeLongitude;

        Pattern pattern = Pattern.compile("(([0-9]+)(.{1})([0-9]+)(,{1})+?-?([0-9]+)(.{1})([0-9]+))");
        //finds 1+ digits, a decimal point, 1+ digits, comma, optional +-, 1+ digits, a decimal point, 1+ digits, end of line
        // /[1-9]+.[1-9]+,+?-?[1-9]+.[1-9]+/

        Matcher matcher = pattern.matcher(both);
        if (matcher.find()) {
            Log.i("regex", matcher.group(0) + "!");
            both = matcher.group(0);
        }

        List<String> latLangList = Arrays.asList(both.split(","));
        latitude = latLangList.get(0);
        longitude = latLangList.get(1);
        this.latitude = Double.parseDouble(latitude);
        this.longitude = Double.parseDouble(longitude);
        Log.i("LAT: " + this.latitude, "yay");
        Log.i("LONG: " + this.longitude, "yay");
    }


}
