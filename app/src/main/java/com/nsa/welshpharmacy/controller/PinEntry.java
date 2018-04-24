package com.nsa.welshpharmacy.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.goodiebag.pinview.Pinview;
import com.nsa.welshpharmacy.R;


public class PinEntry extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_entry);

        Pinview pinview = (Pinview)findViewById(R.id.pinView);
        pinview.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview PinView, boolean b) {
                String pincode=""+pinview.getValue();
                String correctPin = "1111";
                if (pincode == correctPin){
                    Toast.makeText(PinEntry.this, "Welcome back", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(PinEntry.this, StaffFilterPreferenceActivity.class));
                }
                else {
                    Toast.makeText(PinEntry.this, ""+pinview.getValue(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button btn2 = (Button)findViewById(R.id.resetButton);

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PinEntry.this, ResetPinView.class));
            }
        });
    }
}
