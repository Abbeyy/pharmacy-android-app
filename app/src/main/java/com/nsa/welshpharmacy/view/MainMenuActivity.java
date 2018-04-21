
package com.nsa.welshpharmacy.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.nsa.welshpharmacy.R;
import com.nsa.welshpharmacy.model.Language;
import com.nsa.welshpharmacy.view.language.LanguageManager;


public class MainMenuActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    private String welshLanguageCode = "cy";
    private String englishLanguageCode = "en";

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


        final TextView rating = (TextView) findViewById(R.id.rating);
        rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rating.setText("Rating have submitted successfully ");
                int[] i = new int[]{ R.id.RatingBar};
            }

        });

        Switch changeLang = (Switch)findViewById(R.id.change_language);
        changeLang.setOnCheckedChangeListener(this);

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Toast.makeText(this, "Changing language...", Toast.LENGTH_SHORT).show();
        LanguageManager.changeLang(this.getResources(), welshLanguageCode);

        Intent restartActivity = getIntent();
        finish();
        startActivity(restartActivity);
    }

}

