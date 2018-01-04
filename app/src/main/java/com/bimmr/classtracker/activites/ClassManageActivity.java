package com.bimmr.classtracker.activites;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.bimmr.classtracker.Manager;
import com.bimmr.classtracker.Preferences;
import com.bimmr.classtracker.R;
import com.bimmr.classtracker.activites.startup.StartActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ClassManageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_manage);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        updateClassList();
        ListView listView = (ListView) findViewById(R.id.classListView);
        listView.setOnItemClickListener((parent, view, position, id) -> {

            if (id == parent.getCount() - 1) {
                Log.d("info", "Make new class");
                EditText cNameText = new EditText(this);

                cNameText.setHint("Class Name");

                new AlertDialog.Builder(this)
                        .setTitle("Add a new class")
                        .setMessage("Enter the classes name")
                        .setView(cNameText)
                        .setPositiveButton("Add", (dialog, whichButton) -> {
                            String cName = cNameText.getText().toString();
                            Log.d("info", cName);
                            Manager.getSqlLiteManager().insert("Classes", new String[]{"Email", "Name"}, new String[]{Preferences.get("email"), cName});
                            Manager.getClassManager().updateClasses();
                            updateClassList();
                        })
                        .setNegativeButton("Cancel", (dialog, whichButton) -> {

                        })
                        .show();
            } else {
                Log.d("info", "Delete class");
                com.bimmr.classtracker.objects.Class c = Manager.getClassManager().getClasses().get((int) id);
                new AlertDialog.Builder(this)
                        .setTitle("Delete Class")
                        .setMessage("Do you really want to delete this class?")
                        .setPositiveButton(android.R.string.yes, (dialog, whichButton) -> {
                            if (Manager.getSqlLiteManager().tableContains("ClassSchedule", "classRowId", "" + c.getId(), null)) {
                                new AlertDialog.Builder(this)
                                        .setTitle("Delete Class")
                                        .setMessage("Do you want to delete the schedules relating to this class?")
                                        .setPositiveButton(android.R.string.yes, (dialog2, whichButton2) -> {
                                            Log.d("info", "" + c.getId());
                                            Manager.getSqlLiteManager().delete("ClassSchedule", new Pair[]{new Pair("classRowId", "" + c.getId())});
                                            Manager.getSqlLiteManager().delete("Classes", new Pair[]{new Pair("rowId", "" + c.getId())});

                                            Manager.getClassManager().updateClasses();
                                            updateClassList();
                                        })
                                        .setNegativeButton(android.R.string.no, (dialog2, whichButton2) -> {
                                        }).show();
                            } else {
                                Manager.getSqlLiteManager().delete("Classes", new Pair[]{new Pair("rowId", "" + c.getId())});
                                Manager.getClassManager().updateClasses();
                                updateClassList();
                            }
                        })
                        .setNegativeButton(android.R.string.no, (dialog, whichButton) -> {
                        }).show();
            }

        });

    }

    public void updateClassList() {

        List<HashMap<String, String>> data = new ArrayList<>();
        for (com.bimmr.classtracker.objects.Class c : Manager.getClassManager().getClasses()) {
            HashMap<String, String> map = new HashMap<>();
            map.put("name", c.getName());
            data.add(map);
        }

        HashMap<String, String> addMap = new HashMap<>();
        addMap.put("name", "Add Class");
        data.add(addMap);

        SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.activity_class_manage_listview, new String[]{"name"}, new int[]{R.id.classList_className,});

        ListView listView = (ListView) findViewById(R.id.classListView);
        listView.setAdapter(adapter);
    }

}
