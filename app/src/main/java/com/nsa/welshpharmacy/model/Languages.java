package com.nsa.welshpharmacy.model;

/**
 * Created by c1712480 on 29/03/2018.
 */

public class Languages {
    public String id;
    public String description;

    public Languages(String id, String description) {
        this.id = id;
        this.description = description;
    }

    public Languages(){
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
        return "Languages{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
