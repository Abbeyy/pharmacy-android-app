package com.nsa.welshpharmacy.model;

import com.google.firebase.database.IgnoreExtraProperties;
import java.util.Map;

/**
 * Represents all of the relevant information about a pharmacy
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

    public Pharmacy(String pharmacyName, String s, String s1, String s2, String s3, String s4, String s5, String s6){
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
