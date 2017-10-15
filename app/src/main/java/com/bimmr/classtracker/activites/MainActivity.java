package com.bimmr.classtracker.activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.bimmr.classtracker.Manager;
import com.bimmr.classtracker.R;
import com.bimmr.classtracker.objects.Class;
import com.bimmr.classtracker.objects.ClassManager;
import com.bimmr.classtracker.objects.ClassSchedule;

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

        Manager.initClassManager();

        String classes = "";
        for (Class cls : Manager.getClassManager().getClasses()) {
            classes += cls.getName() + "\n";
            for (ClassSchedule cs : cls.getClassScheduales())
                classes += cs.getDayOfWeek() + "-" + cs.getStartTime() + "-" + cs.getEndTime() + "\n";
            classes += "\n\n";
        }
        if (classes.length() > 0)
            ((TextView) findViewById(R.id.main_classList)).setText(classes);

    }
}
