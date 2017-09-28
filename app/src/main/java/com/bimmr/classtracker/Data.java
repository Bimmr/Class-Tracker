package com.bimmr.classtracker;

import java.util.HashMap;

/**
 * Created by Randy on 2017-09-19.
 */

public class Data {
    private static String currentEmail;
    private static String currentPassword;

    private static HashMap<String, String> userInfo = new HashMap<>();

    /**
     * Init userInfo
     */
    public static void init() {
        userInfo.put("bimmr@gmail.com", "password");
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
        return userInfo.containsKey(email) && userInfo.get(email).equals(password);
    }

    /**
     * Add a user
     *
     * @param email    the email
     * @param password the password
     */
    public static void addUser(String email, String password) {
        userInfo.put(email.toLowerCase(), password);
    }

    /**
     * Check if the given email is already taken
     *
     * @param email the email
     * @return if the email is already taken
     */
    public static boolean isEmail(String email) {
        return userInfo.containsKey(email.toLowerCase());
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
