package com.bimmr.classtracker;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.util.Log;
import android.util.Pair;

import com.bimmr.classtracker.Database.SQLLiteManager;

import java.util.List;

/**
 * Created by Randy on 2017-09-19.
 */

public class Data {

    private static String currentEmail;
    private static String currentPassword;

    private static SQLLiteManager sqlLiteManager;

    public static void init(Context context) {

        sqlLiteManager = new SQLLiteManager(context);
        sqlLiteManager.createTable("User", "email TEXT PRIMARY KEY", "password TEXT NOT NULL", "name TEXT", "birthday DATE");
        if (!sqlLiteManager.tableContains("User", "Email", "bimmr@gmail.com", null))
            sqlLiteManager.insert("User", new String[]{"Email", "Password"}, new String[]{"bimmr@gmail.com", "password1"});
    }

    /**
     * Check if given email and password are valid
     *
     * @param email    the email
     * @param password the password
     * @return if email and password are valid
     */
    public static boolean isValid(String email, String password) {
        return sqlLiteManager.tableContains("User", "Email", email,
                new Pair[]{
                        new Pair<>("Email", email),
                        new Pair<>("Password", password)});
    }

    /**
     * Add a user to the database
     *
     * @param user - user profile to add to the database
     */
    public static void addUser(User user) {
        sqlLiteManager.set("User",
                new String[]{"Email", "Password", "Name", "Birthday"},
                new String[]{user.getEmail(), user.getPassword(), user.getName(), user.getBirthdate()});
    }

    /**
     * Check if the given email is already taken
     *
     * @param email the email
     * @return if the email is already taken
     */
    public static boolean isEmail(String email) {
        return sqlLiteManager.tableContains("User", "Email", email, null);
//        List<?> list = sqlLiteManager.get("User",
//                new String[]{"Email"},
//                new Pair[]{
//                        new Pair<>("Email", email)
//                });
//
//        if (list.isEmpty())
//            return false;
//
//        return true;
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
