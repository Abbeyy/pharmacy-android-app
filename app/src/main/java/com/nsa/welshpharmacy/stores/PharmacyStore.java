package com.nsa.welshpharmacy.stores;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.nsa.welshpharmacy.model.Pharmacy;
import com.nsa.welshpharmacy.model.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Creates a list of pharmacies as loaded in from the database
 * Created by c1712480 on 29/03/2018.
 */

public class PharmacyStore {

    private List<Pharmacy> pharmacies = null;

    /*
    public List<Pharmacy> getPharmacies(){
        if(pharmacies == null){
            pharmacies = FirebaseServices.loadPharmacies();
        }
        return pharmacies;
    }
    */

    /**
     * Returns a sorted map of pharmacy objects with their corresponding distance to the user location
     * Nearest distance first
     * Distance must be computed to added to the map
     * @param context
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public Map<Pharmacy, Float> sortPharmaciesByLocation(Context context){
        Map<Pharmacy, Float> userDistanceToPharmacies = new HashMap<>();
        /*
        for (Pharmacy pharmacy : this.getPharmacies()){
            if(LocationServices.getUserLocation() != null) {
                Float distance = LocationServices.getUserDistanceToPharmacy(context, pharmacy);
                userDistanceToPharmacies.put(pharmacy, distance);
            }
        }
        */
        Map<Pharmacy, Float> sortedPharmaciesMap = Utils.sortByValue(userDistanceToPharmacies);
        return sortedPharmaciesMap;
    }
}
