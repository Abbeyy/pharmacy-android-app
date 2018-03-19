package com.nsa.welshpharmacy;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.ToggleButton;

/**
 * Created by c1502032 on 15/03/2018.
 */

public class ServicesInWelshActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    RelativeLayout relativeLayout;
    ToggleButton toggleButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_services_in_welsh);
        toggleButton = (ToggleButton) findViewById(R.id.toggleButton);
        relativeLayout = (RelativeLayout) findViewById(R.id.layout);
        toggleButton.setOnCheckedChangeListener(this);
    }



    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {

            relativeLayout.setBackgroundColor(Color.BLUE);

        }else {

            relativeLayout.setBackgroundColor(Color.GRAY);

        }
    }
}

