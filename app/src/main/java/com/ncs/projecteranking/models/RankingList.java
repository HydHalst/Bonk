package com.ncs.projecteranking.models;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class RankingList {

    @SerializedName("ranking")
    private Ranking[] rankings;

    public Ranking[] getRankings() {
        return rankings;
    }

    public void setRankings(Ranking[] rankings) {
        this.rankings = rankings;
    }

    public int getCount() {
        return this.rankings.length;
    }

    @Override
    public String toString() {
        return "RankingList{" +
                "rankings=" + Arrays.toString(rankings) +
                '}';
    }
}
