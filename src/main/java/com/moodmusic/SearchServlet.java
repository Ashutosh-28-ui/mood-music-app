package com.moodmusic;

import javax.servlet.http.*;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class SearchServlet extends HttpServlet {
    private static final String ACCESS_TOKEN = "YOUR_SPOTIFY_ACCESS_TOKEN"; // Replace with your token

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String query = request.getParameter("q");
        String apiUrl = "https://api.spotify.com/v1/search?q=" + query + "&type=track&limit=5";

        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Authorization", "Bearer " + ACCESS_TOKEN);

        Scanner sc = new Scanner(conn.getInputStream());
        StringBuilder sb = new StringBuilder();
        while (sc.hasNext()) {
            sb.append(sc.nextLine());
        }
        sc.close();

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.println(sb.toString());
    }
}
