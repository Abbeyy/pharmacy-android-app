package com.nsa.welshpharmacy.listpharmacies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.nsa.welshpharmacy.R;

import java.util.ArrayList;

/**
 * Created by c1714546 on 4/2/2018.
 */

public class ListPharmacysDetailsFragment extends Fragment {
    private int position;

    public ListPharmacysDetailsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.list_pharmacys_details_fragment_two_layout, container, false);

        return v;
    }
}
