

package com.moodmusic;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;


public class CallbackServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String code = request.getParameter("code");

        if (code != null && !code.isEmpty()) {
            // Redirect to /token servlet with the code as a query param
            response.sendRedirect("token?code=" + code);
        } else {
            response.getWriter().println("Authorization code not found.");
        }
    }
}
