package com.nsa.welshpharmacy;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.Toast;

/**
 * Created by c1712480 on 14/03/2018.
 */

public class FilterPreferenceActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{

    private AppCompatEditText textWidget;
    //private AppCompatCheckBox checkWidget;

    private AppCompatButton submitButton;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_preference);

        this.textWidget = this.findViewById(R.id.widget_text);

        //this.submitButton = this.findViewById(R.id.submit_button);
        //this.submitButton = this.findViewById(R.id.reset_button);

        this.sharedPreferences = this.getPreferences(MODE_PRIVATE);
    }

    private void initValues(){
        if (this.sharedPreferences != null) {
            this.textWidget.setText(this.sharedPreferences.getString(KeyValueHelper.KEY_WIDGET_TEXT, KeyValueHelper.DEFAULT_WIDGET_TEXT));
        }
    }

    @Override
    protected void onResume(){
        if (this.sharedPreferences != null)
            this.sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause(){
        if (this.sharedPreferences != null)
            this.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }

    /*
    @Override
    protected void onClick(View view){

        int id = view.getId();

        if (id == R.id.submit_button && this.sharedPreferences != null){
            SharedPreferences.Editor editor = this.sharedPreferences.edit();
            editor.putString(KeyValueHelper.KEY_WIDGET_EDIT, this.editWidget.getText().toString());
        }

        if (id == R.id.reset_button && this.sharedPreferences != null) {
            //Reset values to their default
            this.sharedPreferences.edit().clear().commit();
            //Pop up toast message to say that values have been reset
            Toast.makeText(this, getString(R.string.reset_text), Toast.LENGTH_SHORT).show();
            //Initialise sharedPreferences
            initValues();
        }
        return true;
    }
    */

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s){
        /*
        this.changesTextView.setText(String.format(
                this.getResources().getString(R.string.number_of_changes_text),
                this.sharedPreferences.getInt(KeyValueHelper.KEY_CHANGE_AMOUNT, KeyValueHelper.DEFAULT_CHANGE_AMOUNT)
        ));

        Toast.makeText(this, "Filters have been reset", Toast.LENGTH_SHORT).show();
         */
    }
}
