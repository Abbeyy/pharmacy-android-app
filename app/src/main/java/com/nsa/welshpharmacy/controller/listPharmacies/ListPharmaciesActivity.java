package com.nsa.welshpharmacy.controller.listPharmacies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.nsa.welshpharmacy.R;
import com.nsa.welshpharmacy.manager.LanguageManager;

/**
 * Created by c1714546 on 3/14/2018.
 *
 * This class is the Activity behind User Story 7 on
 * our Taiga Sprint 1 Taskboard. The aim of this
 * activity is to offer a GUI to the user, on
 * which a list is offered to the user of
 * Pharmacies offering services through the
 * medium of the Welsh language.
 *
 * @author Abbey Ross.
 * @version 1.0 March 14th, 2018.
 */

public class ListPharmaciesActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private SharedPreferences pharmacyInstancesData;
    private SharedPreferences pharmacyLatLang;
    private String welshLanguageCode = "cy";
    private String englishLanguageCode = "en";
    private AppCompatButton changeLangToEnglish, changeLangToWelsh;
    private SharedPreferences currentLang;
    private SharedPreferences alreadyChanged;
    private String currentLocale;
    private SharedPreferences.Editor edit;
    private SharedPreferences.Editor editLangChanged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_pharmacies_activity_layout);
        //activity should be inflating list_pharmacies_activity_layout - mostly empty layout
        //due to use of fragments - but that doesnt work? This does?.... fix!

        sharedPreferences = getSharedPreferences("pharmacyPos", Context.MODE_PRIVATE);
        pharmacyLatLang = getSharedPreferences("pharmacyLatLang", Context.MODE_PRIVATE);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

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

        generatePharmaciesData();

        //setting up main fragment of view
        android.support.v4.app.FragmentManager fmtManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fmtTransaction = fmtManager.beginTransaction();
        fmtTransaction.add(R.id.fragments_container, new com.nsa.welshpharmacy.controller.listPharmacies.ListPharmaciesFragment());
        fmtTransaction.commit();
    }

    public void generatePharmaciesData() {
        pharmacyInstancesData = getSharedPreferences("pharmacies", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = pharmacyInstancesData.edit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        switch (id){
            case R.id.lang_to_english:
                languageSwitch(id);
                return true;
            case R.id.lang_to_welsh:
                languageSwitch(id);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void languageSwitch(int id){
        Intent restartActivity = getIntent();
        switch (id) {
            case R.id.lang_to_english:
                if (currentLocale == "en") {
                    Toast.makeText(this, "Language remaining in English.", Toast.LENGTH_SHORT).show();
                } else if (currentLocale != null) {
                    Toast.makeText(this, "Changing language to English", Toast.LENGTH_SHORT).show();

                    edit.clear();
                    edit.putString("state", "en");
                    edit.apply();

                    LanguageManager.changeLang(getResources(), englishLanguageCode);
                    finish();
                    startActivity(restartActivity);
                } else {
                    Toast.makeText(this, "Language remaining in English.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.lang_to_welsh:
                if (currentLocale == "cy") {
                    Toast.makeText(this, "Iaith yn aros yn Cymraeg.", Toast.LENGTH_SHORT).show();
                } else if (currentLocale != null) {
                    Toast.makeText(this, "Newid iaith i'r Cymraeg", Toast.LENGTH_SHORT).show();

                    edit.clear();
                    edit.putString("state", "cy");
                    edit.apply();

                    LanguageManager.changeLang(this.getResources(), welshLanguageCode);
                    finish();
                    startActivity(restartActivity);
                } else {
                    Toast.makeText(this, "Iaith yn aros yn Cymraeg.", Toast.LENGTH_SHORT).show();
                }
            default:
                Toast.makeText(this, "Error - language remaining.", Toast.LENGTH_SHORT).show();
                break;
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
}
