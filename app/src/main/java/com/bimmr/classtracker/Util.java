package com.bimmr.classtracker;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Randy on 2017-09-19.
 */

public class Util {


    /**
     * Show Notifications
     *
     * @param title   the title of the notification
     * @param message the message in the notification
     */
    public static void showNotification(String title, String message) {

        Notification notification = new NotificationCompat.Builder(Manager.getContext())
                .setSmallIcon(R.mipmap.icon)
                .setContentTitle(title)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setAutoCancel(true)
                .build();
        NotificationManager manager = (NotificationManager) Manager.getContext().getSystemService(Manager.getContext().NOTIFICATION_SERVICE);
        manager.notify(0, notification);

    }

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

    public void hideKeyboard(AppCompatActivity app, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) app.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
