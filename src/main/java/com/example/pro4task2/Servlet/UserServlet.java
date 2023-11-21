package com.example.pro4task2.Servlet;

import com.example.pro4task2.Model.User;
import com.example.pro4task2.Service.UserService;
import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "UserServlet", urlPatterns = {"/user"})
public class UserServlet extends HttpServlet {
    private final static Logger LOGGER = Logger.getLogger(UserServlet.class.getName());
    private UserService userService = new UserService();
    private Gson gson = new Gson();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle adding a favorite player
        String playerId = request.getParameter("playerId");

        if (playerId == null) {
            LOGGER.log(Level.WARNING, "Player ID is required for POST request");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Player ID is required");
            return;
        }

        boolean success = userService.addFavoritePlayer(playerId);

        if (success) {
            LOGGER.log(Level.INFO, "Favorite player added successfully");
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            LOGGER.log(Level.SEVERE, "Could not add favorite player");
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Could not add favorite player");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Fetch the default user's information
        User user = userService.getUser();

        if (user == null) {
            LOGGER.warning("User not found");
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found");
            return;
        }

        sendResponse(user, response);
    }

    private void sendResponse(User user, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(gson.toJson(user));
        out.flush();
    }
}
