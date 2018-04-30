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
import android.support.v7.widget.ListViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nsa.welshpharmacy.R;
import com.nsa.welshpharmacy.model.Pharmacy;
import com.nsa.welshpharmacy.model.PharmacyServiceAvailability;
import com.nsa.welshpharmacy.viewModel.ListPharmaciesViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by c1714546 on 3/18/2018.
 */

public class ListPharmaciesFragment extends Fragment implements AdapterView.OnItemClickListener {

    ListViewCompat lv;
    List<Pharmacy> listOfPharmacies;
    List<String> listOfNames;
    List<Pharmacy> filteredPharmacies;
    private ListPharmaciesViewModel mViewModel;
    private ArrayAdapter<String> la;

    private FragmentManager fmtManager;
    private FragmentTransaction fmtTrans;
    private SharedPreferences currentLang;
    private String currentLocale;

    private boolean booleanAilments;
    private boolean booleanFlu;
    private boolean booleanHealth;
    private boolean booleanSmoking;
    private boolean booleanAlcohol;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.list_pharmacies_fragment_one_layout, container, false);
        //inflating layout list_pharmacies_fragment_one_layout as layout for my fragment, holding both
        //textviews and the listview!

        currentLang = getActivity().getSharedPreferences("currentLanguage", Context.MODE_PRIVATE);
        currentLocale = currentLang.getString("state", "default");

        /**
         * Retrieved the bundle from the activity that contains the boolean values from the checkboxes
         */
        Bundle bundle = getArguments();
        if (bundle != null){
            booleanAilments = bundle.getBoolean("booleanAilments");
            booleanFlu = bundle.getBoolean("booleanFlu");
            booleanHealth = bundle.getBoolean("booleanHealth");
            booleanSmoking = bundle.getBoolean("booleanSmoking");
            booleanAlcohol = bundle.getBoolean("booleanAlcohol");
        }

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
                    listOfNames.addAll(filterPharmacyNames());
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

    /**
     * Loops through the pharmacy objects to determine what services they ahve and if it is what the
     * user selected, then add them to a list.
     * @return list of filtered pharmacies
     */
    public List<Pharmacy> filterPharmaciesBySelection(){
        List<Pharmacy> filteredPharmacies = new ArrayList<>();
        for(Pharmacy pharmacy : listOfPharmacies){
            for(Map.Entry<String, PharmacyServiceAvailability> pharmacyService : pharmacy.getServices().entrySet()) {
                PharmacyServiceAvailability serviceValue = pharmacyService.getValue();
                if(serviceValue.defaultAvailability != null) {
                    switch (pharmacyService.getKey()) {
                        case "minorAilments":
                            if (booleanAilments && serviceValue.defaultAvailability.get("cym")) {
                                filteredPharmacies.add(pharmacy);
                            }
                            break;
                        case "fluVac":
                            if (booleanFlu && serviceValue.defaultAvailability.get("cym")){
                                filteredPharmacies.add(pharmacy);
                            }
                            break;
                        case "healthCheck":
                            if (booleanHealth && serviceValue.defaultAvailability.get("cym")){
                                filteredPharmacies.add(pharmacy);
                            }
                            break;
                        case "smoking":
                            if (booleanSmoking && serviceValue.defaultAvailability.get("cym")){
                                filteredPharmacies.add(pharmacy);
                            }
                            break;
                        case "alcohol":
                            if (booleanAlcohol && serviceValue.defaultAvailability.get("cym")){
                                filteredPharmacies.add(pharmacy);
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
        }
        return filteredPharmacies;
    }

    /**
     * Creates a list of names from the pharmacies that have been filtered
     * @return list of filtered pharmacy names
     */
    public List<String> filterPharmacyNames(){
        List<String> listOfFilteredNames = new ArrayList<>();
        for(Pharmacy pharmacy : filterPharmaciesBySelection()){
            listOfFilteredNames.add(pharmacy.getName());
        }
        return listOfFilteredNames;
    }
}
