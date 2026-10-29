package com.lastspacecat.starfighterhero.game;

import com.lastspacecat.starfighterhero.enemies.Enemy;
import javafx.scene.input.KeyCode;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.Set;

public class PlayerShip {
    private double x, y;
    private double velocity;
    public static double width = Main.screen_width*0.1;
    public static double height = Main.screen_width*0.033;
    private int current_hp;
    private int max_hp;
    private final Image sprite;
    public final ActiveBonuses activeEffects = new ActiveBonuses();

    PlayerShip(){
        velocity = Main.screen_width*0.25;
        max_hp = 3;
        init_player();
        this.sprite = new Image(getClass().getResourceAsStream("/PLAYER_ship.png"));
    }

    public void init_player(){
        x = Main.screen_width*0.1;
        y = Main.screen_height*0.5 - width*0.5;
        current_hp = max_hp;
        activeEffects.clear();
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
        x = Math.clamp(x, 0, Main.screen_width - width);
        y = Math.clamp(y, 0, Main.screen_height - height);

        activeEffects.update(dt);
    }

    public int get_current_HP(){
        return this.current_hp;
    }
    public Image get_sprite() { return this.sprite; }
    public void takeDamage(int amount) {this.current_hp -= amount;}

    public void activateBonus(DroppedBonus.BonusType type, double duration) {
        double currentDuration = activeEffects.getActiveEffects().getOrDefault(type, 0.0);
        activeEffects.put(type, Math.max(currentDuration, duration));
    }

    public void resolve_collisions(List<Enemy> enemies){
        for (Enemy enemy : enemies){
            if (enemy.get_x() + enemy.get_width() > x && enemy.get_x() < (x+width)){
                if (enemy.get_y() + enemy.get_height() > y && enemy.get_y() <(y+height)){
                    if (activeEffects.isShieldActive()) {
                        activeEffects.take_shield_damage(1);
                    } else {
                        current_hp -= 1;
                    }
                    enemy.takeDamage(100);//ЗДЕСЬ ПРОСТО НАНОСИМ БОЛЬШЕ УРОНА, ЧЕМ МАКСИМУМ ХП
                }
            }
            if (enemy.get_x() <= 0){
                current_hp -= 1;
                enemy.takeDamage(100);  //ЗДЕСЬ ПРОСТО НАНОСИМ БОЛЬШЕ УРОНА, ЧЕМ МАКСИМУМ ХП
            }
        }
    }

    public void heal(int amount) {
        this.current_hp += amount;
        if (this.current_hp > this.max_hp) {
            this.current_hp = this.max_hp;
        }
    }

    public void render_shield(javafx.scene.canvas.GraphicsContext graph_context) {
        if (activeEffects.isShieldActive()) {
            double center_x = x + width / 2;
            double center_y = y + height / 2;
            double padding = Main.screen_height * 0.01;
            double radius = Math.sqrt(width * width + height * height) / 2 + padding;
            double draw_x = center_x - radius;
            double draw_y = center_y - radius;
            double diameter = radius * 2;

            graph_context.setFill(Color.rgb(0, 191, 255, 0.15));
            graph_context.fillOval(draw_x, draw_y, diameter, diameter);
            graph_context.setStroke(Color.rgb(0, 255, 255, 0.8));
            graph_context.setLineWidth(4);
            graph_context.strokeOval(draw_x, draw_y, diameter, diameter);
        }
    }

    public void update_from_state(CampaignState state){
        if (state.upgrade_states.get(4) == 2){
            if (state.upgrade_states.get(6) == 2) max_hp = 5;
            else max_hp = 4;
        } else max_hp = 3;

        if (state.upgrade_states.get(5) == 2){ velocity = Main.screen_width*0.36; }
        else { velocity = Main.screen_width*0.24; }

        activeEffects.update_from_state(state);
    }
}
