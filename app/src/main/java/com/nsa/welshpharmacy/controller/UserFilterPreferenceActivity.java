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
import android.view.View;
import android.widget.Toast;

import com.nsa.welshpharmacy.R;
import com.nsa.welshpharmacy.manager.LanguageManager;
import com.nsa.welshpharmacy.controller.listPharmacies.ListPharmaciesActivity;
import com.nsa.welshpharmacy.services.LocationServices;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This activity allows the user to select their required service and to have their location
 * inputted. The user has a choice of either a valid postcode or allowing the phone GPS to be used.
 *
 * Created by c1712480 on 14/03/2018.
 */

public class UserFilterPreferenceActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener, SharedPreferences.OnSharedPreferenceChangeListener {
    private String welshLanguageCode = "cy";
    private String englishLanguageCode = "en";
    private AppCompatButton changeLangToEnglish, changeLangToWelsh;
    private SharedPreferences currentLang;
    private SharedPreferences alreadyChanged;
    private String currentLocale;
    private SharedPreferences.Editor edit;
    private SharedPreferences.Editor editLangChanged;
//    private SharedPreferences buttonWasClicked;
//    private SharedPreferences.Editor editButtonClicked;
//    private int id;

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
        edit = currentLang.edit();

        alreadyChanged = getPreferences(Context.MODE_PRIVATE);
        editLangChanged = alreadyChanged.edit();

        String langAlreadyChanged = alreadyChanged.getString("state", "error");
        currentLocale = currentLang.getString("state", "error");

//        buttonWasClicked = getPreferences(Context.MODE_PRIVATE);
//        String buttonWasClickedToSwitchLang = buttonWasClicked.getString("state", "error");

        if (currentLocale != "error") {
//            if ((buttonWasClickedToSwitchLang == "error") || (buttonWasClickedToSwitchLang == "no")) {
            if (langAlreadyChanged == "error") {
                changeLanguage(langAlreadyChanged);
            } else {
                String result = alreadyChanged.getString("state", "error");
                editLangChanged.clear();
                editLangChanged.apply();
            }
//            } else if (buttonWasClickedToSwitchLang != "error") {
//                String answer = buttonWasClicked.getString("state", "error");
//                Log.i("BUTTON INFO", answer + "<!");
//                editButtonClicked.clear();
//                editButtonClicked.apply();
//            }
        }

        changeLangToEnglish = (AppCompatButton) findViewById(R.id.lang_to_english);
        changeLangToWelsh = (AppCompatButton) findViewById(R.id.lang_to_welsh);
        changeLangToEnglish.setOnClickListener(this);
        changeLangToWelsh.setOnClickListener(this);

        checkMinorAilments = findViewById(R.id.check_minor_ailments);
        checkFluVac = findViewById(R.id.check_flu_vaccines);
        checkHealthCheck = findViewById(R.id.check_health_check);
        checkSmoking = findViewById(R.id.check_smoking);
        checkAlcohol = findViewById(R.id.check_alcohol);
        textPostcodeWidget = findViewById(R.id.text_postcode);
        switchOnLocationWidget = findViewById(R.id.switch_location);

        submitButton = findViewById(R.id.submit_button);
        resetButton = findViewById(R.id.reset_button);

        sharedPreferences = getPreferences(MODE_PRIVATE);

        initValues();

        textPostcodeWidget.setOnClickListener(this);
        submitButton.setOnClickListener(this);
        resetButton.setOnLongClickListener(this);

        postcodeET = (AppCompatEditText) findViewById(R.id.text_postcode);
        postcodeET.setOnClickListener(this);
    }

    private void initValues() {
        if (sharedPreferences != null) {
            checkMinorAilments.setChecked(sharedPreferences.getBoolean(KeyValueHelper.KEY_CHECKBOX_AILMENTS, KeyValueHelper.DEFAULT_WIDGET_BOOLEAN));
            checkFluVac.setChecked(sharedPreferences.getBoolean(KeyValueHelper.KEY_CHECKBOX_FLU, KeyValueHelper.DEFAULT_WIDGET_BOOLEAN));
            checkHealthCheck.setChecked(sharedPreferences.getBoolean(KeyValueHelper.KEY_CHECKBOX_HEALTH, KeyValueHelper.DEFAULT_WIDGET_BOOLEAN));
            checkSmoking.setChecked(sharedPreferences.getBoolean(KeyValueHelper.KEY_CHECKBOX_SMOKING, KeyValueHelper.DEFAULT_WIDGET_BOOLEAN));
            checkAlcohol.setChecked(sharedPreferences.getBoolean(KeyValueHelper.KEY_CHECKBOX_ALCOHOL, KeyValueHelper.DEFAULT_WIDGET_BOOLEAN));
            switchOnLocationWidget.setChecked(sharedPreferences.getBoolean(KeyValueHelper.KEY_SWITCH_LOCATION, KeyValueHelper.DEFAULT_WIDGET_BOOLEAN));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sharedPreferences != null)
            sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (sharedPreferences != null)
            sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        //Adapted from https://stackoverflow.com/a/4531500 Retrieved: 17/3/18
        //Adapted from https://stackoverflow.com/a/8204716 Retrieved 17/3/18
        Pattern POSTCODE_REGEX = Pattern.compile(getString(R.string.postcode_regex));

        if (id == R.id.text_postcode) {
            postcodeET.setText("");
        }

        if ((id == R.id.lang_to_welsh) || (id == R.id.lang_to_english)) {
//            editButtonClicked.clear();
//            editButtonClicked.putString("state", "yes");
//            editButtonClicked.apply();

            Intent restartActivity = getIntent();

            switch (id) {
                case R.id.lang_to_english:
                    if (currentLocale == "en") {
                        Toast.makeText(this, "Language remaining in English.", Toast.LENGTH_SHORT).show();
                    } else if (currentLocale != null) {
//                        if (currentLocale == "en") {
//                            Toast.makeText(this, "Language remaining in English", Toast.LENGTH_SHORT).show();
//                        } else {
                        Toast.makeText(this, "Changing language to English", Toast.LENGTH_SHORT).show();

                        edit.clear();
                        edit.putString("state", "en");
                        edit.apply();

                        LanguageManager.changeLang(getResources(), englishLanguageCode);
                        finish();
                        startActivity(restartActivity);
//                        }
                    } else {
                        Toast.makeText(this, "Language remaining in English.", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.lang_to_welsh:
                    if (currentLocale == "cy") {
                        Toast.makeText(this, "Iaith yn aros yn Cymraeg.", Toast.LENGTH_SHORT).show();
                    } else if (currentLocale != null) {
//                        if (currentLocale == "cy") {
//                            Toast.makeText(this, "Iaith yn aros yn Gymraeg", Toast.LENGTH_SHORT).show();
//                        } else {
                        Toast.makeText(this, "Newid iaith i'r Cymraeg", Toast.LENGTH_SHORT).show();

                        edit.clear();
                        edit.putString("state", "cy");
                        edit.apply();

                        LanguageManager.changeLang(this.getResources(), welshLanguageCode);
                        finish();
                        startActivity(restartActivity);
//                        }
                    } else {
                        Toast.makeText(this, "Iaith yn aros yn Cymraeg.", Toast.LENGTH_SHORT).show();
                    }
                default:
                    Toast.makeText(this, "Error - language remaining.", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        Matcher matcher = POSTCODE_REGEX.matcher(textPostcodeWidget.getText());
        //If there is not a valid postcode and the switch is not checked show a toast warning
        //And if both a valid postcode and the switch is checked show the toast warning
        if ((!matcher.matches() && !switchOnLocationWidget.isChecked()) || (matcher.matches() && switchOnLocationWidget.isChecked())) {
            Toast.makeText(this, R.string.enter_valid_location, Toast.LENGTH_SHORT).show();
            return;
        }
        if (id == R.id.submit_button && matcher.matches()) {
            try {
                LocationServices.loadPhoneLocationViaPostcode(this, textPostcodeWidget.toString());
            } catch (IOException e) {
                Toast.makeText(this, R.string.location_catch_statement, Toast.LENGTH_SHORT).show();
                return;
            }
            Intent pharmacyListView = new Intent(this, ListPharmaciesActivity.class);
            startActivity(pharmacyListView);
        }
        if (id == R.id.submit_button && switchOnLocationWidget.isChecked()) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                if (LocationServices.isNetworkEnabled(this)) {
                    //If fine location permission is already granted then update last known location from network
                    LocationServices.loadPhoneLocationViaNetwork(this);
                    Toast.makeText(this, LocationServices.getUserLocation().toString(), Toast.LENGTH_LONG).show();
                    //Then switch view to next activity
                    Intent pharmacyListView = new Intent(this, ListPharmaciesActivity.class);
                    startActivity(pharmacyListView);
                }
            } else {
                //Fine location permission has not been granted
                //Rationale as to why the user should grant permission
                if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    Toast.makeText(this, "Fine location permission is needed to retrieve the location.",
                            Toast.LENGTH_SHORT).show();
                }
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_FINE_LOCATION);
            }
        }
        //If the submit button is pressed and the shared preferences are null add the shared preferences
        if (id == R.id.submit_button && sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(KeyValueHelper.KEY_CHECKBOX_AILMENTS, checkMinorAilments.isChecked());
            editor.putBoolean(KeyValueHelper.KEY_CHECKBOX_FLU, checkFluVac.isChecked());
            editor.putBoolean(KeyValueHelper.KEY_CHECKBOX_HEALTH, checkHealthCheck.isChecked());
            editor.putBoolean(KeyValueHelper.KEY_CHECKBOX_SMOKING, checkSmoking.isChecked());
            editor.putBoolean(KeyValueHelper.KEY_CHECKBOX_ALCOHOL, checkAlcohol.isChecked());
            editor.putBoolean(KeyValueHelper.KEY_SWITCH_LOCATION, switchOnLocationWidget.isChecked());
            editor.apply();
        }
    }

    public void changeLanguage(String langAlreadyChanged) {
        Intent restartActivity = getIntent();

        switch (currentLocale) {
            case "error":
                edit.clear();
                edit.putString("state", "cy");
                edit.apply();
                editLangChanged.clear();
                editLangChanged.putString("state", "About to reload");
                editLangChanged.apply();

                LanguageManager.changeLang(this.getResources(), welshLanguageCode);
                finish();
                startActivity(restartActivity);
                break;
            case "cy":
                edit.clear();
                edit.putString("state", "cy");
                edit.apply();
                editLangChanged.clear();
                editLangChanged.putString("state", "About to reload");
                editLangChanged.apply();

                LanguageManager.changeLang(this.getResources(), welshLanguageCode);
                finish();
                startActivity(restartActivity);
                break;
            case "en":
                edit.clear();
                edit.putString("state", "en");
                edit.apply();
                editLangChanged.clear();
                editLangChanged.putString("state", "About to reload");
                editLangChanged.apply();

                LanguageManager.changeLang(this.getResources(), englishLanguageCode);
                finish();
                startActivity(restartActivity);
                break;
            default:
                edit.clear();
                edit.putString("state", "cy");
                edit.apply();
                editLangChanged.clear();
                editLangChanged.putString("state", "About to reload");
                editLangChanged.apply();

                LanguageManager.changeLang(this.getResources(), welshLanguageCode);
                finish();
                startActivity(restartActivity);
                break;
        }
    }

    @Override
    public boolean onLongClick(View view){
        int id = view.getId();

        if (id == R.id.reset_button && sharedPreferences != null) {
            //Reset values to their default
            sharedPreferences.edit().clear().apply();
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
