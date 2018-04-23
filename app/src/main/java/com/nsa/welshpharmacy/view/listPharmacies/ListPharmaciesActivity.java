package com.nsa.welshpharmacy.view.listPharmacies;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.nsa.welshpharmacy.R;
import com.nsa.welshpharmacy.model.MockPharmacy;

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
    private SharedPreferences pharmacyInstancesMockedData;
    private String[] pharmacyNames = new String[]
            {"Boots", "Well", "Cardiff Royal Infirmary Pharmacy",
                    "Clifton Pharmacy", "Pearn's Pharmacies Ltd",
                    "Superdrug Pharmacy", "Woodville Road Pharmacy",
                    "Lloyds Pharmacy Ltd", "Central Pharmacy",
                    "Crwys Pharmacy", "The Co-operative Pharmacy",
                    "Rees & Moore Pharmacy", "M W Philips",
                    "MW Phillips Chemists"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_pharmacies_activity_layout);
        //activity should be inflating list_pharmacies_activity_layout - mostly empty layout
        //due to use of fragments - but that doesnt work? This does?.... fix!

        sharedPreferences = getSharedPreferences("pharmacyPos", Context.MODE_PRIVATE);

        generatePharmaciesMockedData(pharmacyNames.length);

        //setting up main fragment of view
        android.support.v4.app.FragmentManager fmtManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fmtTransaction = fmtManager.beginTransaction();
        fmtTransaction.add(R.id.fragments_container, new com.nsa.welshpharmacy.view.listPharmacies.ListPharmaciesFragment());
        fmtTransaction.commit();
    }

    public void generatePharmaciesMockedData(int numOfPharmacies) {
        pharmacyInstancesMockedData = getSharedPreferences("pharmacies", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = pharmacyInstancesMockedData.edit();

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
    }

}
