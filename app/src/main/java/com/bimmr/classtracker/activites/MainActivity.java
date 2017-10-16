package com.bimmr.classtracker.activites;

import android.os.Bundle;
import android.widget.TextView;

import com.bimmr.classtracker.Manager;
import com.bimmr.classtracker.R;
import com.bimmr.classtracker.objects.Class;
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
