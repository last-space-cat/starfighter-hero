package com.lastspacecat.starfighterhero.game;

import com.lastspacecat.starfighterhero.enemies.Enemy;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.List;

public class PlayerNuke {
    private final double flashDuration = 1.8;
    private double timer = 0.0;
    private boolean active = false;

    public void activate(List<Enemy> enemies) {
        this.timer = flashDuration;
        this.active = true;

        for (Enemy enemy : enemies) {
            enemy.takeDamage(100);
        }
    }

    public void update(double dt) {
        if (!active) return;

        timer -= dt;
        if (timer <= 0) {
            active = false;
        }
    }

    public void render(GraphicsContext gc) {
        if (!active) return;

        double alpha = (timer / flashDuration) * 0.85;

        gc.save();
        gc.setFill(Color.rgb(255, 140, 35, alpha));
        gc.fillRect(0, 0, Main.screen_width, Main.screen_height);
        gc.restore();
    }

    public boolean isActive() {
        return active;
    }
    public void clear() {
        this.active = false;
        this.timer = 0.0;
    }
}
