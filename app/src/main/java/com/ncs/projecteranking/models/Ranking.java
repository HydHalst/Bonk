package com.ncs.projecteranking.models;

import com.google.gson.annotations.SerializedName;

public class Ranking {

    private int id;
    @SerializedName("user_id")
    private int userId;
    @SerializedName("game_id")
    private int gameId;
    private String score;
    private Game game;
    private User user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Ranking{" +
                "id=" + id +
                ", userId=" + userId +
                ", gameId=" + gameId +
                ", score='" + score + '\'' +
                ", game=" + game +
                ", user=" + user +
                '}';
    }
}
