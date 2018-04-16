package com.nsa.welshpharmacy.stores;

import android.nfc.Tag;
import android.support.constraint.solver.widgets.Snapshot;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nsa.welshpharmacy.model.Pharmacy;
import com.nsa.welshpharmacy.services.FirebaseServices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Creates a list of pharmacies as loaded in from the database
 * Created by c1712480 on 29/03/2018.
 */

public class PharmacyStore {

    private List<Pharmacy> pharmacies = null;

    public List<Pharmacy> getPharmacies(){
        if(pharmacies == null){
            pharmacies = FirebaseServices.loadPharmacies();
        }
        return pharmacies;
    }
}
