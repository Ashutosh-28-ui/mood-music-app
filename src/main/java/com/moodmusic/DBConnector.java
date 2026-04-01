package com.moodmusic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {

    // ✅ ABSOLUTE PATH (VERY IMPORTANT)
    private static final String DB_URL =
            "jdbc:sqlite:C:/Users/HP1/MOOD-BASED music recommendation system/musicapp.db";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new SQLException("SQLite JDBC driver not found.", e);
        }
        return DriverManager.getConnection(DB_URL);
    }
}