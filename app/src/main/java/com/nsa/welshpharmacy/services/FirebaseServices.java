package com.nsa.welshpharmacy.services;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nsa.welshpharmacy.model.Pharmacy;
import com.nsa.welshpharmacy.model.PharmacyServiceAvailability;

import java.util.HashMap;
import java.util.Map;

/**
 * This service is linked to Firebase and reads to the Firebase database.
 * It loads in the data from the database into the project.
 * Created by c1712480 on 02/04/2018.
 */

public class FirebaseServices {

    public static void loadPharmacies(ValueEventListener listener){
        //Get a reference to the pharmacies
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("pharmacies");
        ref.addValueEventListener(listener);
    }

    public static Pharmacy constructPharmacy(DataSnapshot dataSnapshot){
        String id = dataSnapshot.getKey();
        String email = (String) dataSnapshot.child("email").getValue();
        String name = (String) dataSnapshot.child("name").getValue();
        String phone = (String) dataSnapshot.child("phone").getValue();
        String postcode = (String) dataSnapshot.child("postcode").getValue();
        Map<String,PharmacyServiceAvailability> services = new HashMap<>();
        if(dataSnapshot.child("services").exists()){
            for(DataSnapshot serviceSnapshot : dataSnapshot.child("services").getChildren()){
                String serviceId = serviceSnapshot.getKey();
                PharmacyServiceAvailability pharmacyServiceAvailability = constructServiceAvailability(serviceSnapshot);
                services.put(serviceId, pharmacyServiceAvailability);
            }
        }
        String website = (String) dataSnapshot.child("website").getValue();
        return new Pharmacy(id, email, name, phone, postcode, services, website);
    }

    private static PharmacyServiceAvailability constructServiceAvailability(DataSnapshot dataSnapshot){
        Map<String, Boolean> defaultAvailability = new HashMap<>();
        DataSnapshot defaultSnapshot = dataSnapshot.child("default");
        for(DataSnapshot languageSnapshot : defaultSnapshot.getChildren()){
            String language = languageSnapshot.getKey();
            Boolean availability = (Boolean) languageSnapshot.getValue();
            defaultAvailability.put(language, availability);
        }
        return new PharmacyServiceAvailability(defaultAvailability);
    }

}
