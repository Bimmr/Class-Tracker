package com.bimmr.classtracker.activites;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.bimmr.classtracker.Manager;
import com.bimmr.classtracker.R;
import com.bimmr.classtracker.Util;
import com.bimmr.classtracker.objects.Class;
import com.bimmr.classtracker.objects.ClassSchedule;
import com.bimmr.classtracker.services.BackgroundService;
import com.bimmr.classtracker.services.FirebaseID;
import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends BaseActivity {


    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public int getNavigationMenuItemId() {
        return R.id.navigation_home;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startService(new Intent(this, BackgroundService.class));
        Log.d("Firebase Token", FirebaseInstanceId.getInstance().getToken());

        Manager.initClassManager();

        String classes = "";
        for (Class cls : Manager.getClassManager().getClasses()) {
            classes += cls.getName() + "\n";
            for (ClassSchedule cs : cls.getClassScheduales())
                classes += cs.getDayOfWeek() + "-" + cs.getStartTime() + "-" + cs.getEndTime() + " "+cs.getRoom()+"\n";
            classes += "\n\n";
        }
        if (classes.length() > 0)
            ((TextView) findViewById(R.id.main_classList)).setText(classes);


    }
}
