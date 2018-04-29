package com.nsa.welshpharmacy.controller.listPharmacies;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nsa.welshpharmacy.R;
import com.nsa.welshpharmacy.model.Pharmacy;
import com.nsa.welshpharmacy.viewModel.ListPharmaciesViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by c1714546 on 3/18/2018.
 */

public class ListPharmaciesFragment extends Fragment implements AdapterView.OnItemClickListener {

    ListViewCompat lv;
    List<Pharmacy> listOfPharmacies;
    List<String> listOfNames;
    private ListPharmaciesViewModel mViewModel;
    private ArrayAdapter<String> la;

    private FragmentManager fmtManager;
    private FragmentTransaction fmtTrans;
    private SharedPreferences currentLang;
    private String currentLocale;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.list_pharmacies_fragment_one_layout, container, false);
        //inflating layout list_pharmacies_fragment_one_layout as layout for my fragment, holding both
        //textviews and the listview!

        Toolbar myToolbar = getActivity().findViewById(R.id.my_toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(myToolbar);

        currentLang = getActivity().getSharedPreferences("currentLanguage", Context.MODE_PRIVATE);
        currentLocale = currentLang.getString("state", "default");
        mViewModel = ViewModelProviders.of(this).get(ListPharmaciesViewModel.class);
        listOfPharmacies = new ArrayList<>();
        listOfNames = new ArrayList<>();

        /**
         * Retrieves the pharmacy information from the PharmacyListViewModel
         * Adds the pharmacy data into a list of pharmacies and a list of pharmacy names
         * The list adapter is then notified of the data change
         */
        final Observer<List<Pharmacy>> pharmacyObserver = new Observer<List<Pharmacy>>(){
            @Override
            public void onChanged(@Nullable final List<Pharmacy> pharmacies) {
                if(pharmacies != null){
                    listOfPharmacies.addAll(pharmacies);
                    listOfNames.clear();
                    for(Pharmacy pharmacy : pharmacies){
                        listOfNames.add(pharmacy.getName());
                    }
                    la.notifyDataSetChanged();
                }
            }
        };
        mViewModel.getPharmacies().observe(this, pharmacyObserver);

        lv = v.findViewById(R.id.listview_pharmacies);
        la = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                listOfNames);

        lv.setAdapter(la);
        lv.setOnItemClickListener(this);

        setUpDate(v);
        return v;
    }

    public void setUpDate(View v) {
        //Adapted from: https://stackoverflow.com/questions/40310773/android-studio-textview-show-date
        TextView dateTV = v.findViewById(R.id.date_text_view);
        Date currentDate = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        String date_today = format.format(currentDate);
        if (currentLocale == "cy") {
            dateTV.setText(" Dyddiad heddiw: " + date_today);
        } else {
            dateTV.setText("  Today's Date: " + date_today);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Switch Fragments
        expandPharmacyInfo(position);
    }

    public void expandPharmacyInfo(int position) {
        List<Pharmacy> pharmacies = listOfPharmacies;
        // https://stackoverflow.com/a/46298244
        Bundle bundle = new Bundle();
        bundle.putParcelable("selectedPharmacy", pharmacies.get(position));
        //getParentFragment().setArguments(bundle);
        ListPharmacyDetailsFragment listPharmacyDetailsFragment = new ListPharmacyDetailsFragment();
        listPharmacyDetailsFragment.setArguments(bundle);
        //Then switch fragments.
        fmtManager = getActivity().getSupportFragmentManager();
        fmtTrans = fmtManager.beginTransaction();
        fmtTrans.replace(R.id.fragments_container, listPharmacyDetailsFragment).addToBackStack("fragTwo");
        fmtTrans.addToBackStack(null);
        fmtTrans.commit();
    }
}
