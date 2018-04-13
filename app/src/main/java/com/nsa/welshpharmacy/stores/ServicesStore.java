package com.nsa.welshpharmacy.stores;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nsa.welshpharmacy.model.Pharmacy;
import com.nsa.welshpharmacy.model.PharmacyService;
import com.nsa.welshpharmacy.services.FirebaseServices;

import java.util.List;

/**
 * Creates a list of pharmacy services as loaded in from the database
 * Created by c1712480 on 30/03/2018.
 */

public class ServicesStore {

    private List<PharmacyService> pharmacyServices = null;

    public List<PharmacyService> getPharmacyServices(){
        if (pharmacyServices == null){
            pharmacyServices = FirebaseServices.loadServices();
        }
        return pharmacyServices;
    }
}
