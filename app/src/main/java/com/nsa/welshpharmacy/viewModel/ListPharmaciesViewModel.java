package com.nsa.welshpharmacy.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nsa.welshpharmacy.model.Pharmacy;
import com.nsa.welshpharmacy.services.FirebaseServices;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by c1712480 on 28/04/2018.
 */

public class ListPharmaciesViewModel extends ViewModel implements ValueEventListener{
    private MutableLiveData<List<Pharmacy>> pharmacies;
    private DatabaseReference mDatabase;

    /**
     * Loads the pharmacies and creates a list of pharmacies but as mutable live data
     * @return LiveData<List<Pharmacy>>
     */
    public LiveData<List<Pharmacy>> getPharmacies(){
        if(pharmacies == null){
            pharmacies = new MutableLiveData<List<Pharmacy>>();
            loadPharmacies();
        }
        return pharmacies;
    }

    /**
     * Get a reference to the database
     */
    private void loadPharmacies(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseServices.loadPharmacies(this);
    }

    /**
     * Firebase ValueEventListener interface methods for the Pharmacies
     * @param dataSnapshot
     */
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        List<Pharmacy> listOfPharmacies = new ArrayList<>();
        // see:  https://firebase.google.com/docs/database/android/lists-of-data#listen_for_value_events
        // Retrieve all  Pharmacy records from the Firebase database in one go
        for(DataSnapshot pharmacySnapshot : dataSnapshot.getChildren()){
            Pharmacy pharmacy = pharmacySnapshot.getValue(Pharmacy.class);
            pharmacy.setId(pharmacySnapshot.getKey());
            listOfPharmacies.add(pharmacy);
        }
        pharmacies.setValue(listOfPharmacies);
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        System.out.println("The read failed: " + databaseError.getCode());
    }
}
