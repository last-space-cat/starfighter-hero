package com.lastspacecat.starfighterhero.game;

import com.lastspacecat.starfighterhero.enemies.Enemy;
import com.lastspacecat.starfighterhero.enemies.EnemyType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BattleSystem {
    private final List<EnemyBullet> enemyBullets = new ArrayList<>();
    private final List<PlayerBullet> playerBullets = new ArrayList<>();
    private double fire_cooldown = 0;
    private double player_fire_rate = 0.3;
    private int player_damage = 1;
    private final PlayerLaser playerLaser = new PlayerLaser();
    private boolean isLaserFiringThisFrame = false;

    public void update_bullets(double dt, PlayerShip player, Set<KeyCode> pressed_keys, double max_width, List<Enemy> enemies) {
        if (fire_cooldown > 0) {
            fire_cooldown -= dt;
        }
        isLaserFiringThisFrame = false;
        if (player.isLaserActive()) {
            if (pressed_keys.contains(KeyCode.SPACE)) {
                isLaserFiringThisFrame = true;
                playerLaser.update(dt, player, enemies, player_damage);
            }
            for (int i = 0; i < enemyBullets.size(); i++) {
                EnemyBullet bullet = enemyBullets.get(i);
                bullet.update(dt);
                if (bullet.is_out_of_bounds()) { enemyBullets.remove(i); i--; }
            }
            return;
        }
        if (pressed_keys.contains(KeyCode.SPACE) && fire_cooldown <= 0) {
            double bullet_x = player.get_x() + PlayerShip.width * 0.9;
            double bullet_y = player.get_y() + (PlayerShip.height * 0.77) - (EnemyBullet.height / 2);

            if (player.isTripleFireActive()) {
                playerBullets.add(new PlayerBullet(bullet_x, bullet_y, 0));  //центр
                playerBullets.add(new PlayerBullet(bullet_x, bullet_y, -1)); //верх
                playerBullets.add(new PlayerBullet(bullet_x, bullet_y, 1));  //низ
            } else {
                playerBullets.add(new PlayerBullet(bullet_x, bullet_y, 0));
            }
            fire_cooldown = player_fire_rate;
        }

        for (int i = 0; i < enemyBullets.size(); i++) {
            EnemyBullet bullet = enemyBullets.get(i);
            bullet.update(dt);
            if (bullet.is_out_of_bounds()) {
                enemyBullets.remove(i);
                i--;
            }
        }
        for (int i = 0; i < playerBullets.size(); i++) {
            PlayerBullet bullet = playerBullets.get(i);
            bullet.update(dt);
            if (bullet.is_out_of_bounds()) {
                playerBullets.remove(i);
                i--;
            }
        }
    }

    public void clear_bullets(){
        playerBullets.clear();
        enemyBullets.clear();
    }

    public void check_collisions(List<Enemy> enemies, BonusSystem bonusSystem) {
        for (int b = 0; b < playerBullets.size(); b++) {
            PlayerBullet bullet = playerBullets.get(b);

            for (int e = 0; e < enemies.size(); e++) {
                Enemy enemy = enemies.get(e);

                if (bullet.get_x() < enemy.get_x() + enemy.get_width() &&
                        bullet.get_x() + EnemyBullet.width > enemy.get_x() &&
                        bullet.get_y() < enemy.get_y() + enemy.get_height() &&
                        bullet.get_y() + EnemyBullet.height > enemy.get_y()) {

                    if (!(playerBullets.get(b).has_pierced(enemy))) {
                        enemy.takeDamage(player_damage);
                        playerBullets.get(b).add_to_pierced(enemy);
                    }
                    if (playerBullets.get(b).is_out_of_pierce()) {
                        playerBullets.remove(b);
                        b--;
                        break;
                    }

                }
            }
        }
        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);
            if (enemy.isDead()) {
                if (enemy.getType() == EnemyType.BONUS) {
                    double rand = Math.random();
                    if (rand < 0.2) {
                        bonusSystem.spawnBonus(enemy.get_x(), enemy.get_y(), DroppedBonus.BonusType.SHIELD);
                    } else if (rand < 0.4) {
                        bonusSystem.spawnBonus(enemy.get_x(), enemy.get_y(), DroppedBonus.BonusType.REPAIR);
                    } else if (rand < 0.6) {
                        bonusSystem.spawnBonus(enemy.get_x(), enemy.get_y(), DroppedBonus.BonusType.TRIPLE_FIRE);
                    } else if (rand < 0.8) {
                        bonusSystem.spawnBonus(enemy.get_x(), enemy.get_y(), DroppedBonus.BonusType.MINES);
                    } else {
                        bonusSystem.spawnBonus(enemy.get_x(), enemy.get_y(), DroppedBonus.BonusType.LASER);
                    }
                }
                enemies.remove(i);
                i--;
            }
        }
    }

    public void render_bullets(GraphicsContext graph_context) {
        if (isLaserFiringThisFrame) {playerLaser.render(graph_context);}
        for (EnemyBullet bullet : enemyBullets) {bullet.render(graph_context);}
        for (PlayerBullet bullet : playerBullets){bullet.render(graph_context);}
    }

    public void update_from_state(CampaignState state){
        if (state.upgrade_states.get(0) == 2){
            player_fire_rate = 0.2;
        } else player_fire_rate = 0.3;

        if (state.upgrade_states.get(3) == 2){
            player_damage = 2;
        } else player_damage = 1;
    }
}