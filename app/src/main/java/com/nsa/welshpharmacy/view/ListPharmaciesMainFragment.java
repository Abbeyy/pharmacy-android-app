package com.nsa.welshpharmacy.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.ListViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.nsa.welshpharmacy.mockingdata.Pharmacy;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import com.nsa.welshpharmacy.R;

/**
 * Created by c1714546 on 3/18/2018.
 */

public class ListPharmaciesMainFragment extends Fragment implements AdapterView.OnItemClickListener {
    ListViewCompat lView;
    List<String> aList;
    //Built-in adapter for string datasource
    ArrayAdapter<String> arrayAdpt;
    private Vector<Pharmacy> pharmaciesContainer = new Vector<Pharmacy>();
    private String[] pharmacyNames = new String[]
            {"Boots", "Well", "Cardiff Royal Infirmary Pharmacy",
                    "Clifton Pharmacy", "Pearn's Pharmacies Ltd",
                    "Superdrug Pharmacy", "Woodville Road Pharmacy",
                    "Lloyds Pharmacy Ltd", "Central Pharmacy",
                    "Crwys Pharmacy", "The Co-operative Pharmacy",
                    "Rees & Moore Pharmacy", "M W Philips",
                    "MW Phillips Chemists"};


    public ListPharmaciesMainFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.list_pharmacies_fragment_one_layout, container, false);
        //inflating layout list_pharmacies_fragment_one_layout as layout for my fragment, holding both
        //textviews and the listview!


        this.lView = v.findViewById(R.id.listview_pharmacies); //line 64

        //Created mock datasource of an arraylist of strings;
        this.aList = new ArrayList<>();
        generatePharmacies(pharmacyNames.length);

        this.arrayAdpt = new ArrayAdapter<String>(
                getActivity(), //line 72
                android.R.layout.simple_list_item_1,
                //switch layout to be lv_pharmacy_names once sorted access!
                this.aList
        );

        this.lView.setAdapter(this.arrayAdpt);
        this.lView.setOnItemClickListener(this);
        //line 80

        //date stuff
        setUpDate(v);


        return v;
    }

    public void setUpDate(View v) {
        //Code help gathered from:
        // https://stackoverflow.com/questions/40310773/android-studio-textview-show-date
        TextView dateTV = (TextView)v.findViewById(R.id.date_text_view);

        Date todaysDate = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        String date_today = format.format(todaysDate);

        dateTV.setText("  Today's Date: " + date_today);
    }

    public void generatePharmacies(int numOfPharmacies) {
        for (int k = 0; k < numOfPharmacies; k++) {
            Pharmacy pharmacy = new Pharmacy(pharmacyNames[k],
                    "02920664506","example@live.com",
                    "Capital Shopping Centre, Cardiff",
                    "Common Ailments Service", "Out of Hours Service",
                    "Provides EC", "Seasonal Flu Vaccine");

            pharmaciesContainer.add(pharmacy);
            this.aList.add(pharmaciesContainer.get(k).getName());
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //1. Toast
        Toast.makeText(getActivity(),
                String.format("User has selected %s", lView.getItemAtPosition(position)),
                Toast.LENGTH_SHORT).show();
        //2. Expand Textview (Method > implement
        // another listview of the 1 pharmacy item,
        // with multiple textviews)
       // expandPharmacyInfo(position);
    }

//    public void expandPharmacyInfo(int position) {
//        //Method - move to a fragment to display
//        //a new listview of 4 textviews
//        //to output all the pharmacy's info?
//
//    }

}