package com.lastspacecat.starfighterhero.game;

import com.lastspacecat.starfighterhero.enemies.Enemy;
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

    public void update_bullets(double dt, PlayerShip player, Set<KeyCode> pressed_keys, double max_width) {
        if (fire_cooldown > 0) {
            fire_cooldown -= dt;
        }

        if (pressed_keys.contains(KeyCode.SPACE) && fire_cooldown <= 0) {
            double bullet_x = player.get_x() + PlayerShip.width * 0.9;
            double bullet_y = player.get_y() + (PlayerShip.height * 0.77) - (EnemyBullet.height / 2);

            playerBullets.add(new PlayerBullet(bullet_x, bullet_y));
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

    public void check_collisions(List<Enemy> enemies) {
        for (int b = 0; b < playerBullets.size(); b++) {
            PlayerBullet bullet = playerBullets.get(b);

            for (int e = 0; e < enemies.size(); e++) {
                Enemy enemy = enemies.get(e);

                if (bullet.get_x() < enemy.get_x() + enemy.get_width() &&
                        bullet.get_x() + EnemyBullet.width > enemy.get_x() &&
                        bullet.get_y() < enemy.get_y() + enemy.get_height() &&
                        bullet.get_y() + EnemyBullet.height > enemy.get_y()) {

                    enemy.takeDamage(player_damage);
                    playerBullets.remove(b);
                    b--;
                    break;
                }
            }
        }

        for (int i = 0; i < enemies.size(); i++) {
            if (enemies.get(i).isDead()) {
                enemies.remove(i);
                i--;
            }
        }
    }

    public void render_bullets(GraphicsContext graph_context) {
        for (EnemyBullet bullet : enemyBullets) {
            bullet.render(graph_context);
        }
        for (PlayerBullet bullet : playerBullets){
            bullet.render(graph_context);
        }
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