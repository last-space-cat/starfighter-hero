package enemies;
import game.*;

public class KamikazeEnemy extends Enemy {
    public KamikazeEnemy(double starting_x, double starting_y, EnemyType type) {
        super(starting_x, starting_y, type);
    }

    @Override
    public void update(double dt, PlayerShip player) {
        x -= type.get_velocity() * dt;

        // наведение
        if (player.get_y() > this.y) {
            y += type.get_velocity() * 0.6 * dt;
        } else if (player.get_y() < this.y) {
            y -= type.get_velocity() * 0.6 * dt;
        }
    }
}
