package com.nsa.welshpharmacy.controller.listPharmacies;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.nsa.welshpharmacy.R;

/**
 * This class is the Activity behind listing
 * Pharmacies by their names in the application.
 * It first links to ListPharmaciesFragment java
 * resource which inflates it's own layout,
 * and then links to ListPharmacysDetailsFragment.
 *
 * Created by c1714546 on 3/14/2018.
 *
 * @author Abbey Ross.
 * @version 1.0 April 30th, 2018.
 */

public class ListPharmaciesActivity extends AppCompatActivity {
    private SharedPreferences pharmacyInstancesData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_pharmacies_activity_layout);

        /**
         * Get intent data from user activity and then wrapping it into a bundle to pass to the fragment
         */
        Bundle data = getIntent().getExtras();
        Boolean ailment = null;
        Boolean flu = null;
        Boolean health = null;
        Boolean smoking = null;
        Boolean alcohol = null;
        Object location = null;
        if (data != null) {
            ailment = data.getBoolean("checkAilments");
            flu = data.getBoolean("checkFlu");
            health = data.getBoolean("checkHealth");
            smoking = data.getBoolean("checkSmoking");
            alcohol = data.getBoolean("checkAlcohol");
            location = data.get("userLocation");
        }

        /*
        String stringLocation = location.toString();
        String[] locationSplit =  stringLocation.split(",");
        double latitude = Double.parseDouble(locationSplit[0]);
        double longitude = Double.parseDouble(locationSplit[1]);
        */

        Bundle bundle = new Bundle();
        bundle.putBoolean("booleanAilments", ailment);
        bundle.putBoolean("booleanFlu", flu);
        bundle.putBoolean("booleanHealth", health);
        bundle.putBoolean("booleanSmoking", smoking);
        bundle.putBoolean("booleanAlcohol", alcohol);
        //bundle.putString("userLocation", stringLocation);
        //bundle.putDouble("latitude", latitude);
        //bundle.putDouble("longitude", longitude);
        ListPharmaciesFragment fragment = new ListPharmaciesFragment();
        fragment.setArguments(bundle);

        //Setting up the first fragment for this activity.
        android.support.v4.app.FragmentManager fmtManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fmtTransaction = fmtManager.beginTransaction();
        fmtTransaction.add(R.id.fragments_container, new com.nsa.welshpharmacy.controller.listPharmacies.ListPharmaciesFragment());
        fmtTransaction.replace(R.id.fragments_container, fragment); //https://stackoverflow.com/a/21102881
        fmtTransaction.commit();
    }

}
