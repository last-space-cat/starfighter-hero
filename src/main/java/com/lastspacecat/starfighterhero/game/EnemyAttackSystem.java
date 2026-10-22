package com.lastspacecat.starfighterhero.game;

import com.lastspacecat.starfighterhero.enemies.*;
import javafx.scene.canvas.GraphicsContext;
import java.util.ArrayList;
import java.util.List;

public class EnemyAttackSystem {
    private final List<EnemyRocket> enemyRockets = new ArrayList<>();
    private final List<NuclearZone> nuclearZones = new ArrayList<>();
    private final List<EnemyBullet> enemyLasers = new ArrayList<>();

    public List<EnemyRocket> getRockets() { return enemyRockets; }
    public List<NuclearZone> getZones() { return nuclearZones; }
    public List<EnemyBullet> getLasers() { return enemyLasers; }

    public void clear() {
        enemyRockets.clear();
        nuclearZones.clear();
        enemyLasers.clear();
    }

    public void update(double dt, PlayerShip player) {
        for (int i = 0; i < enemyRockets.size(); i++) {
            EnemyRocket rocket = enemyRockets.get(i);
            rocket.update(dt, player);

            if (rocket.getX() < player.get_x() + PlayerShip.width &&
                    rocket.getX() + EnemyRocket.size * 1.5 > player.get_x() &&
                    rocket.getY() < player.get_y() + PlayerShip.height &&
                    rocket.getY() + EnemyRocket.size > player.get_y()) {

                player.takeDamage(1);
                enemyRockets.remove(i);
                i--;
                continue;
            }
            if (rocket.getX() < 0) { enemyRockets.remove(i); i--; }
        }

        for (int i = 0; i < nuclearZones.size(); i++) {
            NuclearZone zone = nuclearZones.get(i);
            zone.update(dt, player);
            if (zone.isFinished()) { nuclearZones.remove(i); i--; }
        }

        for (int i = 0; i < enemyLasers.size(); i++) {
            EnemyBullet laser = enemyLasers.get(i);
            laser.update(dt);

            if (laser.get_x() < player.get_x() + PlayerShip.width &&
                    laser.get_x() + EnemyBullet.width > player.get_x() &&
                    laser.get_y() < player.get_y() + PlayerShip.height &&
                    laser.get_y() + EnemyBullet.height > player.get_y()) {

                player.takeDamage(1); // Наносим игроку 1 урон
                enemyLasers.remove(i);
                i--;
                continue;
            }

            if (laser.is_out_of_bounds()) {
                enemyLasers.remove(i);
                i--;
            }
        }
    }

    public void render(GraphicsContext graph_context) {
        for (NuclearZone zone : nuclearZones) { zone.render(graph_context); }
        for (EnemyRocket rocket : enemyRockets) { rocket.render(graph_context); }
        for (EnemyBullet laser : enemyLasers) { laser.render(graph_context); }
    }
}
