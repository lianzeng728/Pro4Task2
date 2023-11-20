package com.example.pro4task2.Service;

import com.example.pro4task2.Model.Player;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlayerService {

    private final static Logger LOGGER = Logger.getLogger(PlayerService.class.getName());

    public List<Player> fetchPlayers(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            LOGGER.log(Level.WARNING, "Search term is invalid.");
            return new ArrayList<>();
        }

        LOGGER.log(Level.INFO, "Fetching players with search term: {0}", searchTerm);
        System.out.println("Fetching players from the NBA API for term: " + searchTerm);

        List<Player> players = new ArrayList<>();

        try {
            String jsonResponseString = fetchDataFromApi(searchTerm);
            if (jsonResponseString == null) {
                LOGGER.log(Level.WARNING, "NBA API is unavailable.");
                return players;
            }
            JSONObject jsonResponse = new JSONObject(jsonResponseString);
            JSONArray jsonArray = jsonResponse.getJSONArray("data");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonPlayer = jsonArray.getJSONObject(i);
                Player player = new Player(
                        jsonPlayer.getInt("id"),
                        jsonPlayer.getString("first_name"),
                        jsonPlayer.getString("last_name"),
                        jsonPlayer.optString("position", "N/A"),
                        jsonPlayer.optJSONObject("team").optString("full_name", "Unknown Team")
                );
                players.add(player);
            }
            LOGGER.log(Level.INFO, "Players fetched successfully");
            System.out.println("Player data fetched successfully.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error fetching players: {0}", e.getMessage());
            System.out.println("Error fetching player data: " + e.getMessage());
        }
        return players;
    }

    private static String fetchDataFromApi(String searchTerm) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://free-nba.p.rapidapi.com/players?per_page=100&search=" + searchTerm))
                    .header("X-RapidAPI-Key", "6d7a17a01cmsh4695d0066a1e2cap17a209jsnc05c0ee50f41")
                    .header("X-RapidAPI-Host", "free-nba.p.rapidapi.com")
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error getting remote JSON: {0}", e.getMessage());
            System.out.println("Error getting remote JSON: " + e.getMessage());
            return null;
        }
    }

    public Player fetchPlayerById(int playerId) {
        LOGGER.log(Level.INFO, "Fetching player with ID: {0}", playerId);

        try {
            String jsonResponseString = fetchDataFromApiById(playerId);
            if (jsonResponseString == null) {
                LOGGER.log(Level.WARNING, "NBA API is unavailable for player ID: " + playerId);
                return null;
            }

            JSONObject jsonPlayer = new JSONObject(jsonResponseString);

            Player player = new Player(
                    jsonPlayer.getInt("id"),
                    jsonPlayer.getString("first_name"),
                    jsonPlayer.getString("last_name"),
                    jsonPlayer.optString("position", "N/A"),
                    jsonPlayer.getJSONObject("team").getString("full_name")
            );

            LOGGER.log(Level.INFO, "Player data fetched successfully for ID: " + playerId);
            return player;

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error fetching player data for ID: {0}", new Object[]{playerId, e.getMessage()});
            System.out.println("Error fetching player data for ID: " + playerId + ": " + e.getMessage());
            return null;
        }
    }

    private static String fetchDataFromApiById(int playerId) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://free-nba.p.rapidapi.com/players/" + playerId))
                    .header("X-RapidAPI-Key", "6d7a17a01cmsh4695d0066a1e2cap17a209jsnc05c0ee50f41")
                    .header("X-RapidAPI-Host", "free-nba.p.rapidapi.com")
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error getting remote JSON for player ID: {0}", new Object[]{playerId, e.getMessage()});
            System.out.println("Error getting remote JSON for player ID: " + playerId + ": " + e.getMessage());
            return null;
        }
    }
}
