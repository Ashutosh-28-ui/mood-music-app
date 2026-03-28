package com.moodmusic;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import org.json.JSONObject;

public class SpotifyTokenManager {
    private static String accessToken = null;
    private static long expiresAt = 0;

    private static final String CLIENT_ID = "d082f0d6c7f04822a7d316bfac62b259";
    private static final String CLIENT_SECRET = "4ef27baefaf54fceae8641c2be182f03"; // Replace this
    private static final String TOKEN_URL = "https://accounts.spotify.com/api/token";

    public static synchronized String getAccessToken() throws IOException {
        long now = System.currentTimeMillis();
        if (accessToken == null || now >= expiresAt) {
            generateNewToken();
        }
        return accessToken;
    }

    private static void generateNewToken() throws IOException {
        URL url = new URL(TOKEN_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Authorization", "Basic " + Base64.getEncoder().encodeToString(
                (CLIENT_ID + ":" + CLIENT_SECRET).getBytes()));
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        String body = "grant_type=client_credentials";
        try (OutputStream os = conn.getOutputStream()) {
            os.write(body.getBytes());
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder jsonBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonBuilder.append(line);
        }

        JSONObject json = new JSONObject(jsonBuilder.toString());
        accessToken = json.getString("access_token");
        int expiresIn = json.getInt("expires_in");
        expiresAt = System.currentTimeMillis() + (expiresIn - 60) * 1000L;
    }
}
