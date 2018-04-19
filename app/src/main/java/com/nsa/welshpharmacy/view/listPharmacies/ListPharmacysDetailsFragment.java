package com.nsa.welshpharmacy.view.listPharmacies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.ListViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nsa.welshpharmacy.R;
import com.nsa.welshpharmacy.model.MockPharmacy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by c1714546 on 4/2/2018.
 */

public class ListPharmacysDetailsFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {
    ListViewCompat lView;
    List<String> aList;
    //Built-in adapter for string datasource
    ArrayAdapter<String> arrayAdpt;

    public ListPharmacysDetailsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.list_pharmacys_details_fragment_two_layout, container, false);

        SharedPreferences sharedPrefs = this.getActivity().getSharedPreferences("pharmacyPos", Context.MODE_PRIVATE);
        int pharmacyPosition = sharedPrefs.getInt("position", -1);
        Log.i("Pharmacy position: ", pharmacyPosition+ "!");
        //Reminder, 1st listed item will have a position of 0.

        //Help gathered from: https://stackoverflow.com/questions/7145606/how-android-sharedpreferences-save-store-object
        SharedPreferences pharmacies = this.getActivity().getSharedPreferences("pharmacies", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = pharmacies.getString("pharmacy" + pharmacyPosition, "Error");
        MockPharmacy pharmacyToDisplay = gson.fromJson(json, MockPharmacy.class);

//        Log.i("Pharmacy name: ", pharmacyToDisplay.getName());
//        Log.i("Pharmacy address: ", pharmacyToDisplay.getAddress());
//        Log.i("Pharmacy phone: ", pharmacyToDisplay.getPhoneNumber());
//        Log.i("Pharmacy email: ", pharmacyToDisplay.getEmail());

        AppCompatButton btnToMap = (AppCompatButton)v.findViewById(R.id.button_to_map);
        btnToMap.setOnClickListener(this);

        this.lView = v.findViewById(R.id.listview_pharmacys_details);

        this.aList = new ArrayList<>();
        populateMockedData(pharmacyToDisplay);

        this.arrayAdpt = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                this.aList
        );

        this.lView.setAdapter(this.arrayAdpt);
        this.lView.setOnItemClickListener(this);

        return v;
    }

    public void populateMockedData(MockPharmacy pharmacyToDisplay) {
        this.aList.add(pharmacyToDisplay.getName());
        this.aList.add(pharmacyToDisplay.getAddress());
        this.aList.add(pharmacyToDisplay.getPhoneNumber());
        this.aList.add(pharmacyToDisplay.getEmail());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
//            case 2:
//                //Get phone number first
//                String phoneNumber = this.aList.get(2);
//                Log.i("Got phone number", phoneNumber);
//                //Create phone call intent
//                Intent aPhoneCallIntent = new Intent(Intent.ACTION_CALL);
//                Log.i("Created phone intent", "yes!");
//                //Pass information to intent
//                aPhoneCallIntent.setData(Uri.parse("tel:"+phoneNumber));
//                Log.i("Successfully passed intent data", "Yes!");
//                //Start intent
//                startActivity(aPhoneCallIntent);
//                break;
            case 3 :
                //THE BELOW WORKS IF THE USER SELECTS TO
                //SEND MAIL VIA "MESSAGES" ON ANDROIDS OPTIONS.

                //Get email address first
                String emailAddress = this.aList.get(3);
                //Create emailing intent
                Intent anEmailIntent = new Intent(Intent.ACTION_SEND);
                //Define mail data
                anEmailIntent.setData(Uri.parse("mailto:"));
                anEmailIntent.setType("text/plain");
                //Define to Who
                anEmailIntent.putExtra(Intent.EXTRA_EMAIL, emailAddress);
                //Receiver/Message content
                anEmailIntent.putExtra(Intent.EXTRA_SUBJECT, "Test/Query");
                anEmailIntent.putExtra(Intent.EXTRA_TEXT, "Test message.");
                Log.i("starting email activity", "yes!");

                try {
                    startActivity(Intent.createChooser(anEmailIntent, "Send email.."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getActivity(), "There's no email client installed!", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(getActivity(), "Launching map...", Toast.LENGTH_SHORT).show();

        //Code will be populated with launching
        //Mukhtar's activity once his code
        //is merged successfully with
        //development as it is compatible
        //with the remainder of the project!
    }
}
