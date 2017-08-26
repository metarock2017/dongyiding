package org.redrock.Model;

import com.google.gson.Gson;

public class Room {
    private int id;
    private int max;
    private int playerId;
    private String wordOne;
    private String wordTwo;

    public Room(int id, int max, int playerId, String wordOne, String wordTwo) {
        this.id = id;
        this.max = max;
        this.playerId = playerId;
        this.wordOne = wordOne;
        this.wordTwo = wordTwo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getWordOne() {
        return wordOne;
    }

    public void setWordOne(String wordOne) {
        this.wordOne = wordOne;
    }

    public String getWordTwo() {
        return wordTwo;
    }

    public void setWordTwo(String wordTwo) {
        this.wordTwo = wordTwo;
    }

    public static void main(String[] args) {
        Gson gson = new Gson();

        Room room = new Room(1, 12, 3, "hehe", "haha");

        System.out.println(gson.toJson(room));
    }
}
