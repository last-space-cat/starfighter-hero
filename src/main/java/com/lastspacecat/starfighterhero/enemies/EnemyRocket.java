package com.lastspacecat.starfighterhero.enemies;
import com.lastspacecat.starfighterhero.game.*;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class EnemyRocket {
    private double x, y;
    private final double speed = Main.screen_width * 0.18;
    public static final double size = Main.screen_height * 0.02;

    public EnemyRocket(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void update(double dt, PlayerShip player) {
        x -= speed * dt;
        if (player.get_y() > this.y) {
            y += speed * 0.5 * dt;
        } else if (player.get_y() < this.y) {
            y -= speed * 0.5 * dt;
        }
    }

    public void render(GraphicsContext gc) {
        gc.setFill(Color.ORANGE);
        gc.fillRect(x, y, size * 1.5, size);
    }

    public double getX() { return x; }
    public double getY() { return y; }
}
