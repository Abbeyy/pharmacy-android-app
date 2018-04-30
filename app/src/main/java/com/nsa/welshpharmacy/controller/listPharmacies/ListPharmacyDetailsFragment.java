package com.nsa.welshpharmacy.controller.listPharmacies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.ListViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.nsa.welshpharmacy.R;
import com.nsa.welshpharmacy.controller.PharmacyMapActivity;
import com.nsa.welshpharmacy.model.Pharmacy;
import com.nsa.welshpharmacy.services.LocationServices;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is the second fragment to be displayed
 * for ListPharmaciesActivity. It displays a listview of
 * the full details of the Pharmacy chosen in the
 * previous fragment, ListPharmaciesFragment, and more.
 *
 * Created by c1714546 on 4/2/2018.
 *
 * @author Abbey Ross.
 * @version 1.0 April 30th, 2018.
 */

public class ListPharmacyDetailsFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {
    ListViewCompat lv;
    List<String> aList;
    ArrayAdapter<String> la;
    private SharedPreferences currentLang;
    private String currentLocale;
    private SharedPreferences latLongs;
    private SharedPreferences pharmacysEmail;

    public ListPharmacyDetailsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.list_pharmacys_details_fragment_two_layout, container, false);
        super.onCreate(savedInstanceState);

        currentLang = getActivity().getSharedPreferences("currentLanguage", Context.MODE_PRIVATE);
        currentLocale = currentLang.getString("state", "default");

        Bundle bundle = this.getArguments();
        Pharmacy recievedPharmacy = bundle.getParcelable("selectedPharmacy");

        latLongs = getActivity().getSharedPreferences("latitudeLongitudes", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = latLongs.edit();
        edit.putString("pharmLatLong", recievedPharmacy.getPharmacyLatLng(getActivity()).toString());
        edit.putString("userLatLong", LocationServices.getUserLocation().toString());
        edit.apply();

        pharmacysEmail = getActivity().getSharedPreferences("emailAddress", Context.MODE_PRIVATE);

        AppCompatButton btnToMap = (AppCompatButton) v.findViewById(R.id.button_to_map);
        btnToMap.setOnClickListener(this);

        this.lv = v.findViewById(R.id.listview_pharmacys_details);

        this.aList = new ArrayList<>();
        populateData(recievedPharmacy);

        this.la = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                this.aList
        );

        this.lv.setAdapter(this.la);
        this.lv.setOnItemClickListener(this);

        return v;
    }

    public void populateData(Pharmacy selectedPharmacy) {
        aList.add(selectedPharmacy.getName());
        aList.add(selectedPharmacy.getPhone());
        aList.add(selectedPharmacy.getPostcode());
        aList.add(selectedPharmacy.getEmail());
        aList.add(selectedPharmacy.getWebsite());

        SharedPreferences.Editor editEmail = pharmacysEmail.edit();
        editEmail.clear();
        editEmail.putString("email", selectedPharmacy.getEmail());
        editEmail.apply();
    }

    /**
     * This method decides whether an intent should be invoked upon the
     * user's click of a particular listview item. If the user clicks
     * the item containing a phone number, an ACTION_DIAL intent is invoked.
     * If the user click's an item containing an email address, the
     * EmailPharmacyActivity is launched via an intent.
     *
     * @param parent
     * @param view View.
     * @param position int.
     * @param id long.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 1:
                //Get phone number first
                String number = this.aList.get(1);
                String phoneNumber = removeWhiteSpace(number);
                //Create phone call intent
                Intent aPhoneCallIntent = new Intent(Intent.ACTION_DIAL);
                //Pass information to intent
                aPhoneCallIntent.setData(Uri.parse("tel:"+phoneNumber));
                startActivity(aPhoneCallIntent);
                break;
            case 3 :
                Intent launchEmail = new Intent(getActivity(), EmailPharmacyActivity.class);
                startActivity(launchEmail);
                break;
            default:
                break;
        }
    }

    public String removeWhiteSpace(String number) {
        String result = "";
        for(Character num : number.toCharArray()) {
            if(Character.isWhitespace(num) == false)
                result = result + num;
        }
        return result;
    }

    /**
     *
     * This method launches the PharmacyMapActivity via an intent if a
     * particular button is clicked by the user on this fragment.
     *
     * @param v View.
     */
    @Override
    public void onClick(View v) {
        Toast.makeText(getActivity(), R.string.launch_map, Toast.LENGTH_SHORT).show();

        Intent mapActivity = new Intent(getActivity(), PharmacyMapActivity.class);
        startActivity(mapActivity);
    }
}
