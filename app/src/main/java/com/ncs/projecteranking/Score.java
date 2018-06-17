package com.ncs.projecteranking;

/**
 * Created by alu2016086 on 12/03/2018.
 */

class Score {

    private static int score;

    public static int increaseScore() {
        return score += 10;
    }

    public static int getScoreEnd() {
        return score;

    }

    public static int reduceScore() {return score -= 10;}

    public static int resetScore() {return score = 0;}

    public void setScore(int score) {
        this.score = score;
    }

}
