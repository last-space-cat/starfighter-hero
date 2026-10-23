package com.lastspacecat.starfighterhero.game;

import com.lastspacecat.starfighterhero.enemies.Enemy;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PlayerMine {
    private final double x, y;
    private final double size = Main.screen_height * 0.10;

    public PlayerMine(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public boolean checkCollision(Enemy enemy) {
        return this.x < enemy.get_x() + enemy.get_width() &&
                this.x + this.size > enemy.get_x() &&
                this.y < enemy.get_y() + enemy.get_height() &&
                this.y + this.size > enemy.get_y();
    }

    public void render(GraphicsContext gc) {
        gc.setFill(Color.rgb(255, 0, 0, 0.35));
        gc.fillOval(x, y, size, size);
        gc.setStroke(Color.rgb(255, 140, 0, 0.85));
        gc.setLineWidth(2);
        gc.strokeOval(x, y, size, size);
    }

    public double getX() { return x; }
    public double getY() { return y; }
}

