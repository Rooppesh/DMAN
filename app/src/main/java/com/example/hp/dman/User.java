package com.example.hp.dman;

/**
 * Created by HP on 15-01-2018.
 */

public class User
{
    String name;
    String password;
    String contact;
    String address;
    String emailid;
    String pincode;

    public User(String name, String password, String contact, String address, String emailid, String pincode) {
        this.name = name;
        this.password = password;
        this.contact = contact;
        this.address = address;
        this.emailid = emailid;
        this.pincode = pincode;
    }
}
