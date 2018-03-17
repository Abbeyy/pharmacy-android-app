package com.nsa.welshpharmacy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by c1712480 on 14/03/2018.
 */

public class FilterPreferenceActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener, SharedPreferences.OnSharedPreferenceChangeListener{

    private AppCompatCheckBox checkMinorAilments;
    private AppCompatCheckBox checkFluVac;
    private AppCompatCheckBox checkHealthCheck;
    private AppCompatCheckBox checkSmoking;
    private AppCompatCheckBox checkAlcohol;
    private AppCompatEditText textWidget;

    private AppCompatButton submitButton;
    private AppCompatButton resetButton;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_preference);

        this.checkMinorAilments = this.findViewById(R.id.check_minor_ailments);
        this.checkFluVac = this.findViewById(R.id.check_flu_vaccines);
        this.checkHealthCheck = this.findViewById(R.id.check_health_check);
        this.checkSmoking = this.findViewById(R.id.check_smoking);
        this.checkAlcohol = this.findViewById(R.id.check_alcohol);
        this.textWidget = this.findViewById(R.id.text_postcode);

        this.submitButton = this.findViewById(R.id.submit_button);
        this.resetButton = this.findViewById(R.id.reset_button);

        this.sharedPreferences = this.getPreferences(MODE_PRIVATE);

        initValues();

        textWidget.setOnClickListener(this);
        submitButton.setOnClickListener(this);
        resetButton.setOnLongClickListener(this);

    }

    private void initValues(){
        if (this.sharedPreferences != null) {
            this.checkMinorAilments.setChecked(sharedPreferences.getBoolean(KeyValueHelper.KEY_CHECKBOX_AILMENTS, KeyValueHelper.DEFAULT_WIDGET_CHECKBOX));
            this.checkFluVac.setChecked(sharedPreferences.getBoolean(KeyValueHelper.KEY_CHECKBOX_FLU, KeyValueHelper.DEFAULT_WIDGET_CHECKBOX));
            this.checkHealthCheck.setChecked(sharedPreferences.getBoolean(KeyValueHelper.KEY_CHECKBOX_HEALTH, KeyValueHelper.DEFAULT_WIDGET_CHECKBOX));
            this.checkSmoking.setChecked(sharedPreferences.getBoolean(KeyValueHelper.KEY_CHECKBOX_SMOKING, KeyValueHelper.DEFAULT_WIDGET_CHECKBOX));
            this.checkAlcohol.setChecked(sharedPreferences.getBoolean(KeyValueHelper.KEY_CHECKBOX_ALCOHOL, KeyValueHelper.DEFAULT_WIDGET_CHECKBOX));
            //this.textWidget.setText(this.sharedPreferences.getString(KeyValueHelper.KEY_WIDGET_TEXT, KeyValueHelper.DEFAULT_WIDGET_TEXT));
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
        Pattern POSTCODE_REGEX = Pattern.compile(getString(R.string.postcode_regex));

        Matcher matcher = POSTCODE_REGEX.matcher(textWidget.getText());
        if (!matcher.matches()) {
            Toast.makeText(this, R.string.postcode_invalid, Toast.LENGTH_SHORT).show();
        }else{
            if (id == R.id.submit_button && this.sharedPreferences != null){
                SharedPreferences.Editor editor = this.sharedPreferences.edit();
                editor.putBoolean(KeyValueHelper.KEY_CHECKBOX_AILMENTS, this.checkMinorAilments.isChecked());
                editor.putBoolean(KeyValueHelper.KEY_CHECKBOX_FLU, this.checkFluVac.isChecked());
                editor.putBoolean(KeyValueHelper.KEY_CHECKBOX_HEALTH, this.checkHealthCheck.isChecked());
                editor.putBoolean(KeyValueHelper.KEY_CHECKBOX_SMOKING, this.checkSmoking.isChecked());
                editor.putBoolean(KeyValueHelper.KEY_CHECKBOX_ALCOHOL, this.checkAlcohol.isChecked());
                editor.apply();

                Intent pharmacyListView = new Intent(this, ListPharmaciesActivity.class);
                startActivity(pharmacyListView);
            }
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

        Toast.makeText(this, R.string.on_shared_pref_saved_text, Toast.LENGTH_SHORT).show();
    }
}
