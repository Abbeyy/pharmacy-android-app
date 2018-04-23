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
    /*
    private String[] pharmacyNames = new String[]
            {"Boots", "Well", "Cardiff Royal Infirmary Pharmacy",
                    "Clifton Pharmacy", "Pearn's Pharmacies Ltd",
                    "Superdrug Pharmacy", "Woodville Road Pharmacy",
                    "Lloyds Pharmacy Ltd", "Central Pharmacy",
                    "Crwys Pharmacy", "The Co-operative Pharmacy",
                    "Rees & Moore Pharmacy", "M W Philips",
                    "MW Phillips Chemists"};
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_pharmacies_activity_layout);
        //activity should be inflating list_pharmacies_activity_layout - mostly empty layout
        //due to use of fragments - but that doesnt work? This does?.... fix!

        sharedPreferences = getSharedPreferences("pharmacyPos", Context.MODE_PRIVATE);

        generatePharmaciesData();

        //setting up main fragment of view
        android.support.v4.app.FragmentManager fmtManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fmtTransaction = fmtManager.beginTransaction();
        fmtTransaction.add(R.id.fragments_container, new com.nsa.welshpharmacy.controller.listPharmacies.ListPharmaciesFragment());
        fmtTransaction.commit();
    }

    public void generatePharmaciesData() {
        pharmacyInstancesData = getSharedPreferences("pharmacies", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = pharmacyInstancesData.edit();

        /*
        for (int k = 0; k < numOfPharmacies; k++) {
            MockPharmacy pharmacy = new MockPharmacy(pharmacyNames[k],
                    "02920688695","Rossa9@cardiff.ac.uk",
                    "Capital Shopping Centre, Cardiff",
                    "Common Ailments Service", "Out of Hours Service",
                    "Provides EC", "Seasonal Flu Vaccine");

            Gson gson = new Gson();
            String json = gson.toJson(pharmacy);

            edit.putString("pharmacy"+k, json);
        }
        edit.apply();
        */
    }
}
