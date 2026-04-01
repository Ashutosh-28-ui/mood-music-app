package com.moodmusic;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.*;
import java.net.*;
import org.json.JSONObject;


public class CallbackServlet extends HttpServlet {

    private static final String CLIENT_ID = "YOUR_CLIENT_ID";
    private static final String CLIENT_SECRET = "YOUR_CLIENT_SECRET";
    private static final String REDIRECT_URI = "http://localhost:8080/mood-music-app-1.0/callback";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String code = request.getParameter("code");

        if (code == null) {
            response.getWriter().write("❌ No code received");
            return;
        }

        try {
            // 🔥 Step 1: Prepare request
            URL url = new URL("https://accounts.spotify.com/api/token");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            String params = "grant_type=authorization_code" +
                    "&code=" + code +
                    "&redirect_uri=" + URLEncoder.encode(REDIRECT_URI, "UTF-8") +
                    "&client_id=" + CLIENT_ID +
                    "&client_secret=" + CLIENT_SECRET;

            // 🔥 Step 2: Send request
            OutputStream os = conn.getOutputStream();
            os.write(params.getBytes());
            os.flush();

            // 🔥 Step 3: Read response
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));

            StringBuilder result = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                result.append(line);
            }

            // 🔥 Step 4: Extract token
            JSONObject json = new JSONObject(result.toString());
            String accessToken = json.getString("access_token");

            // 🔥 Step 5: STORE TOKEN (IMPORTANT)
            SpotifyTokenManager.setAccessToken(accessToken);

            // 🔥 Step 6: Redirect back to app
            response.sendRedirect("home.html");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("❌ Error getting token");
        }
    }
}