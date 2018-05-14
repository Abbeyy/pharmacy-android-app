package com.nsa.welshpharmacy.controller.listPharmacies;

import android.annotation.TargetApi;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
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
import com.nsa.welshpharmacy.services.LocationServices;
import com.nsa.welshpharmacy.viewModel.ListPharmaciesViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * This class is the first fragment displayed when
 * ListPharmaciesActivity is launched by an intent.
 * It contains a Listview to list Pharmacies gathered
 * from a Firebase database, by name.
 *
 * Created by c1714546 on 3/18/2018.
 *
 * @author Abbey Ross.
 * @version 1.0 April 30th, 2018.
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
    private String stringUserLocation;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.list_pharmacies_fragment_one_layout, container, false);

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
            stringUserLocation = bundle.getString("userLocation");
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
                    //listOfNames.clear();
                    //listOfNames.addAll(filterPharmacyNames());
                    pharmaciesByDistance(listOfPharmacies);
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

    /**
     * This method programatically generates today's date and then populates
     * the text of a textview with the result. Depending on whether the
     * application is in English or Welsh, the appropriate message will
     * be displayed.
     *
     * @param v View.
     */
    public void setUpDate(View v) {

        // Adapted from: https://stackoverflow.com/questions/40310773/android-studio-textview-show-date
//        STARTS HERE
        TextView dateTV = v.findViewById(R.id.date_text_view);
        Date currentDate = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        String date_today = format.format(currentDate);
//        ENDS HERE


        // Accessed via hardcoded due to retrieval from
        // string resources grabbing integers instead
        // of the string value.
        if (currentLocale == "cy") {
            dateTV.setText(" Dyddiad heddiw:  " + date_today);
        } else {
            dateTV.setText(" Today's Date:  " + date_today);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Switch fragments
        expandPharmacyInfo(position);
    }

    /**
     * This method passes a Pharmacy object picked by the user upon
     * a tap of a listview item, and then switches this fragment
     * out for ListPharmacyDetailsFragment.
     *
     * @param position int, stores the order number of the
     * listview item picked e.g.
     * 1st item, position will be 0.
     */
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
     * Loops through the pharmacy objects to determine what services they have and if it is what the
     * user selected, then add them to a list.
     * @return list of filtered pharmacies
     */
    public List<Pharmacy> filterPharmaciesBySelection(){
        List<Pharmacy> filteredPharmacies = new ArrayList<>();
        if(!booleanAilments && !booleanFlu && !booleanHealth && !booleanSmoking && !booleanAlcohol){
            filteredPharmacies.addAll(listOfPharmacies);
        }
        for(Pharmacy pharmacy : listOfPharmacies){
            for(Map.Entry<String, PharmacyServiceAvailability> pharmacyService : pharmacy.getServices().entrySet()) {
                PharmacyServiceAvailability serviceValue = pharmacyService.getValue();
                if(serviceValue.defaultAvailability != null) {
                    switch (pharmacyService.getKey()) {
                        case "minorAilments":
                            if (booleanAilments && serviceValue.defaultAvailability.get("cym")) {
                                if(!filteredPharmacies.contains(pharmacy)){
                                    filteredPharmacies.add(pharmacy);
                                }
                            }
                            break;
                        case "fluVac":
                            if (booleanFlu && serviceValue.defaultAvailability.get("cym")){
                                if(!filteredPharmacies.contains(pharmacy)){
                                    filteredPharmacies.add(pharmacy);
                                }
                            }
                            break;
                        case "healthCheck":
                            if (booleanHealth && serviceValue.defaultAvailability.get("cym")){
                                if(!filteredPharmacies.contains(pharmacy)){
                                    filteredPharmacies.add(pharmacy);
                                }
                            }
                            break;
                        case "smoking":
                            if (booleanSmoking && serviceValue.defaultAvailability.get("cym")){
                                if(!filteredPharmacies.contains(pharmacy)){
                                    filteredPharmacies.add(pharmacy);
                                }
                            }
                            break;
                        case "alcohol":
                            if (booleanAlcohol && serviceValue.defaultAvailability.get("cym")){
                                if(!filteredPharmacies.contains(pharmacy)){
                                    filteredPharmacies.add(pharmacy);
                                }
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

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void pharmaciesByDistance(List<Pharmacy> pharmacyList){
        listOfNames.clear();
        Map<Pharmacy, Float> map = LocationServices.sortPharmaciesByLocation(getContext(), pharmacyList);
        for(Pharmacy pharmacy : map.keySet()){
            listOfNames.add(pharmacy.getName());
        }
        la.notifyDataSetChanged();
    }
}
