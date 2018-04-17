
package com.nsa.welshpharmacy.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nsa.welshpharmacy.R;


public class MainMenuActivity extends AppCompatActivity {

    TextView mTextView;
    Spinner mLanguage;
    ArrayAdapter<String> mAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Button button = findViewById(R.id.GeneralUsers);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pharmacyListView = new Intent(getApplicationContext(), UserFilterPreferenceActivity.class);
                startActivity(pharmacyListView);
            }
        });
        final TextView submitRating = findViewById(R.id.rating);

        final RatingBar rating =  findViewById(R.id.RatingBar);
        rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                submitRating.setText("Rating have submitted successfully ");
                String totalStars = "Total Stars:: " + rating.getNumStars();
                String ratingResult = "Rating :: " + rating.getRating();
                Toast.makeText(getApplicationContext(), totalStars + "\n" + ratingResult, Toast.LENGTH_LONG).show();

            }

        });
        
        mLanguage = (Spinner) findViewById(R.id.spLanguage);
        mTextView = (TextView) findViewById (R.id.textView);
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.language_option));
        mLanguage.setAdapter(mAdapter);

        if (LocaleManager.getLanguage(MainMenuActivity.this).equalsIgnoreCase("en")) {
            mLanguage.setSelection(mAdapter.getPosition("English"));
        } else if (LocaleManager.getLanguage(MainMenuActivity.this).equalsIgnoreCase("in")) {
            mLanguage.setSelection(mAdapter.getPosition("Indonesian"));
        } else {
            mLanguage.setSelection(mAdapter.getPosition("Spanish"));
        }
        //switch between the languages
        mLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                 Context context;
                 Resources resources;
                 switch (i) {

                     case 1:
                         context = LocaleManager.setLocale(MainMenuActivity.this, "cy");
                         resources = context.getResources();
                         mTextView.setText(resources.getString(R.string.text_translation));
                         break;
                     case 2:
                         context = LocaleManager.setLocale(MainMenuActivity.this, "Default");
                         resources = context.getResources();
                         mTextView.setText(resources.getString(R.string.text_translation));
                         break;
                 }
             }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    @Override
    // override the base context of application to update default locale for this activity
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleManager.onAttach(newBase));
    }
}




