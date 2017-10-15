package com.bimmr.classtracker.objects;

import android.util.Log;
import android.util.Pair;

import com.bimmr.classtracker.Data;
import com.bimmr.classtracker.Manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Randy on 2017-10-15.
 */

public class ClassManager {

    public ArrayList<Class> getClasses() {
        return classes;
    }

    private ArrayList<Class> classes = new ArrayList<>();

    public ClassManager() {
        List<HashMap<String, String>> classes = Manager.getSqlLiteManager().get("Classes", new String[]{"rowid", "*"}, new Pair[]{new Pair("email", Manager.getData().getCurrentEmail())});
        for (HashMap<String, String> c : classes) {
            Class clas = new Class(Integer.parseInt(c.get("rowid")), c.get("name"));
            this.classes.add(clas);
        }
    }
}
