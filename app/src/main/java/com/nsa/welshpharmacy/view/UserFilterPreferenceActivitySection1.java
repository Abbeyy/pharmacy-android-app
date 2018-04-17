package com.nsa.welshpharmacy.view;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.nsa.welshpharmacy.R;
import com.nsa.welshpharmacy.services.LocationServices;
import com.nsa.welshpharmacy.view.listPharmacies.ListPharmaciesActivity;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class UserFilterPreferenceActivitySection1 extends AppCompatActivity {

    private AppCompatCheckBox checkMinorAilments;
    private AppCompatCheckBox checkFluVac;
    private AppCompatCheckBox checkHealthCheck;
    private AppCompatCheckBox checkSmoking;
    private AppCompatCheckBox checkAlcohol;





    private SharedPreferences sharedPreferences;

    /*private static final int REQUEST_FINE_LOCATION = 1;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_filter_preference_section1);
        AppCompatButton nextButton = (AppCompatButton) findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             Intent nextButton = new Intent(UserFilterPreferenceActivitySection1.this, UserFilterPreferenceActivitySection2.class);
             startActivity(nextButton);
            }
        });

        this.checkMinorAilments = this.findViewById(R.id.check_minor_ailments);
        this.checkFluVac = this.findViewById(R.id.check_flu_vaccines);
        this.checkHealthCheck = this.findViewById(R.id.check_health_check);
        this.checkSmoking = this.findViewById(R.id.check_smoking);
        this.checkAlcohol = this.findViewById(R.id.check_alcohol);

        this.sharedPreferences = this.getPreferences(MODE_PRIVATE);

        initValues();


    }

    private void initValues(){
        if (this.sharedPreferences != null) {
            this.checkMinorAilments.setChecked(sharedPreferences.getBoolean(KeyValueHelper.KEY_CHECKBOX_AILMENTS, KeyValueHelper.DEFAULT_WIDGET_BOOLEAN));
            this.checkFluVac.setChecked(sharedPreferences.getBoolean(KeyValueHelper.KEY_CHECKBOX_FLU, KeyValueHelper.DEFAULT_WIDGET_BOOLEAN));
            this.checkHealthCheck.setChecked(sharedPreferences.getBoolean(KeyValueHelper.KEY_CHECKBOX_HEALTH, KeyValueHelper.DEFAULT_WIDGET_BOOLEAN));
            this.checkSmoking.setChecked(sharedPreferences.getBoolean(KeyValueHelper.KEY_CHECKBOX_SMOKING, KeyValueHelper.DEFAULT_WIDGET_BOOLEAN));
            this.checkAlcohol.setChecked(sharedPreferences.getBoolean(KeyValueHelper.KEY_CHECKBOX_ALCOHOL, KeyValueHelper.DEFAULT_WIDGET_BOOLEAN));
           /* this.switchOnLocationWidget.setChecked(sharedPreferences.getBoolean(KeyValueHelper.KEY_SWITCH_LOCATION, KeyValueHelper.DEFAULT_WIDGET_BOOLEAN));*/
        }
    }

}

