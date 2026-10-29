package com.lastspacecat.starfighterhero.game;

import com.lastspacecat.starfighterhero.enemies.Enemy;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.List;

public final class PlayerNuke {
    private static final double flashDuration = 1.8;
    private static double timer = 0.0;
    private static boolean active = false;
    private static double blast_radius = 0.5;
    private static int damage = 10;

    private PlayerNuke(){}

    public static void activate(List<Enemy> enemies, PlayerShip player) {
        timer = flashDuration;
        active = true;
        double x = player.get_x();
        double y = player.get_y();

        for (Enemy enemy : enemies) {
            if (Math.sqrt((x-enemy.get_x())*(x-enemy.get_x()) + (y-enemy.get_y())*(y-enemy.get_y())) <= Main.screen_width*blast_radius){
                enemy.takeDamage(damage);
            }
        }

        player.activeEffects.setHas_nuke(false);
    }

    public static void update(double dt) {
        if (!active) return;

        timer -= dt;
        if (timer <= 0) {
            active = false;
        }
    }

    public static void render(GraphicsContext gc, double x, double y) {
        if (!active) return;
        double alpha = (timer / flashDuration) * 0.85;
        double blast = Main.screen_width * blast_radius;
        gc.save();
        gc.setFill(Color.rgb(255, 140, 35, alpha));
        gc.fillOval(x-blast/2, y-blast/2, blast, blast);
        gc.restore();
    }

    public static void clear() {
        active = false;
        timer = 0.0;
    }

    public static void update_from_state(CampaignState state){
        if (state.upgrade_states.get(11) == 2) { blast_radius = 2.0; damage = 40; }
        else { blast_radius = 0.5; damage = 10; }
    }
}
