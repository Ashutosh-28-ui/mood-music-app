package com.moodmusic;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestDBQuery {
    public static void main(String[] args) {
        try (Connection conn = DBConnector.getConnection();
             Statement stmt = conn.createStatement()) {

            String query = "SELECT name FROM sqlite_master WHERE type='table';";
            ResultSet rs = stmt.executeQuery(query);

            System.out.println("📋 Tables in database:");
            while (rs.next()) {
                System.out.println("- " + rs.getString("name"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
