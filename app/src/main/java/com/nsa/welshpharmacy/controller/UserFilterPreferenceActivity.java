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
import android.widget.CompoundButton;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.nsa.welshpharmacy.R;
import com.nsa.welshpharmacy.controller.listPharmacies.ListPharmaciesActivity;
import com.nsa.welshpharmacy.manager.LanguageManager;
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

    private AppCompatCheckBox checkMinorAilments;
    private AppCompatCheckBox checkFluVac;
    private AppCompatCheckBox checkHealthCheck;
    private AppCompatCheckBox checkSmoking;
    private AppCompatCheckBox checkAlcohol;
    private AppCompatEditText textPostcodeWidget;
    private SwitchCompat switchOnLocationWidget;

    private AppCompatButton submitButton;
    private AppCompatButton resetButton;

    private SharedPreferences sharedPreferences;

    private static final int REQUEST_FINE_LOCATION = 1;


    private static final String PREFS_NAME = "prefs";
    private static final String PREF_DARK_THEME = "dark_theme";
    private static final String PREF_TRACK= "allow_tracking";
    private static final String PREF_MINOR = "minor";
    private static final String PREF_FLU = "flu";
    private static final String PREF_HEALTH = "health";
    private static final String PREF_SMOKING = "smoking";
    private static final String PREF_ALCOHOL = "alcohol";
    private static final String PREF_POSTCODE = "postcode";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Saving user choices below.
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean useDarkTheme = preferences.getBoolean(PREF_DARK_THEME, false);
        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();


        if(useDarkTheme) {
            setTheme(R.style.darkthem);
        }

        //Setting up activity and inflating layout.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_filter_preference);

        //Other setups.
        currentLang = getSharedPreferences("currentLanguage", Context.MODE_PRIVATE);
        edit = currentLang.edit();

        alreadyChanged = getPreferences(Context.MODE_PRIVATE);
        editLangChanged = alreadyChanged.edit();

        String langAlreadyChanged = alreadyChanged.getString("state", "error");
        currentLocale = currentLang.getString("state", "error");

        if (currentLocale != "error") {
            if (langAlreadyChanged == "error") {
                changeLanguage(langAlreadyChanged);
            } else {
                String result = alreadyChanged.getString("state", "error");
                editLangChanged.clear();
                editLangChanged.apply();
            }
        }

        changeLangToEnglish = findViewById(R.id.lang_to_english);
        changeLangToWelsh = findViewById(R.id.lang_to_welsh);
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

        //Additional code to save user choices.

        SwitchCompat nightModeSwitch = (SwitchCompat) findViewById(R.id.nightmodeswitch);
        AppCompatCheckBox minorCheck=(AppCompatCheckBox)findViewById(R.id.check_minor_ailments);
        AppCompatCheckBox fluCheck=(AppCompatCheckBox)findViewById(R.id.check_flu_vaccines);
        AppCompatCheckBox healthCheck=(AppCompatCheckBox)findViewById(R.id.check_health_check);
        AppCompatCheckBox smokingCheck=(AppCompatCheckBox)findViewById(R.id.check_smoking);
        AppCompatCheckBox alcoholCheck=(AppCompatCheckBox)findViewById(R.id.check_alcohol);
        AppCompatEditText postcode=(AppCompatEditText)findViewById(R.id.text_postcode);
        SwitchCompat allowTrackingSwitch = (SwitchCompat) findViewById(R.id.switch_location);

        nightModeSwitch.setChecked(useDarkTheme);
        minorCheck.setChecked(preferences.getBoolean(PREF_MINOR, false));
        fluCheck.setChecked(preferences.getBoolean(PREF_FLU, false));
        healthCheck.setChecked(preferences.getBoolean(PREF_HEALTH, false));
        smokingCheck.setChecked(preferences.getBoolean(PREF_SMOKING, false));
        alcoholCheck.setChecked(preferences.getBoolean(PREF_ALCOHOL, false));
        postcode.setText(preferences.getString(PREF_POSTCODE, ""));
        allowTrackingSwitch.setChecked(preferences.getBoolean(PREF_TRACK, false));

        minorCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                editor.putBoolean(PREF_MINOR, b);
                editor.apply();
            }
        });
        fluCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                editor.putBoolean(PREF_FLU, b);
                editor.apply();
            }
        });
        healthCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                editor.putBoolean(PREF_HEALTH, b);
                editor.apply();
            }
        });
        smokingCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                editor.putBoolean(PREF_SMOKING, b);
                editor.apply();
            }
        });
        alcoholCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                editor.putBoolean(PREF_ALCOHOL, b);
                editor.apply();
            }
        });


        nightModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton view, boolean isChecked) {
                toggleTheme(isChecked);
            }
        });

    }


    private void toggleTheme(boolean darkTheme) {
        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
        editor.putBoolean(PREF_DARK_THEME, darkTheme);
        editor.apply();

        Intent intent = getIntent();
        finish();

        startActivity(intent);
    }

    private void toggleTrack(boolean track) {
        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
        editor.putBoolean(PREF_TRACK, track);
        editor.apply();




        currentLang = getSharedPreferences("currentLanguage", Context.MODE_PRIVATE);
        edit = currentLang.edit();

        alreadyChanged = getPreferences(Context.MODE_PRIVATE);
        editLangChanged = alreadyChanged.edit();

        String langAlreadyChanged = alreadyChanged.getString("state", "error");
        currentLocale = currentLang.getString("state", "error");

        if (currentLocale != "error") {
            if (langAlreadyChanged == "error") {
                changeLanguage(langAlreadyChanged);
            } else {
                String result = alreadyChanged.getString("state", "error");
                editLangChanged.clear();
                editLangChanged.apply();
            }
        }

        changeLangToEnglish = findViewById(R.id.lang_to_english);
        changeLangToWelsh = findViewById(R.id.lang_to_welsh);
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
    }


    private void initValues() {
        if (sharedPreferences != null) {
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
            textPostcodeWidget.setText("");
        }

        if ((id == R.id.lang_to_welsh) || (id == R.id.lang_to_english)) {
            languageSwitch(id);
        }

        Matcher matcher = POSTCODE_REGEX.matcher(textPostcodeWidget.getText());
        //If there is not a valid postcode and the switch is not checked show a toast warning
        //And if both a valid postcode and the switch is checked show the toast warning
        if ((!matcher.matches() && !switchOnLocationWidget.isChecked()) || (matcher.matches() && switchOnLocationWidget.isChecked())) {
            Toast.makeText(this, R.string.enter_valid_location, Toast.LENGTH_SHORT).show();
        }

        if (id == R.id.submit_button && matcher.matches()) {
            try {
                LocationServices.loadPhoneLocationViaPostcode(this, textPostcodeWidget.toString());
                LatLng userLocation = LocationServices.getUserLocation();
                intentToList(userLocation);
            } catch (IOException e) {
                Toast.makeText(this, R.string.location_catch_statement, Toast.LENGTH_SHORT).show();
                return;
            }
        }
        if (id == R.id.submit_button && switchOnLocationWidget.isChecked()) {
           networkSelected();
        }
        //If the submit button is pressed and the shared preferences are null add the shared preferences
        if (id == R.id.submit_button && sharedPreferences != null) {
            applySharedPreferences();
        }
    }

    public void changeLanguage(String langAlreadyChanged) {
        Intent restartActivity = getIntent();

        switch (currentLocale) {
            case "error":
                changeLanguageToWelsh();
                startActivity(restartActivity);
                break;
            case "cy":
                changeLanguageToWelsh();
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
                changeLanguageToWelsh();
                startActivity(restartActivity);
                break;
        }
    }

    public void changeLanguageToWelsh(){
        edit.clear();
        edit.putString("state", "cy");
        edit.apply();
        editLangChanged.clear();
        editLangChanged.putString("state", "About to reload");
        editLangChanged.apply();

        LanguageManager.changeLang(this.getResources(), welshLanguageCode);
        finish();
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
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                LocationServices.loadPhoneLocationViaNetwork(this);
            }else{
                Toast.makeText(this, R.string.perm_not_granted, Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void languageSwitch(int id){
        Intent restartActivity = getIntent();
        switch (id) {
            case R.id.lang_to_english:
                if (currentLocale == "en") {
                    Toast.makeText(this, R.string.lang_remaining, Toast.LENGTH_SHORT).show();
                } else if (currentLocale != null) {
                    Toast.makeText(this, R.string.lang_changing, Toast.LENGTH_SHORT).show();

                    edit.clear();
                    edit.putString("state", "en");
                    edit.apply();

                    LanguageManager.changeLang(getResources(), englishLanguageCode);
                    finish();
                    startActivity(restartActivity);
                } else {
                    Toast.makeText(this, R.string.lang_remaining, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.lang_to_welsh:
                if (currentLocale == "cy") {
                    Toast.makeText(this, R.string.lang_remaining, Toast.LENGTH_SHORT).show();
                } else if (currentLocale != null) {
                    Toast.makeText(this, R.string.lang_changing, Toast.LENGTH_SHORT).show();

                    edit.clear();
                    edit.putString("state", "cy");
                    edit.apply();

                    LanguageManager.changeLang(this.getResources(), welshLanguageCode);
                    finish();
                    startActivity(restartActivity);
                } else {
                    Toast.makeText(this, R.string.lang_remaining, Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                Toast.makeText(this, R.string.error_lang_remaining, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void applySharedPreferences(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        edit.clear();
        editor.putBoolean(KeyValueHelper.KEY_SWITCH_LOCATION, switchOnLocationWidget.isChecked());
        editor.apply();
    }

    public void networkSelected(){
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (LocationServices.isNetworkEnabled(this)) {
                //If fine location permission is already granted then update last known location from network
                LocationServices.loadPhoneLocationViaNetwork(this);
                LatLng userLocation = LocationServices.getUserLocation();
                //Use to check if location is still working: note- Turn on your phone's location!
                //Toast.makeText(this, LocationServices.getUserLocation().toString(), Toast.LENGTH_LONG).show();
                //Then switch view to next activity
                intentToList(userLocation);
            }
        } else {
            //Fine location permission has not been granted
            //Rationale as to why the user should grant permission
            if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(this, R.string.fine_loc,
                        Toast.LENGTH_SHORT).show();
            }
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_FINE_LOCATION);
        }
    }

    public void intentToList(LatLng latLng){
        Intent pharmacyListView = new Intent(this, ListPharmaciesActivity.class);
        pharmacyListView.putExtra("checkAilments", checkMinorAilments.isChecked());
        pharmacyListView.putExtra("checkFlu", checkFluVac.isChecked());
        pharmacyListView.putExtra("checkHealth", checkHealthCheck.isChecked());
        pharmacyListView.putExtra("checkSmoking", checkSmoking.isChecked());
        pharmacyListView.putExtra("checkAlcohol", checkAlcohol.isChecked());
        pharmacyListView.putExtra("userLocation", latLng);
        startActivity(pharmacyListView);
    }
}
