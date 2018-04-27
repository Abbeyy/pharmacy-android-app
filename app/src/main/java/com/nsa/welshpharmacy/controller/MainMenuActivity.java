
package com.nsa.welshpharmacy.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nsa.welshpharmacy.R;
import com.nsa.welshpharmacy.controller.language.LanguageManager;


public class MainMenuActivity extends AppCompatActivity implements View.OnClickListener {
    private String welshLanguageCode = "cy";
    private String englishLanguageCode = "en";
    private AppCompatButton changeLangToEnglish, changeLangToWelsh;
    private SharedPreferences currentLang;
    private static int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        currentLang = getSharedPreferences("currentLanguage", Context.MODE_PRIVATE);

        changeLangToEnglish = (AppCompatButton) findViewById(R.id.lang_to_english);
        changeLangToWelsh = (AppCompatButton) findViewById(R.id.lang_to_welsh);

        Button button = findViewById(R.id.GeneralUsers);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pharmacyListView = new Intent(getApplicationContext(), UserFilterPreferenceActivity.class);
                startActivity(pharmacyListView);
            }
        });

        final TextView rating = (TextView) findViewById(R.id.rating);
        rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rating.setText("Rating have submitted successfully ");
                int[] i = new int[]{ R.id.RatingBar};
            }

        });

        changeLangToEnglish.setOnClickListener(this);
        changeLangToWelsh.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent restartActivity = getIntent();
        String currentLocale = currentLang.getString("state", "error");
        SharedPreferences.Editor edit = currentLang.edit();

        if (counter == 0) {
            edit.putString("state", "en");
            edit.apply();
        }
        counter++;

        switch (v.getId()) {

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
}

