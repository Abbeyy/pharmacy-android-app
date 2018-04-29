package com.nsa.welshpharmacy.controller.language.AppTheme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate.NightMode;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.SwitchCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.nsa.welshpharmacy.R;

/**
 * Created by c1660911 on 4/24/2018.
 */


        import android.content.Intent;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.app.AppCompatDelegate;
        import android.support.v7.widget.AppCompatButton;
        import android.widget.CompoundButton;
        import android.widget.LinearLayout;
        import android.widget.Switch;
import android.widget.Toolbar;

import com.nsa.welshpharmacy.R;

/**
 * Created by c1660911 on 4/24/2018.
 */

public class AppSettings extends AppCompatActivity {
    private SwitchCompat nightModeSwitch;
    private SwitchCompat allowTrackingSwitch;
    private AppCompatCheckBox minorCheck;
    private AppCompatCheckBox fluCheck;
    private AppCompatCheckBox healthCheck;
    private AppCompatCheckBox smokingCheck;
    private AppCompatCheckBox alcoholCheck;
    private AppCompatEditText postcode;

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
        // Use the chosen theme
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean useDarkTheme = preferences.getBoolean(PREF_DARK_THEME, false);
        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();


        if(useDarkTheme) {
            setTheme(R.style.darkthem);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_filter_preference);



        nightModeSwitch  = (SwitchCompat) findViewById(R.id.nightmodeswitch);
        minorCheck=(AppCompatCheckBox)findViewById(R.id.check_minor_ailments);
        fluCheck=(AppCompatCheckBox)findViewById(R.id.check_flu_vaccines);
        healthCheck=(AppCompatCheckBox)findViewById(R.id.check_health_check);
        smokingCheck=(AppCompatCheckBox)findViewById(R.id.check_smoking);
        alcoholCheck=(AppCompatCheckBox)findViewById(R.id.check_alcohol);
        postcode=(AppCompatEditText)findViewById(R.id.text_postcode);
        allowTrackingSwitch = (SwitchCompat) findViewById(R.id.switch_location);

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


        postcode.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if(!s.equals("") ) {
                    editor.putString(PREF_POSTCODE, s.toString());
                    editor.apply();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }
            public void afterTextChanged(Editable s) {

            }
        });

        allowTrackingSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton view, boolean isChecked) {
                toggleTrack(isChecked);
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


    }


}