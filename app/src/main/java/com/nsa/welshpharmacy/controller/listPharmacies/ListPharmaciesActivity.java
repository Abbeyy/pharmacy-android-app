package com.nsa.welshpharmacy.controller.listPharmacies;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.nsa.welshpharmacy.R;

/**
 * Created by c1714546 on 3/14/2018.
 *
 * This class is the Activity behind User Story 7 on
 * our Taiga Sprint 1 Taskboard. The aim of this
 * activity is to offer a GUI to the user, on
 * which a list is offered to the user of
 * Pharmacies offering services through the
 * medium of the Welsh language.
 *
 * @author Abbey Ross.
 * @version 1.0 March 14th, 2018.
 */

public class ListPharmaciesActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private SharedPreferences pharmacyInstancesData;
//    private SharedPreferences latLongs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_pharmacies_activity_layout);
        //activity should be inflating list_pharmacies_activity_layout - mostly empty layout
        //due to use of fragments - but that doesnt work? This does?.... fix!

//        sharedPreferences = getSharedPreferences("pharmacyPos", Context.MODE_PRIVATE);
//        latLongs = getSharedPreferences("latitudeLongitudes", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editLatLong = latLongs.edit();
//        editLatLong.clear();
//        editLatLong.apply();

        /**
         * Get intent data from user activity and then wrapping it into a bundle to pass to the fragment
         */
        Bundle data = getIntent().getExtras();
        Boolean ailment = null;
        Boolean flu = null;
        Boolean health = null;
        Boolean smoking = null;
        Boolean alcohol = null;
        if (data != null) {
            ailment = data.getBoolean("checkAilments");
            flu = data.getBoolean("checkFlu");
            health = data.getBoolean("checkHealth");
            smoking = data.getBoolean("checkSmoking");
            alcohol = data.getBoolean("checkAlcohol");
        }

        Bundle bundle = new Bundle();
        bundle.putBoolean("booleanAilments", ailment);
        bundle.putBoolean("booleanFlu", flu);
        bundle.putBoolean("booleanHealth", health);
        bundle.putBoolean("booleanSmoking", smoking);
        bundle.putBoolean("booleanAlcohol", alcohol);
        ListPharmaciesFragment fragment = new ListPharmaciesFragment();
        fragment.setArguments(bundle);

        generatePharmaciesData();

        //setting up main fragment of view
        android.support.v4.app.FragmentManager fmtManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fmtTransaction = fmtManager.beginTransaction();
        fmtTransaction.add(R.id.fragments_container, new com.nsa.welshpharmacy.controller.listPharmacies.ListPharmaciesFragment());
        fmtTransaction.replace(R.id.fragments_container, fragment); //https://stackoverflow.com/a/21102881
        fmtTransaction.commit();
    }

    public void generatePharmaciesData() {
        pharmacyInstancesData = getSharedPreferences("pharmacies", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = pharmacyInstancesData.edit();
    }
}
