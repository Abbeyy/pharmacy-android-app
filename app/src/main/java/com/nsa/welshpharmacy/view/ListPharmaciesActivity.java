package com.nsa.welshpharmacy.view;

import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;


import com.nsa.welshpharmacy.R;
import com.nsa.welshpharmacy.model.Pharmacy;
import com.nsa.welshpharmacy.model.PharmacyStore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Vector;

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

public class ListPharmaciesActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListViewCompat lView;
    List<String> aList;
    //Built-in adapter for string datasource
    ArrayAdapter<String> arrayAdpt;
    private Vector<Pharmacy> pharmaciesContainer = new Vector<Pharmacy>();
    private String[] pharmacyNames = new String[]
            {"Boots", "Well", "Cardiff Royal Infirmary Pharmacy",
                    "Clifton Pharmacy", "Superdrug Pharmacy",
                    "Superdrug Pharmacy", "Woodville Road Pharmacy",
                    "Lloyds Pharmacy Ltd", "Central Pharmacy"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_pharmacies_main);
        //activity inflating layout

        //date stuff
        setUpDate();

        this.lView = this.findViewById(R.id.listview_pharmacies);

        //Created mock datasource of an arraylist of strings;
        this.aList = new ArrayList<>();
        generatePharmacies(8);
        collectPharmacyInfo();

        this.arrayAdpt = new ArrayAdapter<String> (
                this,
                android.R.layout.simple_list_item_1,
                //switch layout to be lv_pharmacy_names once sorted access!
                this.aList
        );

        this.lView.setAdapter(this.arrayAdpt);
        this.lView.setOnItemClickListener(this);

        //setting up main fragment of view
//        android.support.v4.app.FragmentManager fmtManager = getSupportFragmentManager();
//        FragmentTransaction fmtTransaction = fmtManager.beginTransaction();
//        fmtTransaction.add(R.id.fragments_container, new ListPharmaciesMainFragment());
//        fmtTransaction.commit();
    }

    public void generatePharmacies(int numOfPharmacies) {
        PharmacyStore.loadPharmacies();
        /*
        for (int k = 0; k < numOfPharmacies; k++) {
            Pharmacy pharmacy = new Pharmacy(pharmacyNames[k],
                    "02920664506","example@live.com",
                    "Capital Shopping Centre, Cardiff",
                    "Common Ailments Service", "Out of Hours Service",
                    "Provides EC", "Seasonal Flu Vaccine");
            pharmaciesContainer.add(pharmacy);
        }
        */
    }

    public void collectPharmacyInfo() {
        //In future, mock this to collect from another
        // datasource. For now, hardcoded generation.

        int numOfPharms = 8;

        for (int i = 0; i < numOfPharms; i++) {
            this.aList.add(pharmaciesContainer.get(i).getName());
        }
    }

    public void setUpDate() {
        //Code help gathered from:
        // https://stackoverflow.com/questions/40310773/android-studio-textview-show-date
        TextView dateTV = (TextView)findViewById(R.id.date_text_view);

        Date todaysDate = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        String date_today = format.format(todaysDate);

        dateTV.setText("  Today's Date: " + date_today);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //1. Toast
        Toast.makeText(getApplicationContext(),
                String.format("User has selected %s", lView.getItemAtPosition(position)),
                Toast.LENGTH_SHORT).show();
        //2. Expand Textview (Method > implement
        // another listview of the 1 pharmacy item,
        // with multiple textviews)
        expandPharmacyInfo(position);
    }

    public void expandPharmacyInfo(int position) {
        //Method - move to a fragment to display
        //a new listview of 4 textviews
        //to output all the pharmacy's info?

    }
}
