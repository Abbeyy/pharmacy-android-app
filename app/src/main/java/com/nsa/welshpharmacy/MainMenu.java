
package com.nsa.welshpharmacy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.goodiebag.pinview.Pinview;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Pinview pinview = (Pinview)findViewById(R.id.pinView);
        pinview.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview PinView, boolean b) {
//                Toast.makeText(MainMenu.this, ""+pinview.getValue(), Toast.LENGTH_SHORT).show();
            }
        }
//        REFRENCE: EDMT DEV Page, ----
//        View view = this.getCurrentFocus();
//        if (view != null) {
//            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//        }﻿
        );
    }

}
