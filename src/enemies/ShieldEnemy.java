package enemies;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import game.*;

public class ShieldEnemy extends Enemy {
    private int shield_hp;
    private final int max_shield_hp = 10;

    public ShieldEnemy(double starting_x, double starting_y, EnemyType type) {
        super(starting_x, starting_y, type);
        this.shield_hp = max_shield_hp;
    }

    // сначала урон получает щит
    @Override
    public void takeDamage(int amount) {
        if (shield_hp > 0) {
            shield_hp -= amount;
            if (shield_hp < 0) shield_hp = 0;
        } else {
            super.takeDamage(amount); // урон идет в обычное ХП врага
        }
    }

    // отрисовка щита
    public void render_shield(GraphicsContext graph_context) {
        if (shield_hp > 0) {
            graph_context.setStroke(Color.rgb(0, 191, 255, 0.7));
            graph_context.setLineWidth(3);

            double padding = 20;
            double shield_x = x - padding;
            double shield_y = y - padding;
            double shield_w = get_width() + (padding * 2);
            double shield_h = get_height() + (padding * 2);

            graph_context.strokeOval(shield_x, shield_y, shield_w, shield_h);
        }
    }
}
