package com.bimmr.classtracker.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

import com.bimmr.classtracker.Manager;
import com.bimmr.classtracker.R;
import com.bimmr.classtracker.Util;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

/**
 * Created by Randy on 2018-01-03.
 */

public class FirebaseMessage extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage message){
        Util.showNotification("ClassTracker Firebase", message.getNotification().getBody());
    }

}
