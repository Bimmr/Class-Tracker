package com.bimmr.classtracker;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Randy on 2017-09-19.
 */

public class Util {

    /**
     * Hide the actionbar at the top of the page
     *
     * @param app the current activities instance
     */
    public static void hideActionBar(AppCompatActivity app) {

        // Hide UI first
        ActionBar actionBar = app.getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    /**
     * Switch current activity
     *
     * @param current the current activities instance
     * @param intent  the next activities class
     */
    public static void switchActivity(AppCompatActivity current, Class intent) {
        Intent myIntent = new Intent(current, intent);
        current.startActivity(myIntent);
    }

    public void hideKeyboard(AppCompatActivity app, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) app.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
