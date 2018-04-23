package com.nsa.welshpharmacy.view;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nsa.welshpharmacy.R;
import com.nsa.welshpharmacy.services.LocationServices;
import com.nsa.welshpharmacy.view.listPharmacies.ListPharmaciesActivity;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by c1502032 on 20/04/2018.
 */

public class UserFilterPreferenceActivityLocationFragment extends Fragment implements View.OnClickListener, View.OnLongClickListener, SharedPreferences.OnSharedPreferenceChangeListener {


    private AppCompatEditText textPostcodeWidget;
    private SwitchCompat switchOnLocationWidget;

    private AppCompatEditText postcodeET;

    private AppCompatButton submitButton;
    private AppCompatButton resetButton;

    private SharedPreferences sharedPreferences;

    private static final int REQUEST_FINE_LOCATION = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_filter_preference_fragment_location, container, false);







        this.textPostcodeWidget = textPostcodeWidget.findViewById(R.id.text_postcode);
        this.switchOnLocationWidget = switchOnLocationWidget.findViewById(R.id.switch_location);

        this.submitButton = submitButton.findViewById(R.id.submit_button);
        this.resetButton = resetButton.findViewById(R.id.reset_button);

        /*
        the line below shows up as an error
        this.sharedPreferences = this.getPreferences(MODE_PRIVATE);
        */

        initValues();

        textPostcodeWidget.setOnClickListener(this);
        submitButton.setOnClickListener(this);
        resetButton.setOnLongClickListener(this);

        /* the line below shows up as an error
        postcodeET = (AppCompatEditText)findViewById(R.id.text_postcode);
        */
        postcodeET.setOnClickListener(this);

















        return view;

    }


    ////code for user filter preference
    private void initValues(){
        if (this.sharedPreferences != null) {
            this.switchOnLocationWidget.setChecked(sharedPreferences.getBoolean(KeyValueHelper.KEY_SWITCH_LOCATION, KeyValueHelper.DEFAULT_WIDGET_BOOLEAN));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (this.sharedPreferences != null)
            this.sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
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
            editor.putBoolean(KeyValueHelper.KEY_SWITCH_LOCATION, this.switchOnLocationWidget.isChecked());
            editor.apply();
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
