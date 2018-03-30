package com.nsa.welshpharmacy.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nsa.welshpharmacy.R;

/**
 * Created by c1714546 on 3/18/2018.
 */

public class ListPharmaciesMainFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_pharmacies, container, false);
    }
}
