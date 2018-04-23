package com.nsa.welshpharmacy.model;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Represents whether a service provided by the pharmacy is available or not
 *
 * Created by c1712480 on 30/03/2018.
 */

public class PharmacyServiceAvailability implements Serializable {

    public HashMap<String, Boolean> defaultAvailability;

    public PharmacyServiceAvailability(HashMap<String, Boolean> defaultAvailability) {
        this.defaultAvailability = defaultAvailability;
    }

    public PharmacyServiceAvailability(){

    }

    @Override
    public String toString() {
        return "PharmacyServiceAvailability{" +
                "defaultAvailability=" + defaultAvailability +
                '}';
    }
}
