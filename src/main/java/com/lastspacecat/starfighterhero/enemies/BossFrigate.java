package com.lastspacecat.starfighterhero.enemies;

import com.lastspacecat.starfighterhero.game.Main;
import com.lastspacecat.starfighterhero.game.PlayerShip;
import com.lastspacecat.starfighterhero.game.*;
import java.util.List;

public class BossFrigate extends Enemy {
    private final List<EnemyRocket> rocketList;
    private double movementAngle = 0;
    private double shootTimer = 0;

    public BossFrigate(double starting_x, double starting_y, EnemyType type, List<EnemyRocket> rocketList) {
        super(starting_x, starting_y, type);
        this.rocketList = rocketList;
    }

    @Override
    public void update(double dt, PlayerShip player) {
        movementAngle += 1.5 * dt;
        y = (Main.screen_height * 0.4) + Math.sin(movementAngle) * (Main.screen_height * 0.25);
        x = (Main.screen_width * 0.72) + Math.sin(movementAngle * 2) * (Main.screen_width * 0.08);

        shootTimer += dt;
        if (shootTimer >= 2.5) {
            shootTimer = 0;
            rocketList.add(new EnemyRocket(this.x, this.y + get_height() / 2));
        }
    }
}
