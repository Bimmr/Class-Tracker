package com.bimmr.classtracker;

import com.bimmr.classtracker.Database.SQLManager;

import java.util.Date;

/**
 * Created by Randy on 2017-10-01.
 */

public class User {
    public User(String name, String email, String password, Date birthdate) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.birthdate = birthdate;
    }

    private String name;
    private String email;
    private String password;
    private Date birthdate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

}
