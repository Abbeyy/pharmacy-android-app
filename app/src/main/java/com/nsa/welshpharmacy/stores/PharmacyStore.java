package com.nsa.welshpharmacy.stores;

import com.nsa.welshpharmacy.model.Pharmacy;
import com.nsa.welshpharmacy.services.FirebaseServices;

import java.util.List;

/**
 * Creates a list of pharmacies as loaded in from the database
 * Created by c1712480 on 29/03/2018.
 */

public class PharmacyStore {

    private List<Pharmacy> pharmacies = null;

    public List<Pharmacy> getPharmacies(){
        if(pharmacies == null){
            pharmacies = FirebaseServices.loadPharmacies();
        }
        return pharmacies;
    }
}
