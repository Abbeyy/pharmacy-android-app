package com.nsa.welshpharmacy.view;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.support.v7.widget.ListViewCompat;

import com.nsa.welshpharmacy.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by c1502032 on 15/03/2018.
 */

public class StaffFilterPreferenceActivity extends AppCompatActivity {

    LinearLayoutCompat layout;
    ToggleButton toggleButton1;
    ToggleButton toggleButton2;
    ToggleButton toggleButton3;
    ToggleButton toggleButton4;
    ToggleButton toggleButton5;
    ListViewCompat listView;
    List<String> servicesInWelsh;
    ArrayAdapter<String> arrayAdapter;


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.onAttach(base, "en"));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_services_in_welsh);
        toggleButton1 = (ToggleButton) findViewById(R.id.toggleButton);
        toggleButton2 = (ToggleButton) findViewById(R.id.toggleButton2);
        toggleButton3 = (ToggleButton) findViewById(R.id.toggleButton3);
        toggleButton4 = (ToggleButton) findViewById(R.id.toggleButton4);
        toggleButton5 = (ToggleButton) findViewById(R.id.toggleButton5);
        layout = (LinearLayoutCompat) findViewById(R.id.layout);

        /*this.listView = this.findViewById(R.id.list_view);



        this.servicesInWelsh = new ArrayList<>();
        servicesInWelsh.add("Minor Ailments");
        servicesInWelsh.add("Flu Vaccinations");
        servicesInWelsh.add("NHS Health Check");
        servicesInWelsh.add("Stop Smoking Service");
        servicesInWelsh.add("Advice on Alcohol Consumption");

        int i;
        for(i=0; i < servicesInWelsh.size(); i++) {
            System.out.println(i);
        }

        this.arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                this.servicesInWelsh);
        this.listView.setAdapter(this.arrayAdapter);
    }*/


    }

}