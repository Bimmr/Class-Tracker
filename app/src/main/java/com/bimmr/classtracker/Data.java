package com.bimmr.classtracker;

import android.content.Context;

import com.bimmr.classtracker.Database.SQLLiteManager;
import com.bimmr.classtracker.Database.SQLManager;

import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by Randy on 2017-09-19.
 */

public class Data {

    private static String currentEmail;
    private static String currentPassword;

    private static SQLLiteManager sqlLiteManager;

    public static void init(Context context){

        sqlLiteManager= new SQLLiteManager(context);

    }

    /**
     * Check if given email and password are valid
     *
     * @param email    the email
     * @param password the password
     * @return if email and password are valid
     */
    public static boolean isValid(String email, String password) {
        email = email.toLowerCase();
        return sqlLiteManager.get("User", "Email", email, "Password").equals(password);
    }

    public static void addUser(User user) {
        sqlLiteManager.set("User", "Email", user.getEmail(), "Password", user.getPassword());
        sqlLiteManager.set("User", "Email", user.getEmail(), "Name", user.getName());
        sqlLiteManager.set("User", "Email", user.getEmail(), "Birthday", user.getBirthdate());
    }

    /**
     * Check if the given email is already taken
     *
     * @param email the email
     * @return if the email is already taken
     */
    public static boolean isEmail(String email) {
        return sqlLiteManager.getAllKey("User", "Email").contains(email);
    }

    public static String getCurrentEmail() {
        return currentEmail;
    }

    public static void setCurrentEmail(String currentEmail) {
        Data.currentEmail = currentEmail;
    }

    public static String getCurrentPassword() {
        return currentPassword;
    }

    public static void setCurrentPassword(String currentPassword) {
        Data.currentPassword = currentPassword;
    }
}
