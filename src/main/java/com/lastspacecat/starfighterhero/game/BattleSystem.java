package com.lastspacecat.starfighterhero.game;

import com.lastspacecat.starfighterhero.enemies.Enemy;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BattleSystem {
    private final List<Bullet> bullets = new ArrayList<>();
    private double fire_cooldown = 0;
    private double fire_rate = 0.3;

    public void update_bullets(double dt, PlayerShip player, Set<KeyCode> pressed_keys, double max_width) {
        if (fire_cooldown > 0) {
            fire_cooldown -= dt;
        }

        if (pressed_keys.contains(KeyCode.SPACE) && fire_cooldown <= 0) {
            double bullet_x = player.get_x() + PlayerShip.width * 0.9;
            double bullet_y = player.get_y() + (PlayerShip.height * 0.77) - (Bullet.height / 2);

            bullets.add(new Bullet(bullet_x, bullet_y, false, Color.YELLOW));
            fire_cooldown = fire_rate;
        }

        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            bullet.update(dt);

            if (bullet.is_out_of_bounds(max_width)) {
                bullets.remove(i);
                i--;
            }
        }
    }

    public void clear_bullets(){
        bullets.clear();
    }

    public void check_collisions(List<Enemy> enemies) {
        for (int b = 0; b < bullets.size(); b++) {
            Bullet bullet = bullets.get(b);

            for (int e = 0; e < enemies.size(); e++) {
                Enemy enemy = enemies.get(e);

                if (bullet.get_x() < enemy.get_x() + enemy.get_width() &&
                        bullet.get_x() + Bullet.width > enemy.get_x() &&
                        bullet.get_y() < enemy.get_y() + enemy.get_height() &&
                        bullet.get_y() + Bullet.height > enemy.get_y()) {

                    enemy.takeDamage(1);
                    bullets.remove(b);
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
        for (Bullet bullet : bullets) {
            bullet.render(graph_context);
        }
    }

    public void update_from_state(CampaignState state){
        if (state.upgrade_states.get(0) == 2){
            fire_rate = 0.2;
        } else fire_rate = 0.3;
    }
}

