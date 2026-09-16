package game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PlayerBullet {
    private double x, y;
    private final double velocity = 600;
    public static final int width = 15;
    public static final int height = 6;

    public PlayerBullet(double starting_x, double starting_y) {
        this.x = starting_x;
        this.y = starting_y;
    }

    public double get_x() { return x; }
    public double get_y() { return y; }

    public void update(double dt) {
        x += velocity * dt;
    }

    public void render(GraphicsContext graph_context) {
        graph_context.setFill(Color.YELLOW);
        graph_context.fillRect(x, y, width, height);
    }

    public boolean is_out_of_bounds() {
        return x > 1280;
    }
}