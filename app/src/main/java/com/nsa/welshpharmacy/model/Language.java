package com.nsa.welshpharmacy.model;

/**
 * Represents the languages in which the services provided by the pharmacy can be conducted in
 *
 * Created by c1712480 on 29/03/2018.
 */

public class Language {
    public String id;
    public String description;

    public Language(String id, String description) {
        this.id = id;
        this.description = description;
    }

    public Language(){
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

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Language{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
