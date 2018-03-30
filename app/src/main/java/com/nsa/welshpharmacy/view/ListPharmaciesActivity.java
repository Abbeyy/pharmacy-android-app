package com.nsa.welshpharmacy.view;

import android.os.Bundle;
<<<<<<< HEAD:app/src/main/java/com/nsa/welshpharmacy/view/ListPharmaciesActivity.java
import android.support.v7.widget.ListViewCompat;
=======
>>>>>>> 53b9dbb6f43fbc063fb6ecc039bc093a35917d74:app/src/main/java/com/nsa/welshpharmacy/ListPharmaciesActivity.java
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


<<<<<<< HEAD:app/src/main/java/com/nsa/welshpharmacy/view/ListPharmaciesActivity.java
import com.nsa.welshpharmacy.R;
import com.nsa.welshpharmacy.model.Pharmacy;
import com.nsa.welshpharmacy.model.PharmacyStore;

import java.util.ArrayList;
=======
>>>>>>> 53b9dbb6f43fbc063fb6ecc039bc093a35917d74:app/src/main/java/com/nsa/welshpharmacy/ListPharmaciesActivity.java
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * Created by c1714546 on 3/14/2018.
 *
 * This class is the Activity behind User Story 7 on
 * our Taiga Sprint 1 Taskboard. The aim of this
 * activity is to offer a GUI to the user, on
 * which a list is offered to the user of
 * Pharmacies offering services through the
 * medium of the Welsh language.
 *
 * @author Abbey Ross.
 * @version 1.0 March 14th, 2018.
 */

public class ListPharmaciesActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_pharmacies_activity_layout);
        //activity should be inflating list_pharmacies_activity_layout - mostly empty layout
        //due to use of fragments - but that doesnt work? This does?.... fix!

        //setting up main fragment of view
        android.support.v4.app.FragmentManager fmtManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fmtTransaction = fmtManager.beginTransaction();
        fmtTransaction.add(R.id.fragments_container, new ListPharmaciesMainFragment());
        fmtTransaction.commit();
    }

}
