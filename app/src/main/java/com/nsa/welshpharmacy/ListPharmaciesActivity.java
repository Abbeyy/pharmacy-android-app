package com.nsa.welshpharmacy;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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

    ListViewCompat lView;
    List<String> aList;
    //Built-in adapter for string datasource
    ArrayAdapter<String> arrayAdpt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_pharmacies);
        //activity inflating layout

        //date stuff
        setUpDate();

        //line below is not working - not recognisning my listview on my layout by id?!
        this.lView = this.findViewById(R.id.listview_pharmacies);

        //Created mock datasource of an arraylist of strings;
        this.aList = new ArrayList<>();
        for (int i = 0; i<6; i++) {
            this.aList.add("pharmacy " + (i+1));
        }

        this.arrayAdpt = new ArrayAdapter<String> (
                this,
                android.R.layout.simple_list_item_1,
                //switch layout to be lv_pharmacy_names_row once sorted access!
                this.aList
        );

        this.lView.setAdapter(this.arrayAdpt);

    }

    public void setUpDate() {
        //Code help gathered from: https://stackoverflow.com/questions/40310773/android-studio-textview-show-date
        TextView dateTV = (TextView)findViewById(R.id.date_text_view);

        Date todaysDate = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        String date_today = format.format(todaysDate);

        dateTV.setText("  Today's Date: " + date_today);
    }

}
