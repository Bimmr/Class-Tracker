package com.bimmr.classtracker.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by Randy on 2017-10-05.
 */

public class SQLLiteManager extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE = "ClassTracker.db";


    public SQLLiteManager(Context context) {
        super(context, DATABASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        createTable("User", "email TEXT PRIMARY KEY", "password TEXT NOT NULL", "name TEXT NOT NULL", "birthday DATE NOT NULL");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void createTable(String table, String... columns) {
        String cols = "";
        for (String c : columns)
            cols += c + (c.equals(columns[columns.length - 1]) ? "" : ",");
        String sql = "CREATE TABLE " + table + " (" + cols + ");";

        getWritableDatabase().execSQL(sql);
    }

    public void set(String table, String key, String keyVal, String col, String colVal) {
        String sql = "INSERT INTO " + table + "(" + key + ", " + col + ") VALUES (?, ?) ON DUPLICATE KEY UPDATE " + key + "=?";
        SQLiteStatement statement = getWritableDatabase().compileStatement(sql);
        statement.bindAllArgsAsStrings(new String[]{keyVal, colVal, keyVal});
        statement.executeInsert();
    }

    public HashMap<String, Object> get(String table, String key, String keyVal, String... columns) {
        String cols = "";
        for (String c : columns)
            cols += c + (c.equals(columns[columns.length - 1]) ? "" : ",");
        String sql = "SELECT " + cols + " FROM " + table + " WHERE " + key + "= ?";

        HashMap map = new HashMap();
        Cursor cursor = getReadableDatabase().rawQuery(sql, new String[]{keyVal});
        for (int i = 0; i < cursor.getCount(); i++)
            map.put(cursor.getColumnName(i), cursor.getString(i));
        cursor.close();
        return null;
    }
}
