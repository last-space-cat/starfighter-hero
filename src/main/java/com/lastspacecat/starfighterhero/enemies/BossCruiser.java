package com.lastspacecat.starfighterhero.enemies;

import com.lastspacecat.starfighterhero.game.Main;
import com.lastspacecat.starfighterhero.game.PlayerShip;
import com.lastspacecat.starfighterhero.game.*;
import java.util.List;


public class BossCruiser extends Enemy {
    private final List<NuclearZone> zoneList;
    private double attackTimer = 0;
    private double movementTimer = 0;

    public BossCruiser(double starting_x, double starting_y, EnemyType type, List<NuclearZone> zoneList) {
        super(starting_x, starting_y, type);
        this.zoneList = zoneList;
    }

    @Override
    public void update(double dt, PlayerShip player) {
        if (x > Main.screen_width * 0.75) {
            x -= type.get_velocity() * dt;
        }

        movementTimer += dt;
        y = (Main.screen_height * 0.4) + Math.sin(movementTimer * 1.0) * (Main.screen_height * 0.25);
        attackTimer += dt;
        if (attackTimer >= 3.5) {
            attackTimer = 0;
            double targetX = player.get_x() + PlayerShip.width / 2;
            double targetY = player.get_y() + PlayerShip.height / 2;
            zoneList.add(new NuclearZone(targetX, targetY));
        }
    }
}
