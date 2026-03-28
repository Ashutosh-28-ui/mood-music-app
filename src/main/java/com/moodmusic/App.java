package com.moodmusic;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

// Import your servlets
import com.moodmusic.MoodServlet;
import com.moodmusic.SearchServlet;
import com.moodmusic.LoginServlet;
import com.moodmusic.FavoriteServlet;
import com.moodmusic.SignupServlet;

public class App {

    public static void main(String[] args) {
        try {
            // Start Jetty server on port 8080
            Server server = new Server(8080);

            // Set up context for servlets
            ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
            handler.setContextPath("/");

            // Register servlets
            handler.addServlet(new ServletHolder(new MoodServlet()), "/mood");
            handler.addServlet(new ServletHolder(new SearchServlet()), "/search");
            handler.addServlet(new ServletHolder(new LoginServlet()), "/login");
            handler.addServlet(new ServletHolder(new FavoriteServlet()), "/favorite");
            handler.addServlet(new ServletHolder(new SignupServlet()), "/signup");

            server.setHandler(handler);

            // Start the server
            server.start();
            System.out.println("🚀 Server started at http://localhost:8080");
            server.join();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
