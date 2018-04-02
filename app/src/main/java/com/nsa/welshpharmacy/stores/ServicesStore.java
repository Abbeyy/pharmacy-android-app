package com.nsa.welshpharmacy.stores;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nsa.welshpharmacy.model.Pharmacy;
import com.nsa.welshpharmacy.model.PharmacyService;

/**
 * Created by c1712480 on 30/03/2018.
 */

public class ServicesStore {
    public static void loadServices(){
        //Get a reference to the services
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("services");

        // Attach a listener to read the data at the services reference
        ref.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                // see:  https://firebase.google.com/docs/database/android/lists-of-data#listen_for_value_events
                // Retrieve all  Pharmacy records from the Firebase database in one go
                for(DataSnapshot pharmacyServiceSnapshot : dataSnapshot.getChildren()){
                    PharmacyService pharmacyService = pharmacyServiceSnapshot.getValue(PharmacyService.class);
                    pharmacyService.setId(pharmacyServiceSnapshot.getKey());
                    System.out.println(pharmacyService);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError){
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
}
