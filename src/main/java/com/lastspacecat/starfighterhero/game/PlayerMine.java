package com.lastspacecat.starfighterhero.game;

import com.lastspacecat.starfighterhero.enemies.Enemy;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PlayerMine {
    private final double x, y;
    private static double size = Main.screen_height * 0.10;
    private static double explosion_size = Main.screen_width* 0.10;
    private static String inside_color = "ff00005a";
    private static String outside_color = "ff8c00d9";

    public PlayerMine(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public boolean checkCollision(Enemy enemy) {
        return this.x < enemy.get_x() + enemy.get_width() &&
                this.x + this.size > enemy.get_x() &&
                this.y < enemy.get_y() + enemy.get_height() &&
                this.y + this.size > enemy.get_y();
    }

    public boolean checkExplosion(Enemy enemy) {
        return this.x < enemy.get_x() + enemy.get_width() &&
                this.x + this.explosion_size > enemy.get_x() &&
                this.y < enemy.get_y() + enemy.get_height() &&
                this.y + this.explosion_size > enemy.get_y();
    }

    public void render(GraphicsContext gc) {
        gc.setFill(Color.web(inside_color));
        gc.fillOval(x, y, size, size);
        gc.setStroke(Color.web(outside_color));
        gc.setLineWidth(2);
        gc.strokeOval(x, y, size, size);
    }

    public double getX() { return x; }
    public double getY() { return y; }

    public static void update_from_state(CampaignState state){
        if (state.upgrade_states.get(13) == 2){
            explosion_size = Main.screen_height*0.20;
            inside_color = "90d5ff5a";
            outside_color = "111184d9";
        }
        else {
            size = Main.screen_height*0.10;
            inside_color = "ff00005a";
            outside_color = "ff8c00d9";
        }
    }
}

