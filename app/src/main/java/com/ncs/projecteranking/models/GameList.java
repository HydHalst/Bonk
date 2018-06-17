package com.ncs.projecteranking.models;

import com.google.gson.annotations.SerializedName;

public class GameList {

    @SerializedName("data")
    private Game[] games;

    public Game[] getGames() {
        return games;
    }

    public void setGames(Game[] games) {
        this.games = games;
    }

    public int getCount() {
        return this.games.length;
    }
}