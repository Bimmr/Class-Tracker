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

    private String currentEmail;
    private String currentPassword;

    private SQLLiteManager sqlLiteManager;

    public Data() {

        sqlLiteManager = Manager.getSqlLiteManager();

        try {
            sqlLiteManager.createTable("User", "email TEXT", "password TEXT NOT NULL", "name TEXT", "birthday DATE", "CONSTRAINT user_pk PRIMARY KEY (email)");
            sqlLiteManager.createTable("Classes", "email TEXT", "name TEXT", "CONSTRAINT class_pk PRIMARY KEY (email, name)", "CONSTRAINT user_email_fk FOREIGN KEY (email) REFERENCES User (email)");
            sqlLiteManager.createTable("ClassSchedule", "classRowId INTEGER", "day TEXT", "start TIME", "end TIME", "CONSTRAINT classschedule_pk PRIMARY KEY (classRowId, day, start, end)");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            sqlLiteManager.insert("User", new String[]{"Email", "Password"}, new String[]{"bimmr@gmail.com", "password1"});
            sqlLiteManager.insert("User", new String[]{"Email", "Password"}, new String[]{"test@gmail.com", "password1"});
        } catch (Exception e) {
        }
        try {
            sqlLiteManager.insert("Classes", new String[]{"Email", "Name"}, new String[]{"bimmr@gmail.com", "PROG3210"});
            sqlLiteManager.insert("Classes", new String[]{"Email", "Name"}, new String[]{"bimmr@gmail.com", "PROG3170"});
            sqlLiteManager.insert("Classes", new String[]{"Email", "Name"}, new String[]{"test@gmail.com", "PROG3170"});
        } catch (Exception e1) {
        }
        try {
            sqlLiteManager.insert("ClassSchedule", new String[]{"classRowId", "day", "start", "end"}, new String[]{"1", "Tuesday", "10:00:00", "12:00:00"});
            sqlLiteManager.insert("ClassSchedule", new String[]{"classRowId", "day", "start", "end"}, new String[]{"1", "Thursday", "09:00:00", "11:00:00"});
            sqlLiteManager.insert("ClassSchedule", new String[]{"classRowId", "day", "start", "end"}, new String[]{"2", "Thursday", "12:00:00", "15:00:00"});
            sqlLiteManager.insert("ClassSchedule", new String[]{"classRowId", "day", "start", "end"}, new String[]{"3", "Thursday", "12:00:00", "15:00:00"});
        } catch (Exception e2) {
        }
        Log.d("USER TABLE",DatabaseUtils.dumpCursorToString(sqlLiteManager.runSQL("select * from user")));
        Log.d("CLASS TABLE",DatabaseUtils.dumpCursorToString(sqlLiteManager.runSQL("select * from classes")));
        Log.d("CLASS SCHEDULE TABLE",DatabaseUtils.dumpCursorToString(sqlLiteManager.runSQL("select * from classschedule")));
    }

    /**
     * Check if given email and password are valid
     *
     * @param email    the email
     * @param password the password
     * @return if email and password are valid
     */
    public boolean isValid(String email, String password) {
        if (email != null)
            email = email.toLowerCase();
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
    public void addUser(User user) {
        sqlLiteManager.set("User",
                new String[]{"Email", "Password", "Name", "Birthday"},
                new String[]{user.getEmail().toLowerCase(), user.getPassword(), user.getName(), user.getBirthdate()});
    }

    /**
     * Check if the given email is already taken
     *
     * @param email the email
     * @return if the email is already taken
     */
    public boolean isEmail(String email) {
        if (email != null)
            email = email.toLowerCase();
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

    public String getCurrentEmail() {
        return currentEmail;
    }

    public void setCurrentEmail(String currentEmail) {
        if (currentEmail != null)
            currentEmail = currentEmail.toLowerCase();
        this.currentEmail = currentEmail;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }
}
