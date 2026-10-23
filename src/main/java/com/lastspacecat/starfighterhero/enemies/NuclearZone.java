package com.lastspacecat.starfighterhero.enemies;
import com.lastspacecat.starfighterhero.game.*;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class NuclearZone {
    private final double x, y;
    private final double radius = Main.screen_height * 0.15;
    private double warningTimer = 1.5;
    private double explosionTimer = 0.5;
    private boolean exploded = false;
    private boolean finished = false;

    public NuclearZone(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void update(double dt, PlayerShip player) {
        if (warningTimer > 0) {
            warningTimer -= dt;
            if (warningTimer <= 0) {
                exploded = true;
                double dx = player.get_x() - x;
                double dy = player.get_y() - y;
                if (Math.sqrt(dx*dx + dy*dy) < radius) {
                    if (!player.isShieldActive()) {
                        player.takeDamage(1);
                    }
                }
            }
        } else if (explosionTimer > 0) {
            explosionTimer -= dt;
            if (explosionTimer <= 0) {
                finished = true;
            }
        }
    }

    public void render(GraphicsContext gc) {
        if (!exploded) {
            gc.setFill(Color.rgb(255, 0, 0, 0.25));
            gc.setStroke(Color.RED);
            gc.setLineWidth(2);
            gc.fillOval(x - radius, y - radius, radius * 2, radius * 2);
            gc.strokeOval(x - radius, y - radius, radius * 2, radius * 2);
        } else {
            gc.setFill(Color.rgb(255, 140, 0, 0.7));
            gc.fillOval(x - radius, y - radius, radius * 2, radius * 2);
        }
    }

    public boolean isFinished() { return finished; }
}
