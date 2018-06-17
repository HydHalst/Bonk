package com.ncs.projecteranking.characters;

import com.ncs.projecteranking.GameEngine;

public class Booster extends Character {

    public Booster(GameEngine gameEngine, int x, int y) {
        super(gameEngine, x, y);
        this.sprite = (int)(Math.random() * 5);
    }

    private static final int[][] ANIMATIONS = new int[][] {
            new int[] { 58, 59, 60, 61, 62, 63, 62, 61, 60, 59 }
    };
    @Override int[][] getAnimations() { return ANIMATIONS; }

    @Override void updatePhysics(int delta) { }

    @Override void updateCollisionRect() {
        collisionRect.set(x, y, x + 16, y + 16);
    }
}
