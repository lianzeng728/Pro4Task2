package com.example.pro4task2.Service;

import com.example.pro4task2.Model.User;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserService {
    private static final String DEFAULT_USER = "defaultUser";
    private static MongoClient mongoClient;
    private static MongoCollection<Document> userCollection;
    private final static Logger LOGGER = Logger.getLogger(UserService.class.getName());

    static {
        try {
            mongoClient = MongoClients.create("mongodb+srv://alinazeng:tVx8Q6B4qWjcXdCE@cluster0.ini6vho.mongodb.net/?retryWrites=true&w=majority");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error establishing MongoDB connection: ", e);
            throw new RuntimeException("Failed to initialize MongoClient", e);
        }
    }

    public UserService() {
        MongoDatabase database = mongoClient.getDatabase("player");
        userCollection = database.getCollection("data");
        initializeDefaultUser();
    }

    private void initializeDefaultUser() {
        if (!usernameExists(DEFAULT_USER)) {
            Document newUser = new Document("username", DEFAULT_USER)
                    .append("favoritePlayers", Collections.emptyList());
            userCollection.insertOne(newUser);
            LOGGER.log(Level.INFO, "Default user created successfully");
        }
    }

    public boolean addFavoritePlayer(String playerId) {
        try {
            userCollection.updateOne(Filters.eq("username", DEFAULT_USER), Updates.addToSet("favoritePlayers", playerId));
            LOGGER.log(Level.INFO, "Favorite player " + playerId + " added successfully for user: " + DEFAULT_USER);
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error adding favorite player " + playerId + " for user: " + DEFAULT_USER, e);
            return false;
        }
    }

    public User getUser() {
        Document foundUser = userCollection.find(Filters.eq("username", DEFAULT_USER)).first();
        if (foundUser != null) {
            List<String> favoritePlayers = foundUser.getList("favoritePlayers", String.class);
            return new User(DEFAULT_USER, favoritePlayers);
        } else {
            LOGGER.log(Level.WARNING, "Default user not found");
            return null;
        }
    }

    private boolean usernameExists(String username) {
        return userCollection.find(Filters.eq("username", username)).first() != null;
    }
}
