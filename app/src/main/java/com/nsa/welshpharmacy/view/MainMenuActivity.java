
package com.nsa.welshpharmacy.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nsa.welshpharmacy.R;


public class MainMenuActivity extends AppCompatActivity {

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


    }
}

