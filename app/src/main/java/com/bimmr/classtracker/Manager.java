package com.bimmr.classtracker;

import android.content.Context;

import com.bimmr.classtracker.Database.SQLLiteManager;
import com.bimmr.classtracker.objects.ClassManager;

/**
 * Created by Randy on 2017-10-15.
 */

public class Manager {

    private static SQLLiteManager sqlLiteManager;
    private static Context context;
    private static Data data;

    public static ClassManager getClassManager() {
        return classManager;
    }

    private static ClassManager classManager;

    public static void init(Context context){
        Manager.context = context;
        Manager.sqlLiteManager = new SQLLiteManager();
        Manager.data = new Data();
    }


    public static SQLLiteManager getSqlLiteManager() {
        return Manager.sqlLiteManager;
    }

    public static void setSqlLiteManager(SQLLiteManager sqlLiteManager) {
        Manager.sqlLiteManager = sqlLiteManager;
    }

    public static Context getContext() {
        return Manager.context;
    }

    public static void setContext(Context context) {
        Manager.context = context;
    }

    public static Data getData() {
        return data;
    }

    public static void initClassManager() {
        classManager = new ClassManager();
    }
}
