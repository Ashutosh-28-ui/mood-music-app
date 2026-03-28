package com.moodmusic;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

import org.json.JSONObject;


public class TokenGeneratorServlet extends HttpServlet {

    // Replace these with your actual credentials
    private static final String CLIENT_ID = "d082f0d6c7f04822a7d316bfac62b259";  // Your client ID
    private static final String CLIENT_SECRET = "4ef27baefaf54fceae8641c2be182f03";          // Your client secret
    private static final String REFRESH_TOKEN = "AQAvWc36wCKOKZVTc0JsNIbRUUvd2E6ICs2rOb6D4LEHR1wQz-CJ5cObPUDOmLrIb4tqA3EzzhX6oMXn2UNotDeI3-KfB2yVb5fJfwDc7oM_3WOMfvcFErnqlRQEwbIC1X8"; // Your refresh token

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accessToken = getAccessTokenFromRefreshToken();

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        if (accessToken != null) {
            out.println("{\"access_token\": \"" + accessToken + "\"}");
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println("{\"error\": \"Failed to generate access token\"}");
        }
    }

    private String getAccessTokenFromRefreshToken() {
        try {
            String authHeader = Base64.getEncoder().encodeToString((CLIENT_ID + ":" + CLIENT_SECRET).getBytes());

            URL url = new URL("https://accounts.spotify.com/api/token");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "Basic " + authHeader);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            String params = "grant_type=refresh_token&refresh_token=" + REFRESH_TOKEN;
            OutputStream os = conn.getOutputStream();
            os.write(params.getBytes());
            os.flush();
            os.close();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            JSONObject json = new JSONObject(response.toString());
            return json.getString("access_token");

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
