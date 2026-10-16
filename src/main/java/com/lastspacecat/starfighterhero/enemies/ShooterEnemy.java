package com.lastspacecat.starfighterhero.enemies;

import com.lastspacecat.starfighterhero.game.Main;
import com.lastspacecat.starfighterhero.game.PlayerShip;
import com.lastspacecat.starfighterhero.game.Bullet;
import javafx.scene.paint.Color;
import java.util.List;

public class ShooterEnemy extends Enemy {
    private double fireCooldown = 0;
    private final double FIRE_RATE = 2.0;
    private List<Bullet> laserList = null;

    public ShooterEnemy(double starting_x, double starting_y, EnemyType type) {
        super(starting_x, starting_y, type);
    }

    @Override
    public void update(double dt, PlayerShip player) {
        if (x > Main.screen_width * 0.75) {
            x -= type.get_velocity() * dt;
        }

        y += Math.sin(System.currentTimeMillis() * 0.002 + x) * (type.get_velocity() * 0.4) * dt;
        y = Math.clamp(y, Main.screen_height * 0.1, Main.screen_height * 0.8);

        if (laserList != null) {
            fireCooldown -= dt;
            if (fireCooldown <= 0) {
                fireCooldown = FIRE_RATE;
                double laserX = this.x;
                double laserY = this.y + get_height() / 2 - Bullet.height / 2;
                laserList.add(new Bullet(laserX, laserY, true, Color.ORANGE));
            }
        }
    }

    public void setLaserList(List<Bullet> laserList) {
        this.laserList = laserList;
    }
}

