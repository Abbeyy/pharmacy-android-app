package com.nsa.welshpharmacy.view.listpharmacies;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.ListViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.google.gson.Gson;
import com.nsa.welshpharmacy.R;
import com.nsa.welshpharmacy.model.MockPharmacy;
import com.nsa.welshpharmacy.model.Pharmacy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by c1714546 on 4/2/2018.
 */

public class ListPharmacysDetailsFragment extends Fragment {
    private int position;
    ListViewCompat lView;
    List<String> aList;
    //Built-in adapter for string datasource
    ArrayAdapter<String> arrayAdpt;

    public ListPharmacysDetailsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.list_pharmacys_details_fragment_two_layout, container, false);

        SharedPreferences sharedPrefs = this.getActivity().getSharedPreferences("pharmacyPos", Context.MODE_PRIVATE);
        int pharmacyPosition = sharedPrefs.getInt("position", -1);
        //Reminder, 1st listed item will have a position of 0.

        SharedPreferences pharmacies = this.getActivity().getSharedPreferences("pharmacies", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = pharmacies.getString("pharmacy" + position, "Error");
        MockPharmacy pharmacyToDisplay = gson.fromJson(json, MockPharmacy.class);

        Log.i("Pharmacy name: ", pharmacyToDisplay.getName());
        Log.i("Pharmacy address: ", pharmacyToDisplay.getAddress());
        Log.i("Pharmacy phone: ", pharmacyToDisplay.getPhoneNumber());
        Log.i("Pharmacy email: ", pharmacyToDisplay.getEmail());


//        this.lView = v.findViewById(R.id.listview_pharmacys_details);
//        this.aList = new ArrayList<>();
//        populateMockedData();
//
//        this.arrayAdpt = new ArrayAdapter<String>(
//                getActivity(), //line 72
//                R.layout.lv_pharmacy_details,
//                this.aList
//        );
//
//        this.lView.setAdapter(this.arrayAdpt);

        return v;
    }

    public void populateMockedData() {
        for (int f = 0; f<4;f++) {
            this.aList.add(ListPharmaciesFragment.pharmaciesContainer.get(position).getName());
            this.aList.add(ListPharmaciesFragment.pharmaciesContainer.get(position).getAddress());
            this.aList.add(ListPharmaciesFragment.pharmaciesContainer.get(position).getPhoneNumber());
            this.aList.add(ListPharmaciesFragment.pharmaciesContainer.get(position).getEmail());
        }
    }

}
