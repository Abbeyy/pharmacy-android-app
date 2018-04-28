package com.nsa.welshpharmacy.controller.language.AppTheme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate.NightMode;
import android.support.v7.widget.AppCompatButton;
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

public class AppTheme extends AppCompatActivity {
    private Switch nightmodeswitch;

    private static final String PREFS_NAME = "prefs";
    private static final String PREF_DARK_THEME = "dark_theme";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Use the chosen theme
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean useDarkTheme = preferences.getBoolean(PREF_DARK_THEME, false);

        if(useDarkTheme) {
            setTheme(R.style.darkthem);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_filter_preference);



        nightmodeswitch  = (Switch) findViewById(R.id.nightmodeswitch);

        Switch toggle = (Switch) findViewById(R.id.nightmodeswitch);
        toggle.setChecked(useDarkTheme);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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


}