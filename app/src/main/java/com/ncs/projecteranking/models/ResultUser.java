package com.ncs.projecteranking.models;

import com.google.gson.annotations.SerializedName;

public class ResultUser {

    private int error;
    private String message;
    @SerializedName("data")
    private User user;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "ResultGame{" +
                "error=" + error +
                ", message='" + message + '\'' +
                ", user=" + user +
                '}';
    }
}
