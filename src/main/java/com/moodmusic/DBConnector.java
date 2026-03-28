package com.moodmusic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {

    private static final String DB_URL = "jdbc:sqlite:musicapp.db"; // database in project root

    // Get a database connection
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC"); // load SQLite driver
        } catch (ClassNotFoundException e) {
            throw new SQLException("SQLite JDBC driver not found.", e);
        }
        return DriverManager.getConnection(DB_URL);
    }
}
