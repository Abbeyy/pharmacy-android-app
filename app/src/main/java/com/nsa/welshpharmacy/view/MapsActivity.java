package com.nsa.welshpharmacy.view;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nsa.welshpharmacy.R;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
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
        LatLng Boots = new LatLng(51.478004, -3.179581);
        mMap.addMarker(new MarkerOptions().position(Boots).title("Marker in Pharmacy 1"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Boots));
        LatLng Pharmacy2 = new LatLng(51.490875, -3.185637);
        mMap.addMarker(new MarkerOptions().position(Pharmacy2).title("Marker in Pharmacy 2"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Pharmacy2));
        LatLng Pharmacy3 = new LatLng(51.486577, -3.165777);
        mMap.addMarker(new MarkerOptions().position(Pharmacy3).title("Marker in Pharmacy 3"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Pharmacy3));
        LatLng Pharmacy4 = new LatLng(51.489220, -3.166650);
        mMap.addMarker(new MarkerOptions().position(Pharmacy4).title("Marker in Pharmacy 4"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Pharmacy4));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(9);
        mMap.moveCamera(zoom);
        mMap.animateCamera(zoom);
    }
}
