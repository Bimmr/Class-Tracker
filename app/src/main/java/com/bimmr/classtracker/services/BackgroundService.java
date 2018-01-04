package com.bimmr.classtracker.services;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.graphics.Color;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.os.HandlerThread;
import android.util.Log;

import com.bimmr.classtracker.Manager;
import com.bimmr.classtracker.R;
import com.bimmr.classtracker.Util;
import com.bimmr.classtracker.objects.Class;
import com.bimmr.classtracker.objects.ClassSchedule;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class BackgroundService extends IntentService {

    public BackgroundService() {

        super("BackgroundService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        startTimer();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        startTimer();
    }

    private int counter = 0;
    private Timer timer;
    private TimerTask timerTask;
    long oldTime = 0;

    public void startTimer() {
        //set a new Timer
        timer = new Timer();

        //initialize the TimerTask's job
        initializeTimerTask();

        //schedule the timer, to wake up every 1 second
        timer.schedule(timerTask, 1000, 1000 * 60 * 5); //
    }

    /**
     * it sets the timer to print the counter every x seconds
     */
    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
                Calendar current = Calendar.getInstance();

                String classes = "";
                for (Class c : Manager.getClassManager().getClasses()) {
                    boolean added = false;
                    for (ClassSchedule cs : c.getClassScheduales()) {

                        if (cs.getDayOfWeekVal() == (int) current.get(Calendar.DAY_OF_WEEK)) {
                            if (!added) {
                                added = true;
                                classes += "\n" + c.getName() + "\n";
                            }
                            classes += "    " + cs.getStartTime() + "-" + cs.getEndTime() + " " + cs.getRoom() + "\n";

                        }
                    }
                }

                if (classes.length() != 0)
                    Util.showNotification("Today's Schedule", classes);

                if (counter == Integer.MAX_VALUE)
                    counter = -1;
                Log.d("Background", "Background counter: " + (counter++));
            }
        };
    }

    /**
     * not needed
     */
    public void stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}

