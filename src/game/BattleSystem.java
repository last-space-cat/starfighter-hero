package game;

import enemies.Enemy;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BattleSystem {
    private final List<PlayerBullet> bullets = new ArrayList<>();
    private double fire_cooldown = 0;

    public void update_bullets(double dt, PlayerShip player, Set<KeyCode> pressed_keys) {
        if (fire_cooldown > 0) {
            fire_cooldown -= dt;
        }

        if (pressed_keys.contains(KeyCode.SPACE) && fire_cooldown <= 0) {
            double bullet_x = player.get_x() + PlayerShip.width;
            double bullet_y = player.get_y() + (PlayerShip.height / 2) - (PlayerBullet.height / 2);
            bullets.add(new PlayerBullet(bullet_x, bullet_y));
            fire_cooldown = 0.3;
        }

        for (int i = 0; i < bullets.size(); i++) {
            PlayerBullet bullet = bullets.get(i);
            bullet.update(dt);
            if (bullet.is_out_of_bounds()) {
                bullets.remove(i);
                i--;
            }
        }
    }

    public void check_collisions(List<Enemy> enemies) {
        for (int b = 0; b < bullets.size(); b++) {
            PlayerBullet bullet = bullets.get(b);

            for (int e = 0; e < enemies.size(); e++) {
                Enemy enemy = enemies.get(e);

                // проверка пересечения
                if (bullet.get_x() < enemy.get_x() + enemy.get_width() &&
                        bullet.get_x() + PlayerBullet.width > enemy.get_x() &&
                        bullet.get_y() < enemy.get_y() + enemy.get_height() &&
                        bullet.get_y() + PlayerBullet.height > enemy.get_y()) {

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
        for (PlayerBullet bullet : bullets) {
            bullet.render(graph_context);
        }
    }
}
