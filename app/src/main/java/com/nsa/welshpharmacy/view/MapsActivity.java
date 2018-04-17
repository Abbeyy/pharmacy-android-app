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
        mMap.addMarker(new MarkerOptions().position(Boots).title("Marker in Boots"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Boots));
        LatLng Wells = new LatLng(51.470269, -3.186349);
        mMap.addMarker(new MarkerOptions().position(Wells).title("Marker in Wells"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Wells));
        LatLng Clifton = new LatLng(51.486759, -3.158758);
        mMap.addMarker(new MarkerOptions().position(Clifton).title("Marker in Clifton Pharmacy"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Clifton));
        LatLng SuperDrug = new LatLng(51.489220, -3.166650);
        mMap.addMarker(new MarkerOptions().position(SuperDrug).title("Marker in Superdrug"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(SuperDrug));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(9);
        mMap.moveCamera(zoom);
        mMap.animateCamera(zoom);
    }
}
