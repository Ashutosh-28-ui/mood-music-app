package com.moodmusic;

public class SpotifyTokenManager {

    private static String accessToken = null;

    public static void setAccessToken(String token) {
        accessToken = token;
    }

    public static String getAccessToken() {
        return accessToken;
    }
}