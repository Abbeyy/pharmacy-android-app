package com.nsa.welshpharmacy;

import android.app.FragmentManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;


import com.nsa.welshpharmacy.mockingdata.Pharmacy;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Vector;

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
        setContentView(R.layout.list_pharmacies_main);
        //activity should be inflating list_pharmacies - mostly empty layout
        //due to use of fragments - but that doesnt work? This does?.... fix!

        //date stuff
        setUpDate(); //line 62

        //setting up main fragment of view
        Fragment fragment_main = new ListPharmaciesMainFragment();

        android.support.v4.app.FragmentManager fmtManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fmtTransaction = fmtManager.beginTransaction();
        fmtTransaction.add(R.id.fragments_container, fragment_main);
        fmtTransaction.commit();
    }

    public void setUpDate() {
        //Code help gathered from:
        // https://stackoverflow.com/questions/40310773/android-studio-textview-show-date
        TextView dateTV = (TextView)findViewById(R.id.date_text_view);

        Date todaysDate = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        String date_today = format.format(todaysDate);

        dateTV.setText("  Today's Date: " + date_today);
    }

}
