
package com.nsa.welshpharmacy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;


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


        final TextView ratting = (TextView) findViewById(R.id.ratting);
        ratting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ratting.setText("Rating have submitted successfully ");
                int[] i = new int[]{ R.id.RattingBar};
            }

        });


    }
}

