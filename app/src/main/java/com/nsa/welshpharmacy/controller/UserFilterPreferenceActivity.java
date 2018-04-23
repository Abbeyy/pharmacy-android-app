package com.nsa.welshpharmacy.controller;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.nsa.welshpharmacy.R;
import com.nsa.welshpharmacy.services.LocationServices;
import com.nsa.welshpharmacy.controller.listPharmacies.ListPharmaciesActivity;
import com.nsa.welshpharmacy.view.language.LanguageManager;
import com.nsa.welshpharmacy.view.listPharmacies.ListPharmaciesActivity;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This activity allows the user to select their required service and to have their location
 * inputted. The user has a choice of either a valid postcode or allowing the phone GPS to be used.
 * 
 * Created by c1712480 on 14/03/2018.
 */

public class UserFilterPreferenceActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener, SharedPreferences.OnSharedPreferenceChangeListener{
    private String welshLanguageCode = "cy";
    private String englishLanguageCode = "en";
    private AppCompatButton changeLangToEnglish, changeLangToWelsh;
    private SharedPreferences currentLang;
    private static int counter = 0;

    private AppCompatCheckBox checkMinorAilments;
    private AppCompatCheckBox checkFluVac;
    private AppCompatCheckBox checkHealthCheck;
    private AppCompatCheckBox checkSmoking;
    private AppCompatCheckBox checkAlcohol;
    private AppCompatEditText textPostcodeWidget;
    private SwitchCompat switchOnLocationWidget;

    private AppCompatEditText postcodeET;

    private AppCompatButton submitButton;
    private AppCompatButton resetButton;

    private SharedPreferences sharedPreferences;

    private static final int REQUEST_FINE_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_filter_preference);

        currentLang = getSharedPreferences("currentLanguage", Context.MODE_PRIVATE);
        changeLangToEnglish = (AppCompatButton) findViewById(R.id.lang_to_english);
        changeLangToWelsh = (AppCompatButton) findViewById(R.id.lang_to_welsh);
        changeLangToEnglish.setOnClickListener(this);
        changeLangToWelsh.setOnClickListener(this);

        this.checkMinorAilments = this.findViewById(R.id.check_minor_ailments);
        this.checkFluVac = this.findViewById(R.id.check_flu_vaccines);
        this.checkHealthCheck = this.findViewById(R.id.check_health_check);
        this.checkSmoking = this.findViewById(R.id.check_smoking);
        this.checkAlcohol = this.findViewById(R.id.check_alcohol);
        this.textPostcodeWidget = this.findViewById(R.id.text_postcode);
        this.switchOnLocationWidget = this.findViewById(R.id.switch_location);

        this.submitButton = this.findViewById(R.id.submit_button);
        this.resetButton = this.findViewById(R.id.reset_button);

        this.sharedPreferences = this.getPreferences(MODE_PRIVATE);

        initValues();

        textPostcodeWidget.setOnClickListener(this);
        submitButton.setOnClickListener(this);
        resetButton.setOnLongClickListener(this);

        postcodeET = (AppCompatEditText)findViewById(R.id.text_postcode);
        postcodeET.setOnClickListener(this);
    }

    private void initValues(){
        if (this.sharedPreferences != null) {
            this.checkMinorAilments.setChecked(sharedPreferences.getBoolean(KeyValueHelper.KEY_CHECKBOX_AILMENTS, KeyValueHelper.DEFAULT_WIDGET_BOOLEAN));
            this.checkFluVac.setChecked(sharedPreferences.getBoolean(KeyValueHelper.KEY_CHECKBOX_FLU, KeyValueHelper.DEFAULT_WIDGET_BOOLEAN));
            this.checkHealthCheck.setChecked(sharedPreferences.getBoolean(KeyValueHelper.KEY_CHECKBOX_HEALTH, KeyValueHelper.DEFAULT_WIDGET_BOOLEAN));
            this.checkSmoking.setChecked(sharedPreferences.getBoolean(KeyValueHelper.KEY_CHECKBOX_SMOKING, KeyValueHelper.DEFAULT_WIDGET_BOOLEAN));
            this.checkAlcohol.setChecked(sharedPreferences.getBoolean(KeyValueHelper.KEY_CHECKBOX_ALCOHOL, KeyValueHelper.DEFAULT_WIDGET_BOOLEAN));
            this.switchOnLocationWidget.setChecked(sharedPreferences.getBoolean(KeyValueHelper.KEY_SWITCH_LOCATION, KeyValueHelper.DEFAULT_WIDGET_BOOLEAN));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (this.sharedPreferences != null)
            this.sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (this.sharedPreferences != null)
            this.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onClick(View view){
        int id = view.getId();
        //Adapted from https://stackoverflow.com/a/4531500 Retrieved: 17/3/18
        //Adapted from https://stackoverflow.com/a/8204716 Retrieved 17/3/18
        Pattern POSTCODE_REGEX = Pattern.compile(getString(R.string.postcode_regex));

        if (id == R.id.text_postcode) {
            postcodeET.setText("");
        }
        if ((id == R.id.lang_to_welsh) || (id == R.id.lang_to_english)) {
            changeLanguage(id);
        }

        Matcher matcher = POSTCODE_REGEX.matcher(textPostcodeWidget.getText());
        //If there is not a valid postcode and the switch is not checked show a toast warning
        //And if both a valid postcode and the switch is checked show the toast warning
        if((!matcher.matches() && !switchOnLocationWidget.isChecked()) || (matcher.matches() && switchOnLocationWidget.isChecked())){
            Toast.makeText(this, R.string.enter_valid_location, Toast.LENGTH_SHORT).show();
            return;
        }
        if(id == R.id.submit_button && matcher.matches()){
            try {
                LocationServices.loadPhoneLocationViaPostcode(this, textPostcodeWidget.toString());
            }catch (IOException e){
                Toast.makeText(this, R.string.location_catch_statement, Toast.LENGTH_SHORT).show();
                return;
            }
            Intent pharmacyListView = new Intent(this, ListPharmaciesActivity.class);
            startActivity(pharmacyListView);
        }
        if(id == R.id.submit_button && switchOnLocationWidget.isChecked()){
            if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                if (LocationServices.isNetworkEnabled(this)){
                    //If fine location permission is already granted then update last known location from network
                    LocationServices.loadPhoneLocationViaNetwork(this);
                    Toast.makeText(this, LocationServices.getUserLocation().toString(), Toast.LENGTH_LONG).show();
                    //Then switch view to next activity
                    Intent pharmacyListView = new Intent(this, ListPharmaciesActivity.class);
                    startActivity(pharmacyListView);
                }
            } else{
                //Fine location permission has not been granted
                //Rationale as to why the user should grant permission
                if(shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)){
                    Toast.makeText(this, "Fine location permission is needed to retrieve the location.",
                            Toast.LENGTH_SHORT).show();
                }
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_FINE_LOCATION);
            }
        }
        //If the submit button is pressed and the shared preferences are null add the shared preferences
        if (id == R.id.submit_button && this.sharedPreferences != null){
            SharedPreferences.Editor editor = this.sharedPreferences.edit();
            editor.putBoolean(KeyValueHelper.KEY_CHECKBOX_AILMENTS, this.checkMinorAilments.isChecked());
            editor.putBoolean(KeyValueHelper.KEY_CHECKBOX_FLU, this.checkFluVac.isChecked());
            editor.putBoolean(KeyValueHelper.KEY_CHECKBOX_HEALTH, this.checkHealthCheck.isChecked());
            editor.putBoolean(KeyValueHelper.KEY_CHECKBOX_SMOKING, this.checkSmoking.isChecked());
            editor.putBoolean(KeyValueHelper.KEY_CHECKBOX_ALCOHOL, this.checkAlcohol.isChecked());
            editor.putBoolean(KeyValueHelper.KEY_SWITCH_LOCATION, this.switchOnLocationWidget.isChecked());
            editor.apply();
        }
    }

    public void changeLanguage(int id) {
        Intent restartActivity = getIntent();
        String currentLocale = currentLang.getString("state", "error");
        SharedPreferences.Editor edit = currentLang.edit();

        if (counter == 0) {
            edit.putString("state", "en");
            edit.apply();
        }
        counter++;
        switch (id) {

            case R.id.lang_to_english :
                if (currentLocale != null) {
                    if (currentLocale != "error") {
                        if (currentLocale == "en") {
                            Toast.makeText(this, "Language remaining in English", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Changing language to English", Toast.LENGTH_SHORT).show();

                            edit.clear();
                            edit.putString("state", "en");
                            edit.apply();

                            LanguageManager.changeLang(this.getResources(), englishLanguageCode);
                            finish();
                            startActivity(restartActivity);
                        }
                    } else {
                        Log.i("DEV", "Issue switching languages. Error returned from shared pref.");
                        Toast.makeText(this, "Could not switch language.", Toast.LENGTH_SHORT).show();
                    }
                    Log.i("DEV", "Issue switching languages. Nothing in shared prefs");
                    Toast.makeText(this, "Could not switch language.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.lang_to_welsh :
                if (currentLocale != null) {
                    if (currentLocale != "error") {
                        if (currentLocale == "cy") {
                            Toast.makeText(this, "Iaith yn aros yn Gymraeg", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Newid iaith i'r Cymraeg", Toast.LENGTH_SHORT).show();

                            edit.clear();
                            edit.putString("state", "cy");
                            edit.apply();

                            LanguageManager.changeLang(this.getResources(), welshLanguageCode);
                            finish();
                            startActivity(restartActivity);
                        }
                    }else {
                        Log.i("DEV", "Issue switching languages. Error returned from shared pref.");
                        Toast.makeText(this, "Methu newid iaith.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.i("DEV", "Issue swicthing languages. Nothing in shared prefs.");
                    Toast.makeText(this, "Methu newid iaith.", Toast.LENGTH_SHORT).show();
                }
                break;
            default :
                Toast.makeText(this, "Error - language remaining.", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public boolean onLongClick(View view){
        int id = view.getId();

        if (id == R.id.reset_button && this.sharedPreferences != null) {
            //Reset values to their default
            this.sharedPreferences.edit().clear().apply();
            //Pop up toast message to say that values have been reset
            Toast.makeText(this, getString(R.string.reset_text), Toast.LENGTH_SHORT).show();
            //Initialise sharedPreferences
            initValues();
        }
        return true;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s){
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == REQUEST_FINE_LOCATION) {
            //Check if the permission has been granted
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                LocationServices.loadPhoneLocationViaNetwork(this);
            }else{
                //Fine permission was denied so the feature cannot be used
                Toast.makeText(this, "Permission was not granted", Toast.LENGTH_SHORT).show();
            }
        }else{
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
