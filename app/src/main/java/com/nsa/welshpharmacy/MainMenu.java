
package com.nsa.welshpharmacy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);


        Button button = findViewById(R.id.GeneralUsers);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pharmacyListView = new Intent(getApplicationContext(), FilterPreferenceActivity.class);
                startActivity(pharmacyListView);
            }
        });
        final TextView submitRating = (TextView)findViewById(R.id.rating);
        final RatingBar rating = (RatingBar) findViewById(R.id.RatingBar);
        submitRating.setClickable(true);
        submitRating.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){
                submitRating.setText("Rating has been submitted successfully ");
                String totalStars = "Total Stars:: " + rating.getNumStars();
                String ratingr = "Rating :: " + rating.getRating();
                Toast.makeText(getApplicationContext(), totalStars + "\n" + ratingr, Toast.LENGTH_LONG).show();
            }

        });


    }

}


