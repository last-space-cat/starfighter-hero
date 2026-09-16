package enemies;
import game.*;
public class ShooterEnemy extends Enemy {
    public ShooterEnemy(double starting_x, double starting_y, EnemyType type) {
        super(starting_x, starting_y, type);
    }

    @Override
    public void update(double dt, PlayerShip player) {
        if (x > 1000) {
            x -= type.get_velocity() * dt;
        }
        // добавить стрельбу
    }
}
