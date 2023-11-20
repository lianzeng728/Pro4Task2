package com.example.pro4task2.Model;

public class Player {
    private int id;
    private String firstName;
    private String lastName;
    private String position;
    private String team;

    public Player(int id, String firstName, String lastName, String position, String team) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.team = team;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }
    public String getTeam() { return team; }
    public void setTeam(String team) { this.team = team; }
}
