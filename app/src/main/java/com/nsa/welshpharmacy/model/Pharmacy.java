package com.nsa.welshpharmacy.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.IgnoreExtraProperties;
import com.nsa.welshpharmacy.services.LocationServices;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents all of the relevant information about a pharmacy
 * The fields correspond with the information contained in the db
 *
 * Parcelable example: http://www.vogella.com/tutorials/AndroidParcelable/article.html
 *
 * Created by c1714546 on 3/23/2018.
 */

@IgnoreExtraProperties
public class Pharmacy  implements Parcelable {

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){
        public Pharmacy createFromParcel(Parcel in){
            return new Pharmacy(in);
        }

        public Pharmacy[] newArray(int size){
            return new Pharmacy[size];
        }
    };

    public String id;
    public String email;
    public String name;
    public String phone;
    public String postcode;
    public Map<String, PharmacyServiceAvailability> services;
    public String website;

    public Pharmacy(String id,String email, String name, String phone, String postcode, Map<String,
            PharmacyServiceAvailability> services, String website) {
        this.id = id;
        this.email = email;
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

    public String getEmail(){ return email;}

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

    //Parcel constructor
    public Pharmacy(Parcel in) {
        id = in.readString();
        email = in.readString();
        name = in.readString();
        phone = in.readString();
        postcode = in.readString();
        //https://stackoverflow.com/questions/43471996/pass-parcelable-object-containing-map-between-activity/43478206#43478206
        services = new HashMap<String, PharmacyServiceAvailability>();
        in.readMap(services, PharmacyServiceAvailability.class.getClassLoader());
        website = in.readString();
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(id);
        dest.writeString(email);
        dest.writeString(name);
        dest.writeString(phone);
        dest.writeString(postcode);
        dest.writeMap(services);
        dest.writeString(website);
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
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", postcode='" + postcode + '\'' +
                ", services=" + services +
                ", website='" + website + '\'' +
                '}';
    }
}
