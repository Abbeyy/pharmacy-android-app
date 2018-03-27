package com.nsa.welshpharmacy.mockingdata;

/**
 * Created by c1714546 on 3/23/2018.
 */

public class Pharmacy {
    private String name;
    private String phoneNumber;
    private String email;
    private String address;
    private String[] services;

    public Pharmacy(String name, String phoneNumber, String email, String address, String... services) {
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
