package com.lastspacecat.starfighterhero.game;

import com.lastspacecat.starfighterhero.enemies.Enemy;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.List;

public class PlayerLaser {
    private double startX;
    private double startY;
    private final double beamHeight = Main.screen_height * 0.009;
    private final double damageCooldown = 0.1;
    private double fireTimer = 0.0;

    public void update(double dt, PlayerShip player, List<Enemy> enemies, int damage) {
        this.startX = player.get_x() + PlayerShip.width * 0.9;
        this.startY = player.get_y() + (PlayerShip.height * 0.77);

        if (fireTimer > 0) {
            fireTimer -= dt;
        }

        if (fireTimer <= 0) {
            fireTimer = damageCooldown;
            double beamTopY = startY - beamHeight / 2;
            for (Enemy enemy : enemies) {
                if (enemy.get_x() + enemy.get_width() > startX) {
                    if (enemy.get_y() < beamTopY + beamHeight && enemy.get_y() + enemy.get_height() > beamTopY) {
                        enemy.takeDamage(damage);
                    }
                }
            }
        }
    }

    public void render(GraphicsContext gc) {
        double startYTop = startY - beamHeight / 2;
        double beamLength = Main.screen_width - startX;
        gc.setFill(Color.rgb(255, 69, 0, 0.4));
        gc.fillRect(startX, startYTop - 4, beamLength, beamHeight + 8);
        gc.setFill(Color.WHITE);
        gc.fillRect(startX, startYTop, beamLength, beamHeight);
    }
}
