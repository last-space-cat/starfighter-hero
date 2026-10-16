package com.lastspacecat.starfighterhero.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Screen;

public class Bullet {
    private double x, y;
    private final double velocity = Main.screen_width * 0.33;
    public static final double width = Main.screen_height * 0.014;
    public static final double height = Main.screen_height * 0.006;

    // true — летит влево (враг), false — летит вправо (игрок)
    private final boolean isFacingLeft;
    private final Color color;

    public Bullet(double starting_x, double starting_y, boolean isFacingLeft, Color color) {
        this.x = starting_x;
        this.y = starting_y;
        this.isFacingLeft = isFacingLeft;
        this.color = color;
    }

    public boolean is_out_of_bounds(double max_width) {
        return isFacingLeft ? x < -width : x > max_width;
    }

    public double get_x() { return x; }
    public double get_y() { return y; }

    public void update(double dt) {
        if (isFacingLeft) {
            x -= velocity * dt;
        } else {
            x += velocity * dt;
        }
    }

    public void render(GraphicsContext graph_context) {
        graph_context.setFill(color);
        graph_context.fillRect(x, y, width, height);
    }

    public boolean is_out_of_bounds() {
        return isFacingLeft ? x < -width : x > Screen.getPrimary().getBounds().getWidth();
    }
}
