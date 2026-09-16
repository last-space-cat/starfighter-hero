import javafx.scene.input.KeyCode;
import javafx.stage.Screen;

import java.util.Set;

//Корабль игрока. Не использует тип, поскольку он у нас один.

public class PlayerShip {
    private double x, y;
    private double velocity;
    public static int width = 100, height = 50;
    public double clamp_w;
    public double clamp_h;

    PlayerShip(double starting_x, double starting_y){ //ПОЗЖЕ НУЖНО ДОБАВИТЬ В КОНСТРУКТОР Campaign_State для установки апгрейдов
        x = starting_x;
        y = starting_y;
        velocity = 20;
        clamp_w = Screen.getPrimary().getBounds().getWidth() - width;
        clamp_h = Screen.getPrimary().getBounds().getHeight() - height;
    }

    public double get_x(){return x;}

    public double get_y(){return y;}

    public void update(double dt, Set<KeyCode> pressed_keys){
        double dx = 0, dy = 0;

        if (pressed_keys.contains(KeyCode.W) || pressed_keys.contains(KeyCode.UP))    dy -= 1;
        if (pressed_keys.contains(KeyCode.S) || pressed_keys.contains(KeyCode.DOWN))  dy += 1;
        if (pressed_keys.contains(KeyCode.A) || pressed_keys.contains(KeyCode.LEFT))  dx -= 1;
        if (pressed_keys.contains(KeyCode.D) || pressed_keys.contains(KeyCode.RIGHT)) dx += 1;

        if (dx != 0 && dy != 0){
            dx *= 1/Math.sqrt(2);
            dy *= 1/Math.sqrt(2);
        }

        x += dx*velocity*dt;
        y += dy*velocity*dt;

        x = Math.clamp(x, 0, clamp_w);
        y = Math.clamp(y, 0, clamp_h);
    }


}
