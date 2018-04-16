package com.nsa.welshpharmacy.view.listpharmacies;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.ListViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nsa.welshpharmacy.R;
import com.nsa.welshpharmacy.model.MockPharmacy;
import com.nsa.welshpharmacy.model.Pharmacy;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;

/**
 * Created by c1714546 on 3/18/2018.
 */

public class ListPharmaciesFragment extends Fragment implements AdapterView.OnItemClickListener {
    ListViewCompat lView;
    List<String> aList;
    //Built-in adapter for string datasource
    ArrayAdapter<String> arrayAdpt;
    public static Vector<MockPharmacy> pharmaciesContainer = new Vector<MockPharmacy>();
    private String[] pharmacyNames = new String[]
            {"Boots", "Well", "Cardiff Royal Infirmary Pharmacy",
                    "Clifton Pharmacy", "Pearn's Pharmacies Ltd",
                    "Superdrug Pharmacy", "Woodville Road Pharmacy",
                    "Lloyds Pharmacy Ltd", "Central Pharmacy",
                    "Crwys Pharmacy", "The Co-operative Pharmacy",
                    "Rees & Moore Pharmacy", "M W Philips",
                    "MW Phillips Chemists"};
    private FragmentManager fmtManager;
    private FragmentTransaction fmtTrans;


    public ListPharmaciesFragment() {
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
                getActivity(),
                android.R.layout.simple_list_item_1,
                this.aList
        );

        this.lView.setAdapter(this.arrayAdpt);
        this.lView.setOnItemClickListener(this);

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
        SharedPreferences pharmsInstances = getActivity().getSharedPreferences("pharmacies", Context.MODE_PRIVATE);
        for (int k = 0; k < numOfPharmacies; k++) {

            Gson gson = new Gson();
            String json = pharmsInstances.getString("pharmacy" + k, "Error");
            MockPharmacy aPharmacy = gson.fromJson(json, MockPharmacy.class);

            pharmaciesContainer.add(aPharmacy);
            this.aList.add(aPharmacy.getName());
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //1. Toast
        Toast.makeText(getActivity(),
                String.format("User has selected %s", lView.getItemAtPosition(position)),
                Toast.LENGTH_SHORT).show();
        //2. Switch Fragments
        expandPharmacyInfo(position);
    }

    public void expandPharmacyInfo(int position) {
        //First updateSharedPrefs to store position data in activity.
        SharedPreferences sharedPrefs = this.getActivity().getSharedPreferences("pharmacyPos", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPrefs.edit();
        edit.putInt("position", position);
        edit.apply();

        //Then switch fragments.
        this.fmtManager = getActivity().getSupportFragmentManager();
        this.fmtTrans = this.fmtManager.beginTransaction();
        this.fmtTrans.replace(R.id.fragments_container, new ListPharmacysDetailsFragment());
        this.fmtTrans.commit();
    }

}
