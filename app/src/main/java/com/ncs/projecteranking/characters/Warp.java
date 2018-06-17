package com.ncs.projecteranking.characters;

import com.ncs.projecteranking.GameEngine;

public class Warp extends Character {

    public Warp(GameEngine gameEngine, int x, int y) {
        super(gameEngine, x, y);
        this.sprite = (int)(Math.random() * 5);
    }

    private static final int[][] ANIMATIONS = new int[][] {
            new int[] { 66 }
    };
    @Override int[][] getAnimations() { return ANIMATIONS; }

    @Override void updatePhysics(int delta) { }

    @Override void updateCollisionRect() {
        collisionRect.set(x, y, x + 16, y + 16);
    }
}