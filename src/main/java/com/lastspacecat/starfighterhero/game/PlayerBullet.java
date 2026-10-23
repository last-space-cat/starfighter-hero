package com.lastspacecat.starfighterhero.game;

import com.lastspacecat.starfighterhero.enemies.Enemy;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Screen;

import java.util.ArrayList;
import java.util.List;

public class PlayerBullet {
    private double x, y;
    private double dy = 0;
    private final double velocity = Main.screen_width*0.33;
    private List<Enemy> pierced_enemies = new ArrayList<>();
    private static int pierced_maxsize = 1;
    public static double width = Main.screen_height*0.014;
    public static double height = Main.screen_height*0.006;

    public PlayerBullet(double starting_x, double starting_y, double dy) {
        this.x = starting_x;
        this.y = starting_y;
        this.dy = dy;
    }

    public double get_x() { return x; }
    public double get_y() { return y; }

    public void update(double dt) {x += velocity * dt;  y += dy * velocity * 0.25 * dt;}

    public void render(GraphicsContext graph_context) {
        graph_context.setFill(Color.YELLOW);
        graph_context.fillRect(x, y, width, height);
    }

    public boolean is_out_of_bounds() {
        return x > Screen.getPrimary().getBounds().getWidth();
    }

    public boolean is_out_of_pierce() { return pierced_enemies.size() >= pierced_maxsize; }

    public boolean has_pierced(Enemy enemy) { return pierced_enemies.contains(enemy); }

    public void add_to_pierced(Enemy enemy) {pierced_enemies.add(enemy); }

    public static void update_from_state(CampaignState state){
        if (state.upgrade_states.get(1) == 2){ height = Main.screen_height*0.018; }
        else { height = Main.screen_height*0.006; }

        if (state.upgrade_states.get(2) == 2){ pierced_maxsize = 2; }
        else { pierced_maxsize = 1; }
    }
}