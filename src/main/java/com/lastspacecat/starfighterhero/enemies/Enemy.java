package com.lastspacecat.starfighterhero.enemies;
import com.lastspacecat.starfighterhero.game.*;
import javafx.scene.paint.Color;

public class Enemy {
    protected double x, y;
    protected final EnemyType type;
    private int currentHP;

    public Enemy(double starting_x, double starting_y, EnemyType type) {
        this.x = starting_x;
        this.y = starting_y;
        this.type = type;
        this.currentHP = type.get_maxHP();
    }

    public void takeDamage(int amount) {
        currentHP -= amount;
    }

    public boolean isDead() {
        return currentHP <= 0;
    }

    public double get_x() { return x; }
    public double get_y() { return y; }
    public double get_width() { return type.get_width(); }
    public double get_height() { return type.get_height(); }
    public javafx.scene.image.Image get_sprite() { return type.get_sprite();}

    public void update(double dt, PlayerShip player) {
        x -= type.get_velocity() * dt;
    }
}
