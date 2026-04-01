package com.moodmusic;

import javax.servlet.http.*;
import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.*;

public class FavoriteServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        // ✅ Get user from session (not request)
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.html");
            return;
        }

        String username = (String) session.getAttribute("user");
        String song = request.getParameter("song");
        String artist = request.getParameter("artist");
        String url = request.getParameter("url");

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "INSERT INTO favorites(username, song, artist, url) VALUES(?, ?, ?, ?)")) {

            ps.setString(1, username);
            ps.setString(2, song);
            ps.setString(3, artist);
            ps.setString(4, url);

            ps.executeUpdate();

            response.setContentType("text/plain");
            response.getWriter().println("Added to favorites ❤️");

        } catch (SQLException e) {
            response.setContentType("text/plain");
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}