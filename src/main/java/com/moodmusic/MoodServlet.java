package com.moodmusic;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

@WebServlet("/mood")
public class MoodServlet extends HttpServlet {

    // ⚠️ Replace with your **latest Spotify Access Token**
    private static final String ACCESS_TOKEN = "AQDhsyT3gg-duTsgkozN8lFAXf99_pRv2bK0IjjhAWLMshAYIdgDis00RDicUYDQWzPXQhbiAgQBDxogh4-dCHY1KEUg8PkY1TM_addwe2_ZlmjqNoQ0J6Eu1gsyI4xZTz8uliRpeDmqvYu8Xd7xFxgP2nGcEiVX9Puh4uCjKGdqAjSLVONyfZCSzBTB5Jtp--FIaRwOw7x-SvmnamXfZrfhcL3_00-5BbI_a5LdXPsTOlH0BSjPsZU5i80e";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String query = request.getParameter("query");
        if (query == null || query.isEmpty()) {
            query = "happy"; // default
        }

        String apiUrl = "https://api.spotify.com/v1/search?q=" + query + "&type=track&limit=10";
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Authorization", "Bearer " + ACCESS_TOKEN);

        int status = conn.getResponseCode();
        response.setContentType("application/json");

        if (status == 200) {
            Scanner sc = new Scanner(conn.getInputStream());
            StringBuilder sb = new StringBuilder();
            while (sc.hasNext()) sb.append(sc.nextLine());
            sc.close();
            response.getWriter().print(sb.toString());
        } else {
            response.setStatus(502);
            response.getWriter().println("{\"error\":\"Spotify API error: " + status + "\"}");
        }
    }
}
