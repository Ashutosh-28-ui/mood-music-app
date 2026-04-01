package com.moodmusic;

import javax.servlet.http.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.*;

@WebServlet("/getFavorites")
public class GetFavoritesServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ✅ Get user from session
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.html");
            return;
        }

        String username = (String) session.getAttribute("user");

        response.setContentType("application/json");
        StringBuilder json = new StringBuilder();
        json.append("[");

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT song, artist, url FROM favorites WHERE username=?")) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            boolean first = true;

            while (rs.next()) {
                if (!first) json.append(",");
                first = false;

                json.append("{")
                        .append("\"song\":\"").append(rs.getString("song")).append("\",")
                        .append("\"artist\":\"").append(rs.getString("artist")).append("\",")
                        .append("\"url\":\"").append(rs.getString("url")).append("\"")
                        .append("}");
            }

            json.append("]");
            response.getWriter().write(json.toString());

        } catch (SQLException e) {
            response.getWriter().write("[]");
        }
    }
}