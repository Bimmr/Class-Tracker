package com.bimmr.classtracker.Database;

import android.util.Log;

import java.sql.*;
import java.util.*;


public class SQLManager {

    private MySQL mysql;

    /**
     * Create a mysql connection
     */
    public SQLManager(String database) {
        this.mysql = new MySQL(database);
    }


    public SQLManager(String hostname, String port, String database, String username, String password) {
        this.mysql = new MySQL(hostname, port, database, username, password);
    }

    /**
     * Closes connections
     */
    public void unload() {
        mysql.closeConnection();
    }

    public MySQL getMySQL() {
        return mysql;
    }

    public void createTableIfDoesntExist(String tableName, Column... columns) throws SQLException {
        String mySQLData = "";
        for (int i = 0; i != columns.length; i++) {
            Column c = columns[i];
            mySQLData += c.toString() + (i + 1 != columns.length ? ", " : "");
        }
        mysql.updateSQL("CREATE TABLE IF NOT EXISTS " + tableName + " (" + mySQLData + ");");
    }


    public Object get(String tableName, String keyCol, String keyVal, String columnName) {
        Object obj = 0;

        if (!mysql.hasOpenConnection())
            mysql.openConnection();

        Connection con = mysql.getConnection();

        ResultSet set = mysql.querySQL("SELECT " + columnName + " FROM " + tableName + " WHERE " + keyCol + " = '" + keyVal + "';");
        if (set != null) {
            try {
                if (set.next())
                    obj = set.getObject(columnName);

            } catch (SQLException e) {
                Log.d("info", "Error getting " + keyCol + " " + keyVal + " from column " + columnName + " from table " + tableName);
                e.printStackTrace();
            }

        }
        if (obj == null)
            obj = 0;

        mysql.closeConnection();
        return obj;

    }


    public ArrayList<String> getAllKey(String tableName, String keyCol) {
        ArrayList<String> players = new ArrayList<String>();
        if (!mysql.hasOpenConnection())
            mysql.openConnection();

        Connection con = mysql.getConnection();
        try {

            ResultSet set = mysql.querySQL("SELECT " + keyCol + " FROM `" + tableName + "`");

            while (set.next()) {
                if (set.getString(1) != null)
                    players.add(set.getString(1));
            }
        } catch (SQLException e) {
            Log.d("info", "Error getting " + keyCol + " from table " + tableName);
            e.printStackTrace();
        }
        //mysql.closeConnection();
        return players;
    }

    private void set(String tableName, String keyCol, String keyVal, String columnName, Object value) {

        mysql.updateSQL("INSERT INTO " + tableName + " (`" + keyCol + "`, `" + columnName + "`) VALUES ('" + keyVal + "', '" + value + "');");
    }

    public void update(String tableName, String keyCol, String keyVal, String columnName, Object value) {

        if (getAllKey(tableName, keyCol).contains(keyVal))
            mysql.updateSQL("UPDATE " + tableName + " SET " + columnName + "='" + value + "' WHERE " + keyCol + " ='" + keyVal + "';");
        else set(tableName, keyCol, keyVal, columnName, value);

    }

    public void alterTable(String table, Column column) throws SQLException {

        Connection c = null;
        PreparedStatement s = null;

        if (!mysql.hasOpenConnection())
            mysql.openConnection();

        c = mysql.getConnection();

        s = c.prepareStatement("ALTER TABLE " + table + " ADD " + column.getName() + " " + column.getDataType().toString() + "(" + column.getLength() + ");");

        s.executeUpdate();
    }

    public static enum DataType {
        INT, CHAR, VARCHAR, TINYINT, SMALLINT, BOOLEAN, BIGINT, FLOAT, DOUBLE;
    }

    public static class Column {

        private Integer length;
        private DataType type;
        private String name;

        public Column(String name, DataType type, Integer length) {
            this.name = name;
            this.type = type;
            this.length = length;
        }

        public Column(String name, DataType type, int length) {
            this.name = name;
            this.type = type;
            this.length = length;
        }

        /**
         * @return the type
         */
        public DataType getDataType() {
            return type;
        }

        /**
         * @return the length
         */
        public Integer getLength() {
            return length;
        }

        /**
         * @return the name
         */
        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            if (length == null) return name + " " + type.toString();
            return name + " " + type.toString() + "(" + length + ")";
        }
    }

    public class MySQL {

        private final String hostname, port, database, username, password;
        private Connection connection;

        public MySQL(String database) {
            this.hostname = null;
            this.port = null;
            this.database = database;
            this.username = null;
            this.password = null;
            this.connection = null;
        }

        public MySQL(String hostname, String port, String database, String username, String password) {
            this.hostname = hostname;
            this.port = port;
            this.database = database;
            this.username = username;
            this.password = password;
            this.connection = null;
        }

        /**
         * Checks if there is an open connection
         *
         * @return
         */
        public boolean hasOpenConnection() {
            try {
                return this.connection != null && !this.connection.isClosed();
            } catch (SQLException e) {
                Log.d("info",  "Error checking the MySQL Connection!");
                e.printStackTrace();
                return false;
            }
        }

        /**
         * Opens a connection
         */
        public void openConnection() {
            try {
                if (hostname == null) {
                    if (!hasOpenConnection()) {
                        Class.forName("org.sqlite.JDBC");
                        this.connection = DriverManager.getConnection("jdbc:sqlite:" + database + ".db");
                    }
                } else {
                    Class.forName("com.mysql.jdbc.Driver");
                    this.connection = DriverManager.getConnection("jdbc:mysql://" + this.hostname + ":" + this.port + "/" + this.database, this.username, this.password);
                }
            } catch (SQLException e) {
                Log.d("info", "Could not connect to MySQL server! because: " + e.getMessage());
            } catch (ClassNotFoundException e) {
                Log.d("info", "JDBC Driver not found!");
            }
        }

        /**
         * Closes the connection
         */
        public void closeConnection() {
            if (hasOpenConnection())
                try {
                    this.connection.close();
                    this.connection = null;
                } catch (SQLException e) {
                    Log.d("info", "Error closing the MySQL Connection!");
                    e.printStackTrace();
                }
        }

        /**
         * Gets the connection
         *
         * @return
         */
        public Connection getConnection() {
            return this.connection;
        }

        /**
         * Querys the SQL
         *
         * @param query
         * @return
         */
        public ResultSet querySQL(String query) {

            Connection c = null;
            PreparedStatement state = null;
            ResultSet set = null;

            if (!hasOpenConnection())
                openConnection();

            c = getConnection();

            try {
                state = c.prepareStatement(query);
            } catch (SQLException e) {
                Log.d("info", "Error creating the query statement!");
                e.printStackTrace();
            }

            try {
                set = state.executeQuery();
            } catch (SQLException e) {
                Log.d("info", "Error getting the ResultSet from the query!");
                e.printStackTrace();
            }

            return set;
        }

        /**
         * Updates the SQL
         */
        public void updateSQL(String update) {

            Connection c = null;
            PreparedStatement s = null;

            if (!hasOpenConnection())
                openConnection();

            c = getConnection();

            try {
                s = c.prepareStatement(update);
            } catch (SQLException e) {
                Log.d("info", "Error creating the update statement!");
                e.printStackTrace();
            }
            try {
                s.executeUpdate();
            } catch (SQLException e) {
                Log.d("info", "Error executing the update statement!");
                e.printStackTrace();
            }
            //closeConnection();
        }
    }
}