package com.nsa.welshpharmacy.model;

/**
 * Represents a service provided by a pharmacy eg. A Flu Jab
 *
 * Created by c1712480 on 29/03/2018.
 */

public class PharmacyService {
    public String id;
    public String name;
    public String description;

    public PharmacyService(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public PharmacyService(){
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

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "PharmacyService{" +
                "id='" + id + '\'' +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
