package com.nsa.welshpharmacy.model;

/**
 * Created by c1714546 on 4/4/2018.
 */

public class MockPharmacy {

    private String name;
    private String phoneNumber;
    private String email;
    private String address;
    private String[] services;

    public MockPharmacy(String name, String phoneNumber, String email, String address, String... services) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.services = services;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String[] getServices() {
        return services;
    }


}
