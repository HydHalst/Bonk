package com.ncs.projecteranking.characters;

import com.ncs.projecteranking.GameEngine;

public class Coin extends Character {

    public Coin(GameEngine gameEngine, int x, int y) {
        super(gameEngine, x, y);
        this.sprite = (int)(Math.random() * 5);
    }

    private static final int[][] ANIMATIONS = new int[][] {
      new int[] { 29, 30, 31, 32, 33 }
    };
    @Override int[][] getAnimations() { return ANIMATIONS; }

    @Override void updatePhysics(int delta) { }

    @Override void updateCollisionRect() {
        collisionRect.set(x, y, x + 12, y + 12);
    }
}
