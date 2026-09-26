package com.lastspacecat.starfighterhero.game;

import com.lastspacecat.starfighterhero.enemies.Enemy;
import javafx.scene.input.KeyCode;
import javafx.scene.image.Image;
import java.util.List;
import java.util.Set;

public class PlayerShip {
    private double x, y;
    private double velocity;
    public static double width = 100, height = 50;
    private int current_hp;
    private final Image sprite;

    PlayerShip(double starting_x, double starting_y){ //ПОЗЖЕ НУЖНО ДОБАВИТЬ В КОНСТРУКТОР Campaign_State для установки апгрейдов
        x = starting_x;
        y = starting_y;
        velocity = 400;
        current_hp = 3;
        this.sprite = new Image(getClass().getResourceAsStream("/PLAYER_ship.png"));
    }

    public double get_x(){return x;}

    public double get_y(){return y;}

    public void update(double dt, Set<KeyCode> pressed_keys, double max_width, double max_height){
        double dx = 0, dy = 0;

        if (pressed_keys.contains(KeyCode.W) || pressed_keys.contains(KeyCode.UP))    dy -= dt;
        if (pressed_keys.contains(KeyCode.S) || pressed_keys.contains(KeyCode.DOWN))  dy += dt;
        if (pressed_keys.contains(KeyCode.A) || pressed_keys.contains(KeyCode.LEFT))  dx -= dt;
        if (pressed_keys.contains(KeyCode.D) || pressed_keys.contains(KeyCode.RIGHT)) dx += dt;

        if (dx != 0 && dy != 0){
            dx *= 1/Math.sqrt(2);
            dy *= 1/Math.sqrt(2);
        }

        x += dx*velocity;
        y += dy*velocity;

        x = Math.clamp(x, 0, max_width - width);
        y = Math.clamp(y, 0, max_height - height);
    }

    public int get_current_HP(){
        return this.current_hp;
    }
    public Image get_sprite() { return this.sprite; }

    public void resolve_collisions(List<Enemy> enemies, HUD hud){
        for (Enemy enemy : enemies){
            if (enemy.get_x() + enemy.get_width() > x && enemy.get_x() < (x+width)){
                if (enemy.get_y() + enemy.get_height() > y && enemy.get_y() <(y+height)){
                    current_hp -= 1;
                    enemy.takeDamage(100); //ЗДЕСЬ ПРОСТО НАНОСИМ БОЛЬШЕ УРОНА, ЧЕМ МАКСИМУМ ХП
                    hud.update();
                }
            }
        }
    }
}
