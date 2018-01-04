package com.bimmr.classtracker.activites;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.bimmr.classtracker.Manager;
import com.bimmr.classtracker.R;
import com.bimmr.classtracker.objects.ClassSchedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ScheduleManageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_manage);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        updateClassList();
        ListView listView = (ListView) findViewById(R.id.classListView);


    }

    public void updateClassList() {

        List<HashMap<String, String>> data = new ArrayList<>();
        for (com.bimmr.classtracker.objects.Class c : Manager.getClassManager().getClasses()) {
            String schedule = "";
            for(ClassSchedule cs : c.getClassScheduales())
                schedule += cs.getDayOfWeek() + "-" + cs.getStartTime() + "-" + cs.getEndTime() + " "+cs.getRoom()+"\n";
            HashMap<String, String> map = new HashMap<>();
            map.put("name", c.getName());
            map.put("schedule", schedule);
            data.add(map);
        }

        SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.activity_classschedule_manage_listview, new String[]{"name", "schedule"}, new int[]{R.id.classList_className, R.id.classList_classSchedule});

        ListView listView = (ListView) findViewById(R.id.classListView);
        listView.setAdapter(adapter);
    }
}