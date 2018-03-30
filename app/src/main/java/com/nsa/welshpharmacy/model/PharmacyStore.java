package com.nsa.welshpharmacy.model;

import android.nfc.Tag;
import android.support.constraint.solver.widgets.Snapshot;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by c1712480 on 29/03/2018.
 */

public class PharmacyStore {

    public static void loadPharmacies(){
        //Get a reference to our pharmacies
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("pharmacies");

        // Attach a listener to read the data at our pharmacies reference
        ref.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                // see:  https://firebase.google.com/docs/database/android/lists-of-data#listen_for_value_events
                // Retrieve all  Pharmacy records from the firebase database in one go
                for(DataSnapshot pharmacySnapshot : dataSnapshot.getChildren()){
                    Pharmacy pharmacy = pharmacySnapshot.getValue(Pharmacy.class);
                    System.out.println(pharmacy);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError){
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
}
