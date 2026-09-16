package enemies;
import game.*;
import java.util.List;

public class ModularEnemy extends Enemy {
    // ссылка на очередь спавна
    private final List<Enemy> spawn_queue;

    public ModularEnemy(double starting_x, double starting_y, EnemyType type, List<Enemy> spawn_queue) {
        super(starting_x, starting_y, type);
        this.spawn_queue = spawn_queue;
    }

    @Override
    public void takeDamage(int amount) {
        super.takeDamage(amount);

        if (this.isDead()) {
            spawn_fragments();
        }
    }

    private void spawn_fragments() {
        // создание детей
        Enemy fragment_1 = new Enemy(this.x, this.y - 40, EnemyType.BASE);
        Enemy fragment_2 = new Enemy(this.x + 30, this.y, EnemyType.BASE);
        Enemy fragment_3 = new Enemy(this.x, this.y + 40, EnemyType.BASE);

        // добавляем в буферную очередь
        spawn_queue.add(fragment_1);
        spawn_queue.add(fragment_2);
        spawn_queue.add(fragment_3);
    }
}