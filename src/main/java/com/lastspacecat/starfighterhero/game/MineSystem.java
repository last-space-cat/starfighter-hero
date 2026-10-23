package com.lastspacecat.starfighterhero.game;

import com.lastspacecat.starfighterhero.enemies.Enemy;
import javafx.scene.canvas.GraphicsContext;
import java.util.ArrayList;
import java.util.List;

public class MineSystem {
    private final List<PlayerMine> activeMines = new ArrayList<>();

    public void clear() {
        activeMines.clear();
    }

    public void deployMines() {
        activeMines.clear();
        double mineX = Main.screen_width * 0.05;
        double totalHeight = Main.screen_height;

        for (int i = 1; i <= 7; i++) {
            double mineY = (totalHeight / 8) * i - (Main.screen_height * 0.015);
            activeMines.add(new PlayerMine(mineX, mineY));
        }
    }

    public void checkCollisions(List<Enemy> enemies) {
        for (int m = 0; m < activeMines.size(); m++) {
            PlayerMine mine = activeMines.get(m);

            for (Enemy enemy : enemies) {
                if (mine.checkCollision(enemy)) {
                    enemy.takeDamage(10);
                    activeMines.remove(m);
                    m--;
                    break;
                }
            }
        }
    }

    public void render(GraphicsContext gc) {
        for (PlayerMine mine : activeMines) {
            mine.render(gc);
        }
    }
}
