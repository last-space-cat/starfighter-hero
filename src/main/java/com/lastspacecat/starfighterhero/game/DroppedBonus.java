package com.lastspacecat.starfighterhero.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DroppedBonus {
    public enum BonusType {
        SHIELD, LASER, MINES, REPAIR, TRIPLE_FIRE
    }

    private double x, y;
    private final BonusType type;
    private final double speed = Main.screen_width * 0.08;
    private final double size = Main.screen_height * 0.04;

    public DroppedBonus(double x, double y, BonusType type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public void update(double dt) {
        x -= speed * dt;
    }

    public void render(GraphicsContext gc) {
        if (type == BonusType.SHIELD) {
            gc.setFill(Color.rgb(0, 255, 255, 0.9));
            gc.fillOval(x, y, size, size);
            gc.setStroke(Color.WHITE);
            gc.setLineWidth(2);
            gc.strokeOval(x, y, size, size);
        }
        else if (type == BonusType.REPAIR) {
            gc.setFill(Color.rgb(50, 205, 50, 0.9));
            gc.fillOval(x, y, size, size);
            gc.setStroke(Color.WHITE);
            gc.setLineWidth(3);
            gc.strokeLine(x + size * 0.25, y + size * 0.5, x + size * 0.75, y + size * 0.5);
            gc.strokeLine(x + size * 0.5, y + size * 0.25, x + size * 0.5, y + size * 0.75);
        }
        else if (type == BonusType.TRIPLE_FIRE) {
            gc.setFill(Color.rgb(255, 0, 255, 0.9));
            gc.fillOval(x, y, size, size);
            gc.setStroke(Color.WHITE);
            gc.setLineWidth(2);
            gc.strokeOval(x, y, size, size);
        }
        else if (type == BonusType.MINES) {
            gc.setFill(Color.rgb(139, 0, 0, 0.95));
            gc.fillOval(x, y, size, size);
            gc.setStroke(Color.ORANGE);
            gc.setLineWidth(2);
            gc.strokeOval(x, y, size, size);
        }
        else if (type == BonusType.LASER) {
            gc.setFill(Color.rgb(139, 199, 0, 0.95));
            gc.fillOval(x, y, size, size);
            gc.setStroke(Color.ORANGE);
            gc.setLineWidth(2);
            gc.strokeOval(x, y, size, size);
        }
    }

    public boolean checkCollision(PlayerShip player) {
        return player.get_x() < this.x + this.size &&
                player.get_x() + PlayerShip.width > this.x &&
                player.get_y() < this.y + this.size &&
                player.get_y() + PlayerShip.height > this.y;
    }

    public void collect(PlayerShip player, MineSystem mineSystem) {
        if (this.type == BonusType.REPAIR) {
            player.heal(1);
        } else if (this.type == BonusType.MINES) {
            mineSystem.deployMines();
        } else {
            double duration = (this.type == BonusType.LASER) ? 5.0 : 15.0;
            player.activateBonus(this.type, duration);
        }
    }

    public boolean isOutOfBounds() {
        return x < -size;
    }
}