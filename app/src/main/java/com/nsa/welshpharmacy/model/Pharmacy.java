package com.nsa.welshpharmacy.model;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.IgnoreExtraProperties;
import com.nsa.welshpharmacy.services.LocationServices;

import java.io.IOException;
import java.util.Map;

/**
 * Represents all of the relevant information about a pharmacy
 * The fields correspond with the information contained in the db
 *
 * Created by c1714546 on 3/23/2018.
 */

@IgnoreExtraProperties
public class Pharmacy {
    public String id;
    public String name;
    public String phone;
    public String postcode;
    public Map<String, PharmacyServiceAvailability> services;
    public String website;

    public Pharmacy(String id, String name, String phone, String postcode, Map<String, PharmacyServiceAvailability> services, String website) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.postcode = postcode;
        this.services = services;
        this.website = website;
    }

    public Pharmacy(){
        //Default constructor
        //Reflection
    }

    //This is only for use when reading from Firebase
    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getPostcode() {
        return postcode;
    }

    public Map<String, PharmacyServiceAvailability> getServices() {
        return services;
    }

    public String getWebsite() {
        return website;
    }

    /**
     *  Converts the postcode of a pharmacy into LatLng type
     */
    public LatLng getPharmacyLatLng(Context context){
        LatLng pharmacyLatLng = null;
        try {
            pharmacyLatLng = LocationServices.loadPhoneLocationViaPostcode(context, this.getPostcode());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pharmacyLatLng;
    }

    @Override
    public String toString() {
        return "Pharmacy{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", postcode='" + postcode + '\'' +
                ", services=" + services +
                ", website='" + website + '\'' +
                '}';
    }
}
