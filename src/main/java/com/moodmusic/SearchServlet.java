package com.moodmusic;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.net.*;

public class SearchServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ✅ Encoding
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        String query = request.getParameter("query");

        if (query == null || query.trim().isEmpty()) {
            response.getWriter().write("{\"error\":\"No query provided\"}");
            return;
        }

        try {
            // ✅ Get OAuth token
            String token = SpotifyTokenManager.getAccessToken();

            // ❌ If user has NOT connected Spotify
            if (token == null) {
                response.getWriter().write("{\"error\":\"Please connect Spotify first\"}");
                return;
            }

            // ✅ Spotify API URL
            String apiUrl = "https://api.spotify.com/v1/search?q=" +
                    URLEncoder.encode(query, "UTF-8") +
                    "&type=track&limit=10";

            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + token);

            int responseCode = conn.getResponseCode();

            // 🔥 Handle token expired / invalid
            if (responseCode == 401) {
                response.getWriter().write("{\"error\":\"Session expired. Please reconnect Spotify.\"}");
                return;
            }

            // 🔥 Handle other API errors
            if (responseCode != 200) {
                BufferedReader errorReader = new BufferedReader(
                        new InputStreamReader(conn.getErrorStream()));
                StringBuilder error = new StringBuilder();
                String line;

                while ((line = errorReader.readLine()) != null) {
                    error.append(line);
                }

                response.getWriter().write("{\"error\":\"" + error.toString() + "\"}");
                return;
            }

            // ✅ Read success response
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));

            StringBuilder result = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            // ✅ Return JSON
            response.getWriter().write(result.toString());

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("{\"error\":\"Server error\"}");
        }
    }
}