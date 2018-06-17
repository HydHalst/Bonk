package com.ncs.projecteranking.models;

import com.google.gson.annotations.SerializedName;

public class ResultGame {

    private int error;
    private String message;
    @SerializedName("data")
    private Game game;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public String toString() {
        return "ResultGame{" +
                "error=" + error +
                ", message='" + message + '\'' +
                ", game=" + game +
                '}';
    }
}
