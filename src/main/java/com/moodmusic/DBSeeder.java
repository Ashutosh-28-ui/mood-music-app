package com.moodmusic;

import java.sql.Connection;
import java.sql.Statement;

public class DBSeeder {
    public static void main(String[] args) {
        try (Connection conn = DBConnector.getConnection();
             Statement stmt = conn.createStatement()) {

            // ✅ Users table
            stmt.execute("CREATE TABLE IF NOT EXISTS users (" +
                    "username TEXT PRIMARY KEY, " +
                    "password TEXT)");

            // ✅ Favorites table
            stmt.execute("CREATE TABLE IF NOT EXISTS favorites (" +
                    "username TEXT, " +
                    "song TEXT, " +
                    "artist TEXT, " +
                    "url TEXT)");

            System.out.println("Tables created successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}