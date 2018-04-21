
package com.nsa.welshpharmacy.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.nsa.welshpharmacy.R;
import com.nsa.welshpharmacy.model.Language;
import com.nsa.welshpharmacy.view.language.LanguageManager;


public class MainMenuActivity extends AppCompatActivity implements View.OnClickListener {
    private String welshLanguageCode = "cy";
    private String englishLanguageCode = "en";
    private AppCompatButton changeLangToEnglish, changeLangToWelsh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

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

//    @Override
//    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//        Toast.makeText(this, "Changing language...", Toast.LENGTH_SHORT).show();
//
//            LanguageManager.changeLang(this.getResources(), welshLanguageCode);
//
//            Intent restartActivity = getIntent();
//            finish();
//            startActivity(restartActivity);
//
//            changeLang.setChecked(true);
//    }

    @Override
    public void onClick(View v) {
        Intent restartActivity = getIntent();

        switch (v.getId()) {

            case R.id.lang_to_english :
                Toast.makeText(this, "Changing language to English", Toast.LENGTH_SHORT).show();

                LanguageManager.changeLang(this.getResources(), englishLanguageCode);
                finish();
                startActivity(restartActivity);
                break;
            case R.id.lang_to_welsh :
                Toast.makeText(this, "Newid iaith i'r Cymraeg", Toast.LENGTH_SHORT).show();

                LanguageManager.changeLang(this.getResources(), welshLanguageCode);
                finish();
                startActivity(restartActivity);
                break;
            default :
                Toast.makeText(this, "Error - language remaining.", Toast.LENGTH_SHORT).show();

                break;
        }
    }
}

