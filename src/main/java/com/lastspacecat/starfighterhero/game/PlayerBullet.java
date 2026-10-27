package com.lastspacecat.starfighterhero.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Screen;

public class PlayerBullet {
    private double x, y;
    private double dy = 0;
    private double dx = 1;
    private final double velocity = Main.screen_width*0.33;
    public static double width = Main.screen_height*0.014;
    public static double height = Main.screen_height*0.006;

    public PlayerBullet(double starting_x, double starting_y, double dy, double dx) {
        this.x = starting_x;
        this.y = starting_y;
        this.dy = dy;
        this.dx = dx;
    }

    public double get_x() { return x; }
    public double get_y() { return y; }

    public void update(double dt) {x += velocity * dt * dx;  y += dy * velocity * 0.25 * dt;}

    public void render(GraphicsContext graph_context) {
        graph_context.setFill(Color.YELLOW);
        graph_context.fillRect(x, y, width, height);
    }

    public boolean is_out_of_bounds() {
        return x > Screen.getPrimary().getBounds().getWidth();
    }

    public static void update_from_state(CampaignState state){
        if (state.upgrade_states.get(1) == 2){ height = Main.screen_height*0.018; }
        else { height = Main.screen_height*0.006; }
    }
}