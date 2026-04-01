package com.moodmusic;

import javax.servlet.http.*;
import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.*;

public class SignupServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try (Connection conn = DBConnector.getConnection()) {

            // ✅ Check if user already exists
            PreparedStatement check = conn.prepareStatement(
                    "SELECT * FROM users WHERE username=?");
            check.setString(1, username);
            ResultSet rs = check.executeQuery();

            if (rs.next()) {
                // ❌ User exists
                response.sendRedirect("signup.html?error=exists");
                return;
            }

            // ✅ Insert new user
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO users(username, password) VALUES(?, ?)");

            ps.setString(1, username);
            ps.setString(2, password);
            ps.executeUpdate();

            // ✅ Redirect to login after signup
            response.sendRedirect("login.html?signup=success");

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}