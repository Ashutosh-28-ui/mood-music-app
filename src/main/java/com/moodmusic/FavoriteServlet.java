package com.moodmusic;

import javax.servlet.http.*;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

public class FavoriteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String song = request.getParameter("song");

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO favorites(username, song) VALUES(?, ?)")) {

            ps.setString(1, username);
            ps.setString(2, song);
            ps.executeUpdate();

            response.setContentType("text/plain");
            PrintWriter out = response.getWriter();
            out.println("success");

        } catch (SQLException e) {
            response.setContentType("text/plain");
            response.getWriter().println("error: " + e.getMessage());
        }
    }
}
