package com.ncs.projecteranking;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.util.Log;
import android.util.SparseIntArray;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.ncs.projecteranking.characters.Bonk;
import com.ncs.projecteranking.characters.Booster;
import com.ncs.projecteranking.characters.Coin;
import com.ncs.projecteranking.characters.Crab;
import com.ncs.projecteranking.characters.Enemy;
import com.ncs.projecteranking.characters.Warp;

public class Scene {
    private GameEngine gameEngine;
    private String scene[];
    private Paint paint;

    private int sceneWidth, sceneHeight;
    private SparseIntArray CHARS;
    private String GROUND, WALLS;
    private int WATERLEVEL, SKY, WATERSKY, WATER;

    private Bonk bonk;

    private List<Coin> coins;
    private List<Enemy> enemies;
    private List<Booster> boosters;
    private List<Warp> warps;

    Scene(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
        paint = new Paint();
        CHARS = new SparseIntArray();
        WATERLEVEL = 999;
        coins = new ArrayList<>();
        enemies = new ArrayList<>();
        boosters = new ArrayList<>();
        warps = new ArrayList<>();
    }

    void loadFromFile(int resource) {
        InputStream res = gameEngine.getContext().getResources().openRawResource(resource);
        BufferedReader reader = new BufferedReader(new InputStreamReader(res));
        List<String> lines = new ArrayList<>();
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isEmpty()) continue;
                line = line.trim();
                if (!line.contains("=")) continue;                  // NO VALID LINE
                if (line.startsWith("=")) continue;                 // COMMENT
                String[] parts = line.split("=", 2);
                String cmd = parts[0].trim();
                String args = parts[1].trim();
                String[] parts2;
                switch (cmd) {
                    case "SCENE":
                        lines.add(args);
                        break;
                    case "CHARS":
                        parts2 = args.split(" ");
                        for(String def : parts2) {
                            String[] item = def.split("=");
                            if (item.length != 2) continue;
                            char c = item[0].trim().charAt(0);
                            int idx = Integer.parseInt(item[1].trim());
                            CHARS.put(c, idx);
                        }
                        break;
                    case "GROUND":
                        GROUND = args;
                        break;
                    case "WALLS":
                        WALLS = args;
                        break;
                    case "WATER":
                        parts2 = args.split(",");
                        if (parts2.length != 4) continue;
                        WATERLEVEL = Integer.parseInt(parts2[0].trim());
                        SKY = Integer.parseInt(parts2[1].trim());
                        WATERSKY = Integer.parseInt(parts2[2].trim());
                        WATER = Integer.parseInt(parts2[3].trim());
                        break;
                    case "COIN":
                        parts2 = args.split(",");
                        if (parts2.length != 2) continue;
                        int coinX = Integer.parseInt(parts2[0].trim()) * 16;
                        int coinY = Integer.parseInt(parts2[1].trim()) * 16;
                        Coin coin = new Coin(gameEngine, coinX, coinY);
                        coins.add(coin);
                        break;
                    case "CRAB":
                        parts2 = args.split(",");
                        if (parts2.length != 3) continue;
                        int crabX0 = Integer.parseInt(parts2[0].trim()) * 16;
                        int crabX1 = Integer.parseInt(parts2[1].trim()) * 16;
                        int crabY = Integer.parseInt(parts2[2].trim()) * 16;
                        Crab crab = new Crab(gameEngine, crabX0, crabX1, crabY);
                        enemies.add(crab);
                        break;
                    case "BOOSTER":
                        parts2 = args.split(",");
                        if (parts2.length != 2) continue;
                        int boosterX = Integer.parseInt(parts2[0].trim()) * 16;
                        int boosterY = Integer.parseInt(parts2[1].trim()) * 16;
                        Booster booster = new Booster(gameEngine, boosterX, boosterY);
                        boosters.add(booster);
                        break;
                    case "WARP":
                        parts2 = args.split(",");
                        if (parts2.length != 2) continue;
                        int warpX = Integer.parseInt(parts2[0].trim()) * 16;
                        int warpY = Integer.parseInt(parts2[1].trim()) * 16;
                        Warp warp = new Warp(gameEngine, warpX, warpY);
                        warps.add(warp);
                        break;



                }
            }
            scene = lines.toArray(new String[0]);
            reader.close();
            sceneHeight = scene.length;
            sceneWidth = scene[0].length();
        }
        catch (IOException e) {
            Toast.makeText(gameEngine.getContext(), "Error loading scene:" +  e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public boolean isGround(int r, int c) {
        if (r < 0) return false;
        if (r >= sceneHeight) return false;
        if (c < 0) return false;
        if (c >= sceneWidth) return false;
        char sc = scene[r].charAt(c);
        return (GROUND.indexOf(sc) != -1);
    }

    public boolean isWall(int r, int c) {
        if (r < 0) return false;
        if (r >= sceneHeight) return false;
        if (c < 0) return false;
        if (c >= sceneWidth) return false;
        char sc = scene[r].charAt(c);
        return (WALLS.indexOf(sc) != -1);
    }

    public int getSceneWidth() { return sceneWidth; }
    public int getSceneHeight() { return sceneHeight; }
    public int getWaterLevel() { return WATERLEVEL; }

    public int getWidth() { return sceneWidth * 16; }
    public int getHeight() { return sceneHeight * 16; }



    // Scene physics
    void physics(int delta) {

        for(Coin coin : coins) coin.physics(delta);
        for(Enemy enemy : enemies) enemy.physics(delta);
        for(Booster booster : boosters) booster.physics(delta);
        for(Warp warp : warps) warp.physics(delta);

        final Bonk bonk = gameEngine.getBonk();
        // Coin collision
        for(int i = coins.size() -1; i>=0; i--) {
            Coin coin = coins.get(i);
            if (bonk.getCollisionRect().intersect(coin.getCollisionRect())) {
                gameEngine.getAudio().coin();
                Score.increaseScore();
                Log.d("fix", String.valueOf(Score.getScoreEnd()));
                coins.remove(coin);

            }
        }

        // Booster collision
        for(int i = boosters.size() -1; i>=0; i--) {
            final Booster booster = boosters.get(i);
            if (bonk.getCollisionRect().intersect(booster.getCollisionRect())) {
                gameEngine.getAudio().booster();
        //      Score.increaseScore();
        //      Log.d("fix", String.valueOf(Score.getScoreEnd()));
                bonk.speedBoost(6);
                boosters.remove(booster);
                bonk.ArrowShow = true;

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        bonk.speedBoost(3);
                        bonk.ArrowShow = false;


                    }

                },4500);

            }
        }

        // Warp collision
        for(int i = warps.size() -1; i>=0; i--) {
            Warp warp = warps.get(i);
            if (bonk.getCollisionRect().intersect(warp.getCollisionRect())) {
                gameEngine.getAudio().coin();
                loadFromFile(R.raw.scene2);
                warps.remove(warp);
            }
        }

        // Enemy/crab collision
        for (Enemy enemy : enemies) {
            if (bonk.getCollisionRect().intersect(enemy.getCollisionRect())) {
                gameEngine.getAudio().die();
                bonk.die();


                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        bonk.alive();
                        bonk.reset(4,2);
                        GameEngine.lifesCount -= 1;
                        if (GameEngine.lifesCount == 0) {
                            moveTaskToBack(true);
                            android.os.Process.killProcess(android.os.Process.myPid());
                            System.exit(1);
                        }

                    }

                },3500);

            }
        }


        /*

        for(int i = enemies.size() -1; i>=0; i--) {
            final Enemy enemy = enemies.get(i);
            if (bonk.getCollisionRect().intersect(enemy.getCollisionRect())) {
                gameEngine.getAudio().die();
                Score.reduceScore();

                Log.d("fix", "DEAD");
                Log.d("fix", String.valueOf(Score.getScoreEnd()));
                bonk.die();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        bonk.reset(0,20);
                        bonk.alive();
                        GameEngine.lifesCount -= 1;
                        loadFromFile(R.raw.mini);
                        if (GameEngine.lifesCount == 0) {
                            loadFromFile(R.raw.mini);
                            GameEngine.lifesCount = 3;
                            Score.resetScore();
                        }

                    }

                },3700);
            }
        }

        */


    }

    private void moveTaskToBack(boolean b) {
        System.exit(0);
    }

    // Scene draw
    void draw(Canvas canvas, int offsetX, int offsetY, int screenWidth, int screenHeight) {
        if (scene == null) return;

        // Compute which tiles will be drawn
        int l = Math.max(0, offsetX / 16);
        int r = Math.min(scene[0].length(), offsetX / 16 + screenWidth / 16 + 2);
        int t = Math.max(0, offsetY / 16);
        int b = Math.min(scene.length, offsetY / 16 + screenHeight / 16 + 2);

        // Do the x-y loops over the visible scene
        for(int y = t; y < b; y++) {

            // Compute the background index (sky / water)
            int bgIdx = SKY;
            if (y == WATERLEVEL) bgIdx = WATERSKY;
            else if (y > WATERLEVEL) bgIdx = WATER;
            Bitmap bgBitmap = gameEngine.getBitmap(bgIdx);

            for(int x = l; x < r; x++) {
                // Draw the background tile
                canvas.drawBitmap(bgBitmap, x * 16, y * 16, paint);

                // Compute the bitmap index for the current tile
                char c = scene[y].charAt(x);
                int index = CHARS.get(c);
                if (index == SKY) continue;
                Bitmap bitmap = gameEngine.getBitmap(index);
                canvas.drawBitmap(bitmap, x * 16, y * 16, paint);
            }
        }

        for(Coin coin : coins) coin.draw(canvas);
        for(Enemy enemy : enemies) enemy.draw(canvas);
        for(Booster booster : boosters) booster.draw(canvas);
        for(Warp warp : warps) warp.draw(canvas);


    }
}
