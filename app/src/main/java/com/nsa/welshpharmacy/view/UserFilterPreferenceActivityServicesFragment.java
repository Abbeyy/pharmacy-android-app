package com.nsa.welshpharmacy.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nsa.welshpharmacy.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by c1502032 on 20/04/2018.
 */

public class UserFilterPreferenceActivityServicesFragment extends Fragment implements SharedPreferences.OnSharedPreferenceChangeListener{


    //code for user filter preference activity
    private AppCompatCheckBox checkMinorAilments;
    private AppCompatCheckBox checkFluVac;
    private AppCompatCheckBox checkHealthCheck;
    private AppCompatCheckBox checkSmoking;
    private AppCompatCheckBox checkAlcohol;
    private SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_filter_preference_fragment_services, container, false);

        //code for user filter preference
        this.checkMinorAilments = checkMinorAilments.findViewById(R.id.check_minor_ailments);
        this.checkFluVac = checkFluVac.findViewById(R.id.check_flu_vaccines);
        this.checkHealthCheck = checkHealthCheck.findViewById(R.id.check_health_check);
        this.checkSmoking = checkSmoking.findViewById(R.id.check_smoking);
        this.checkAlcohol = checkAlcohol.findViewById(R.id.check_alcohol);

        //the code below is giving me errors
        /*this.sharedPreferences = this.getPreferences(MODE_PRIVATE);*/

        initValues();

        return view;
    }


    //code for user filter preference
    private void initValues(){
        if (this.sharedPreferences != null) {
            this.checkMinorAilments.setChecked(sharedPreferences.getBoolean(KeyValueHelper.KEY_CHECKBOX_AILMENTS, KeyValueHelper.DEFAULT_WIDGET_BOOLEAN));
            this.checkFluVac.setChecked(sharedPreferences.getBoolean(KeyValueHelper.KEY_CHECKBOX_FLU, KeyValueHelper.DEFAULT_WIDGET_BOOLEAN));
            this.checkHealthCheck.setChecked(sharedPreferences.getBoolean(KeyValueHelper.KEY_CHECKBOX_HEALTH, KeyValueHelper.DEFAULT_WIDGET_BOOLEAN));
            this.checkSmoking.setChecked(sharedPreferences.getBoolean(KeyValueHelper.KEY_CHECKBOX_SMOKING, KeyValueHelper.DEFAULT_WIDGET_BOOLEAN));
            this.checkAlcohol.setChecked(sharedPreferences.getBoolean(KeyValueHelper.KEY_CHECKBOX_ALCOHOL, KeyValueHelper.DEFAULT_WIDGET_BOOLEAN));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (this.sharedPreferences != null)
            this.sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (this.sharedPreferences != null)
            this.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s){
    }
}
