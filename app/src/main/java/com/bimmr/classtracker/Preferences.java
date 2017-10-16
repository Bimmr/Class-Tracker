package com.bimmr.classtracker;

import android.content.SharedPreferences;
import android.util.Pair;

/**
 * Created by Randy on 2017-10-15.
 */

public class Preferences {

    private static SharedPreferences sp;

    /**
     * Get a value from shared preferences
     *
     * @param key - Key to get
     */
    public static String get(String key) {
        if (sp == null)
            sp = Manager.getContext().getSharedPreferences("ClassTracker", 0);
        return sp.getString(key, null);
    }

    /**
     * Set a value in shared preferences
     *
     * @param pair - Pairs of keys and values
     */
    public static void set(Pair<String, String>... pair) {
        if (sp == null)
            sp = Manager.getContext().getSharedPreferences("ClassTracker", 0);
        SharedPreferences.Editor editor = sp.edit();
        for (Pair<String, String> p : pair)
            editor.putString(p.first, p.second);
        editor.apply();
    }

    /**
     * Remove keys
     * @param key- key to remove
     */
    public static void remove(String... key) {
        if (sp == null)
            sp = Manager.getContext().getSharedPreferences("ClassTracker", 0);
        SharedPreferences.Editor editor = sp.edit();
        for (String k : key)
            editor.remove(k);
        editor.apply();
    }
}
