package com.nsa.welshpharmacy.controller.language.AppTheme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate.NightMode;
import android.support.v7.widget.AppCompatButton;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.nsa.welshpharmacy.R;

/**
 * Created by c1660911 on 4/24/2018.
 */


        import android.content.Intent;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.app.AppCompatDelegate;
        import android.support.v7.widget.AppCompatButton;
        import android.widget.CompoundButton;
        import android.widget.LinearLayout;
        import android.widget.Switch;

        import com.nsa.welshpharmacy.R;

/**
 * Created by c1660911 on 4/24/2018.
 */

public class AppTheme extends AppCompatActivity {
    private Switch nightmodeswitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES) {
//            setTheme(R.style.darkthem);
//            restartApp();
//        }
//        else setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
//
        setContentView(R.layout.activity_user_filter_preference);
//


        //    System.out.println("############################# " + button.getText());

        ((AppCompatButton) findViewById(R.id.lang_to_english)).setBackgroundColor(R.style.darkthem);


        nightmodeswitch = (Switch) findViewById(R.id.nightmodeswitch);

        nightmodeswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    setContentView(R.layout.activity_user_filter_preference);
                    NightMood();


                } else {
                    setContentView(R.layout.activity_user_filter_preference);
                    DayMood();


                }
            }
        });
    }


    public void restartApp() {
        Intent i = new Intent(getApplicationContext(), AppTheme.class);
        startActivity(i);
        finish();
    }

    public void NightMood() {

        AppCompatButton button = (AppCompatButton) findViewById(R.id.lang_to_english);
        button.setBackgroundColor(R.style.darkthem);
        button.setTextColor(R.style.darkthem);

        AppCompatButton button1 = (AppCompatButton) findViewById(R.id.lang_to_welsh);
        button1.setBackgroundColor(R.style.darkthem);
        button.setTextColor(R.style.darkthem);

    }

    public void DayMood() {

        AppCompatButton button = (AppCompatButton) findViewById(R.id.lang_to_english);
        button.setBackgroundColor(R.style.AppTheme);
        button.setTextColor(R.style.AppTheme);
        setContentView(R.layout.activity_user_filter_preference);

        AppCompatButton button1 = (AppCompatButton) findViewById(R.id.lang_to_welsh);
        button1.setBackgroundColor(R.style.AppTheme);
        button1.setTextColor(R.style.AppTheme);
        setContentView(R.layout.activity_user_filter_preference);

    }


}
