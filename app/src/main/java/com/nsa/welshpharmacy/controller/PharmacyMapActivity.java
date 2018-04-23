package com.nsa.welshpharmacy.controller;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nsa.welshpharmacy.R;

public class PharmacyMapActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;

    //Coding ideas....
    //retrieve pharmacies lats and longs and place into ....
    //pinpoint as markers on map.
    //Use pharmacy.getPharmacyLatLang, to separate out Latitude
    // and Longitude and ensure it is rounded to 0dp, and then
    // pop this into a marker.

    //Ensure mocked data for pharmacy's comes from google maps
    //and we take the numbers beneath NS/, E/W latitude/longitude...

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy_map);

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
        LatLng cardiffCityCentre = new LatLng(-3.179100, 51.481600);

        //round to 0dp.
        mMap.addMarker(new MarkerOptions().position(cardiffCityCentre).title("Cardiff City Centre"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(cardiffCityCentre));
    }
}
