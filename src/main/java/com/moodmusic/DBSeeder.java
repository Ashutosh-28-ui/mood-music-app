package com.moodmusic;

import java.sql.Connection;
import java.sql.Statement;

public class DBSeeder {
    public static void seed() {
        try (Connection conn = DBConnector.getConnection();
             Statement stmt = conn.createStatement()) {

            // Users table
            stmt.execute("CREATE TABLE IF NOT EXISTS users (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "username TEXT UNIQUE NOT NULL," +
                    "password TEXT NOT NULL" +
                    ");");

            // Favorites table
            stmt.execute("CREATE TABLE IF NOT EXISTS favorites (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "username TEXT NOT NULL," +
                    "song TEXT NOT NULL" +
                    ");");

            System.out.println("✅ Database seeded successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
