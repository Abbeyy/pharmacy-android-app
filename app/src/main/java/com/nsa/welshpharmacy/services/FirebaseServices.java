package com.nsa.welshpharmacy.services;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nsa.welshpharmacy.model.Language;
import com.nsa.welshpharmacy.model.Pharmacy;
import com.nsa.welshpharmacy.model.PharmacyService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by c1712480 on 02/04/2018.
 */

public class FirebaseServices {

    public static List<Language> loadLanguages(){
        final ArrayList<Language> languages = new ArrayList<Language>();

        //Get a reference to the services
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("languages");

        // Attach a listener to read the data at the languages reference
        ref.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                // see:  https://firebase.google.com/docs/database/android/lists-of-data#listen_for_value_events
                // Retrieve all  languages from the Firebase database in one go
                for(DataSnapshot languageSnapshot : dataSnapshot.getChildren()){
                    Language language = languageSnapshot.getValue(Language.class);
                    language.setId(languageSnapshot.getKey());
                    //System.out.println(language);
                    languages.add(language);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError){
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        return languages;
    }

    public static List<Pharmacy> loadPharmacies(){
        final ArrayList<Pharmacy> pharmacies = new ArrayList<Pharmacy>();
        //Get a reference to the pharmacies
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("pharmacies");

        // Attach a listener to read the data at our pharmacies reference
        ref.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                // see:  https://firebase.google.com/docs/database/android/lists-of-data#listen_for_value_events
                // Retrieve all  Pharmacy records from the Firebase database in one go
                for(DataSnapshot pharmacySnapshot : dataSnapshot.getChildren()){
                    Pharmacy pharmacy = pharmacySnapshot.getValue(Pharmacy.class);
                    pharmacy.setId(pharmacySnapshot.getKey());
                    //System.out.println(pharmacy);
                    pharmacies.add(pharmacy);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError){
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        return pharmacies;
    }

    public static List<PharmacyService> loadServices(){
        final ArrayList<PharmacyService> pharmacyServices = new ArrayList<PharmacyService>();
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
                    //System.out.println(pharmacyService);
                    pharmacyServices.add(pharmacyService);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError){
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        return pharmacyServices;
    }
}
