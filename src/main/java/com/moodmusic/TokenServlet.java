package com.moodmusic;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Base64;
import org.json.JSONObject;

@WebServlet("/token")
public class TokenServlet extends HttpServlet {

    private static final String CLIENT_ID = "d082f0d6c7f04822a7d316bfac62b259";
    private static final String CLIENT_SECRET = "4ef27baefaf54fceae8641c2be182f03"; // Replace this with your actual client secret
    private static final String REDIRECT_URI = "http://127.0.0.1:8080/callback"; // Should match what you set in Spotify dashboard

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");

        if (code == null || code.isEmpty()) {
            response.getWriter().write("Missing authorization code.");
            return;
        }

        String url = "https://accounts.spotify.com/api/token";

        String params = "grant_type=authorization_code" +
                "&code=" + URLEncoder.encode(code, "UTF-8") +
                "&redirect_uri=" + URLEncoder.encode(REDIRECT_URI, "UTF-8");

        String basicAuth = Base64.getEncoder().encodeToString((CLIENT_ID + ":" + CLIENT_SECRET).getBytes());

        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Authorization", "Basic " + basicAuth);
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        try (OutputStream os = conn.getOutputStream()) {
            os.write(params.getBytes());
        }

        int status = conn.getResponseCode();
        InputStream is = (status >= 200 && status < 300) ? conn.getInputStream() : conn.getErrorStream();

        StringBuilder result = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        }

        JSONObject json = new JSONObject(result.toString());

        if (json.has("access_token")) {
            String accessToken = json.getString("access_token");
            String refreshToken = json.optString("refresh_token", ""); // Optional

            response.setContentType("text/plain");
            response.getWriter().println("Access Token: " + accessToken);
            response.getWriter().println("Refresh Token: " + refreshToken);
        } else {
            response.setContentType("application/json");
            response.getWriter().println(json.toString(2));
        }
    }
}
