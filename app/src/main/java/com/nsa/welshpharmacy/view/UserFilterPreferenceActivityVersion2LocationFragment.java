package com.nsa.welshpharmacy.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nsa.welshpharmacy.R;

/**
 * Created by c1502032 on 23/04/2018.
 */

public class UserFilterPreferenceActivityVersion2LocationFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_filter_preference_activity_version2_fragment_location, container, false);
        return view;
    }

}
