package com.nsa.welshpharmacy.stores;

import android.support.constraint.solver.widgets.Snapshot;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nsa.welshpharmacy.model.Languages;
import com.nsa.welshpharmacy.model.PharmacyService;

/**
 * Created by c1712480 on 30/03/2018.
 */

public class LanguagesStore {
    public static void loadLanguages(){
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
                    Languages languages = languageSnapshot.getValue(Languages.class);
                    languages.setId(languageSnapshot.getKey());
                    System.out.println(languages);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError){
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
}
