package com.nsa.welshpharmacy.controller.listPharmacies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nsa.welshpharmacy.R;
import com.nsa.welshpharmacy.manager.LanguageManager;

public class EmailPharmacyActivity extends AppCompatActivity implements View.OnClickListener {
    private SharedPreferences pharmacyEmail;

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
        setContentView(R.layout.content_email_pharmacy);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        pharmacyEmail = getSharedPreferences("emailAddress", Context.MODE_PRIVATE);

        Button sendEmail = findViewById(R.id.send_email_btn);
        sendEmail.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //Get email address first
        String emailAddress = pharmacyEmail.getString("email", "error");
        //Create emailing intent
        if ((emailAddress != "error") && (emailAddress != null)) {
            String text = getEmailContent();
            Intent anEmailIntent = new Intent(Intent.ACTION_SEND);
            //Define mail data
            anEmailIntent.setData(Uri.parse("mailto:"));
            anEmailIntent.setType("text/plain");
            //Define to Who
            anEmailIntent.putExtra(Intent.EXTRA_EMAIL, emailAddress);
            //Receiver/Message content
            anEmailIntent.putExtra(Intent.EXTRA_SUBJECT, "Question");
            if ((text != null) && (text != "")) {
                anEmailIntent.putExtra(Intent.EXTRA_TEXT, text);
            } else {
                anEmailIntent.putExtra(Intent.EXTRA_TEXT, "Dear Sir/Madam. ");
            }
            try {
                startActivity(Intent.createChooser(anEmailIntent, "Send email.."));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(this, "There's no email client installed!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public String getEmailContent() {
        EditText usersMessage = findViewById(R.id.users_msg_edittext);
        return usersMessage.getText().toString();
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
