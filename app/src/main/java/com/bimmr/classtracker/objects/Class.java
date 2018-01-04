package com.bimmr.classtracker.objects;

import android.util.Pair;

import com.bimmr.classtracker.Manager;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Randy on 2017-10-15.
 */

public class Class {

    private int id;

    private String name;

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public ArrayList<ClassSchedule> getClassScheduales() {
        return classScheduales;
    }

    private ArrayList<ClassSchedule> classScheduales = new ArrayList<>();

    public Class(int id, String name) {
        this.id = id;
        this.name = name;
        loadSchedule();
    }

    public void loadSchedule() {

        classScheduales.clear();
        List<HashMap<String, String>> schedules = Manager.getSqlLiteManager().get("ClassSchedule", new String[]{"rowid", "*"}, new Pair[]{new Pair("classRowId", "" + this.id)});
        for (HashMap<String, String> s : schedules) {
            ClassSchedule cs = new ClassSchedule(Integer.parseInt(s.get("rowid")), s.get("day"), Time.valueOf(s.get("start")), Time.valueOf(s.get("end")), s.get("room"));
            classScheduales.add(cs);
        }
    }

    public void updateToDB() {

        for (ClassSchedule cs : classScheduales)
            cs.updateToDB();
    }


}
