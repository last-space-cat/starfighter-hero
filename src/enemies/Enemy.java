package enemies;
import game.*;
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
    public int get_width() { return type.get_width(); }
    public int get_height() { return type.get_height(); }
    public Color get_color() { return type.get_color(); }

    public void update(double dt, PlayerShip player) {
        x -= type.get_velocity() * dt;
    }
}
