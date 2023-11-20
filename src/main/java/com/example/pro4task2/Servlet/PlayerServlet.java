package com.example.pro4task2.Servlet;

import com.example.pro4task2.Model.Player;
import com.example.pro4task2.Service.PlayerService;
import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "PlayerServlet", urlPatterns = {"/players"})
public class PlayerServlet extends HttpServlet {
    private final static Logger LOGGER = Logger.getLogger(PlayerServlet.class.getName());
    private PlayerService playerService = new PlayerService();
    private Gson gson = new Gson();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchTerm = request.getParameter("searchTerm");
        String playerIdParam = request.getParameter("playerId");

        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            List<Player> players = searchByName(searchTerm);
            sendResponse(players, response);
        } else if (playerIdParam != null && !playerIdParam.trim().isEmpty()) {
            Player player = searchById(playerIdParam, response);
            if (player == null) {
                return; // response is already handled in searchById method
            }
            sendResponse(Collections.singletonList(player), response);
        } else {
            LOGGER.log(Level.WARNING, "Search term or player ID is required");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Search term or player ID is required");
        }
    }

    private List<Player> searchByName(String searchTerm) {
        LOGGER.log(Level.INFO, "Fetching players with search term: {0}", searchTerm);
        return playerService.fetchPlayers(searchTerm);
    }

    private Player searchById(String playerIdParam, HttpServletResponse response) throws IOException {
        try {
            int playerId = Integer.parseInt(playerIdParam.trim());
            LOGGER.log(Level.INFO, "Fetching player with ID: {0}", playerId);
            return playerService.fetchPlayerById(playerId);
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, "Invalid player ID format: {0}", playerIdParam);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid player ID format");
            return null;
        }
    }

    private void sendResponse(List<Player> players, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(gson.toJson(players));
        out.flush();
    }
}
