package com.moodmusic;

import javax.servlet.http.*;
import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.*;

public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT * FROM users WHERE username=? AND password=?")) {

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // ✅ Create session
                HttpSession session = request.getSession();
                session.setAttribute("user", username);

                // ✅ Redirect to home
                response.sendRedirect("home.html");

            } else {
                // ❌ Redirect back with error
                response.sendRedirect("login.html?error=1");
            }

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}