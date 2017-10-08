package com.bimmr.classtracker.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = "DROP TABLE IF EXISTS User";
        sqLiteDatabase.execSQL(sql);
    }

    /**
     * Create a table
     *
     * @param table   - Table name
     * @param columns - Columns
     * @throws SQLException
     */
    public void createTable(String table, String... columns) throws SQLException {
        String cols = "";
        for (String c : columns)
            cols += c + (c.equals(columns[columns.length - 1]) ? "" : ",");
        String sql = "CREATE TABLE IF NOT EXISTS " + table + " (" + cols + ");";

        getWritableDatabase().execSQL(sql);
    }

    /**
     * Insert data into the database
     *
     * @param table   - Table name
     * @param cols    - Column names
     * @param colVals - Column values
     * @throws SQLException
     */
    public void insert(String table, String[] cols, String[] colVals) throws SQLException {
        String columns = "";

        for (String c : cols)
            columns += c + (c.equals(cols[cols.length - 1]) ? "" : ",");


        String vals = "";

        for (int i = 0; i < colVals.length; i++) {
            vals += "?";

            if (i != colVals.length - 1)
                vals += ",";

        }

        String sql = "INSERT INTO " + table + " (" + columns + ") VALUES (" + vals + ");";
        SQLiteStatement statement = getWritableDatabase().compileStatement(sql);
        statement.bindAllArgsAsStrings(colVals);
        statement.executeInsert();
    }

    /**
     * Insert/Update the table. Will update or insert based upon the need of the table
     *
     * @param table   - The table
     * @param cols    - The columns
     * @param colVals - The column values
     * @throws SQLException
     */
    public void set(String table, String[] cols, String[] colVals) throws SQLException {
        if (tableContains(table, cols[0], colVals[0], null))
            update(table, cols, colVals);
        else
            insert(table, cols, colVals);
    }

    /**
     * Check if the table contains something
     *
     * @param table      - The table
     * @param col        - The column
     * @param val        - The column's value
     * @param conditions - Where conditions
     * @return If the table contains the column, and the column's key
     */
    public boolean tableContains(String table, String col, String val, Pair<String, String>[] conditions) {
        List<HashMap<String, String>> rows = get(table, new String[]{col}, conditions);
        boolean hasKey = false;
        for (HashMap<String, String> row : rows)
            for (Map.Entry<String, String> entry : row.entrySet()) {
                if (entry.getKey().equalsIgnoreCase(col) && entry.getValue().equalsIgnoreCase(val)) {
                    hasKey = true;
                    break;
                }
            }
        return hasKey;
    }

    /**
     * Update a value
     *
     * @param table   - The table
     * @param cols    - The columns
     * @param colVals - The column values
     * @throws SQLException
     */
    public void update(String table, String[] cols, String[] colVals) throws SQLException {

        String update = "";

        //Move first element to the last (Columns)
        ArrayList<String> colsList = new ArrayList<>(Arrays.asList(cols));
        colsList.add(colsList.get(0));
        colsList.remove(0);
        cols = colsList.toArray(new String[colsList.size()]);

        //Move first element to the last (Column Values)
        ArrayList<String> colValList = new ArrayList<>(Arrays.asList(colVals));
        colValList.add(colValList.get(0));
        colValList.remove(0);
        colVals = colValList.toArray(new String[colValList.size()]);

        for (int i = 0; i < colVals.length-1; i++) {
            update += cols[i] + "=?";
            if (i != colVals.length - 2)
                update += ",";
        }

        String sql = "UPDATE " + table + " SET " + update + " WHERE " + cols[cols.length - 1] + "=?;";
        SQLiteStatement statement = getWritableDatabase().compileStatement(sql);
        statement.bindAllArgsAsStrings(colVals);
        statement.executeInsert();
    }

    /**
     * Get the results of the query
     * @param table - The table
     * @param cols - The columns
     * @param conditions - The Where conditions
     * @return a list of all the rows, with a map of the columns
     */
    public List<HashMap<String, String>> get(String table, String[]
            cols, Pair<String, String>[] conditions) {
        List<HashMap<String, String>> results = new ArrayList<>();
        String columns = "";

        String wheres = "";
        List<String> whereVals = new ArrayList<>();

        for (String c : cols)
            columns += c + (c.equals(cols[cols.length - 1]) ? "" : ",");

        String sql = "SELECT " + columns + " FROM " + table;

        if (conditions != null && conditions.length > 0) {
            sql += " WHERE ";
            String where = "";
            for (Pair<String, String> w : conditions) {
                whereVals.add(w.second);
                where += w.first + "=?" + (w.second.equals(conditions[conditions.length - 1].second) ? "" : " AND ");
            }
            sql += where;
        }
        sql += ";";

        String[] args = whereVals.toArray(new String[whereVals.size()]);
        Cursor cursor = getReadableDatabase().rawQuery(sql, args);

        while (cursor.moveToNext()) {
            HashMap<String, String> subset = new HashMap<>();
            for (int i = 0; i < cursor.getColumnCount(); i++)
                subset.put(cursor.getColumnName(i), cursor.getString(i));

            results.add(subset);
        }
        cursor.close();
        return results;
    }


    public Cursor runSQL(String sql) throws SQLException {
        return getWritableDatabase().rawQuery(sql, null);
    }
}
