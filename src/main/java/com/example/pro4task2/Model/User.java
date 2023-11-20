package com.example.pro4task2.Model;

import java.util.List;

public class User {
    private String username;
    private List<String> favoritePlayerIds;

    // Constructor
    public User(String username, List<String> favoritePlayerIds) {
        this.username = username;
        this.favoritePlayerIds = favoritePlayerIds;
    }

    // Getters and setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public List<String> getFavoritePlayerIds() { return favoritePlayerIds; }
    public void setFavoritePlayerIds(List<String> favoritePlayerIds) { this.favoritePlayerIds = favoritePlayerIds; }
}