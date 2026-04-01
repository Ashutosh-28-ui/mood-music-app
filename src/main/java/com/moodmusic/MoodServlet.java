package com.moodmusic;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;


public class MoodServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String mood = request.getParameter("mood");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<h2>Songs for mood: " + mood + "</h2>");

        if (mood.equals("happy")) {
            out.println("<p>Happy Song 1</p>");
            out.println("<p>Happy Song 2</p>");
        } else if (mood.equals("sad")) {
            out.println("<p>Sad Song 1</p>");
            out.println("<p>Sad Song 2</p>");
        } else {
            out.println("<p>Angry Song 1</p>");
        }
    }
}