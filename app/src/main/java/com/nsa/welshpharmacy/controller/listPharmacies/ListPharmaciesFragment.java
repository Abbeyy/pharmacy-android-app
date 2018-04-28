package com.nsa.welshpharmacy.controller.listPharmacies;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.ListViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.nsa.welshpharmacy.R;
import com.nsa.welshpharmacy.model.Pharmacy;
import com.nsa.welshpharmacy.services.FirebaseServices;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by c1714546 on 3/18/2018.
 */

public class ListPharmaciesFragment extends Fragment implements AdapterView.OnItemClickListener, ValueEventListener {

    ListViewCompat lv;
    List<Pharmacy> pharmacies;
    List<String> listOfNames;
    private ArrayAdapter<String> la;

    private FragmentManager fmtManager;
    private FragmentTransaction fmtTrans;
    private SharedPreferences currentLang;
    private String currentLocale;


    public ListPharmaciesFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.list_pharmacies_fragment_one_layout, container, false);
        //inflating layout list_pharmacies_fragment_one_layout as layout for my fragment, holding both
        //textviews and the listview!

        currentLang = getActivity().getSharedPreferences("currentLanguage", Context.MODE_PRIVATE);
        currentLocale = currentLang.getString("state", "default");

        lv = v.findViewById(R.id.listview_pharmacies); //line 64

        pharmacies = new ArrayList<>();
        listOfNames = new ArrayList<>();

        FirebaseServices.loadPharmacies(this);

        la = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                listOfNames);

        lv.setAdapter(la);
        lv.setOnItemClickListener(this);

        //date stuff
        setUpDate(v);
        return v;
    }

    public void setUpDate(View v) {
        //Code help gathered from:
        // https://stackoverflow.com/questions/40310773/android-studio-textview-show-date
        TextView dateTV = (TextView)v.findViewById(R.id.date_text_view);

        Date currentDate = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        String date_today = format.format(currentDate);

        if (currentLocale == "cy") {
            dateTV.setText(" Dyddiad heddiw: " + date_today);
        } else {
            dateTV.setText("  Today's Date: " + date_today);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //1. Toast
        if (currentLocale == "cy") {
            Toast.makeText(getActivity(),
                    String.format("Defnyddiwr wedi dewis %s", lv.getItemAtPosition(position)),
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(),
                    String.format("User has selected %s", lv.getItemAtPosition(position)),
                    Toast.LENGTH_SHORT).show();
        }

        //2. Switch Fragments
        expandPharmacyInfo(position);
    }

    public void expandPharmacyInfo(int position) {
        //First updateSharedPrefs to store position data in activity.
        /*
        SharedPreferences sharedPrefs = this.getActivity().getSharedPreferences("pharmacyPos", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPrefs.edit();
        edit.putInt("position", position);
        edit.apply();
        */
        // https://stackoverflow.com/a/46298244
        Bundle bundle = new Bundle();
        bundle.putParcelable("selectedPharmacy", pharmacies.get(position));
        //getParentFragment().setArguments(bundle);
        ListPharmacyDetailsFragment listPharmacyDetailsFragment = new ListPharmacyDetailsFragment();
        listPharmacyDetailsFragment.setArguments(bundle);
        //Then switch fragments.
        fmtManager = getActivity().getSupportFragmentManager();
        fmtTrans = fmtManager.beginTransaction();
        fmtTrans.replace(R.id.fragments_container, listPharmacyDetailsFragment).addToBackStack("fragTwo");
        fmtTrans.addToBackStack(null);
        fmtTrans.commit();
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot){
        // see:  https://firebase.google.com/docs/database/android/lists-of-data#listen_for_value_events
        // Retrieve all  Pharmacy records from the Firebase database in one go
        for(DataSnapshot pharmacySnapshot : dataSnapshot.getChildren()){
            Pharmacy pharmacy = pharmacySnapshot.getValue(Pharmacy.class);
            pharmacy.setId(pharmacySnapshot.getKey());
            System.out.println("pharmacy : " + pharmacy);
            pharmacies.add(pharmacy);
            listOfNames.add(pharmacy.getName());
        }
        la.notifyDataSetChanged();
    }

    @Override
    public void onCancelled(DatabaseError databaseError){
        System.out.println("The read failed: " + databaseError.getCode());
    }
}
