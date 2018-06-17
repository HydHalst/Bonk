package com.ncs.projecteranking.models;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class Game {

    private int id;
    private String name;
    private String description;
    @SerializedName("image_path")
    private String imagePath;
    private Ranking[] ranking;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Ranking[] getRanking() {
        return ranking;
    }

    public void setRanking(Ranking[] ranking) {
        this.ranking = ranking;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", ranking=" + Arrays.toString(ranking) +
                '}';
    }
}
