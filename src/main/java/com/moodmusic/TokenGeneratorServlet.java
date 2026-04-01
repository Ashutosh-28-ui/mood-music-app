package com.moodmusic;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.net.URLEncoder;

@WebServlet("/generate-token")
public class TokenGeneratorServlet extends HttpServlet {

    private static final String CLIENT_ID = "d082f0d6c7f04822a7d316bfac62b259";
    private static final String REDIRECT_URI = "http://localhost:8080/mood-music-app-1.0/callback";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String url = "https://accounts.spotify.com/authorize?" +
                "client_id=" + CLIENT_ID +
                "&response_type=code" +
                "&redirect_uri=" + URLEncoder.encode(REDIRECT_URI, "UTF-8") +
                "&scope=user-read-private";

        response.sendRedirect(url);
    }
}