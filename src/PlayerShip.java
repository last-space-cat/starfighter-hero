import javafx.scene.input.KeyCode;

import java.util.Set;

public class PlayerShip {
    private double x, y;
    private double velocity;
    public static int width = 120, height = 40;

    PlayerShip(double starting_x, double starting_y){ //ПОЗЖЕ НУЖНО ДОБАВИТЬ В КОНСТРУКТОР Campaign_State для установки апгрейдов
        x = starting_x;
        y = starting_y;
        velocity = 40;
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

        x += dx*velocity;
        y += dy*velocity;

        x = Math.clamp(x, 0, 1280 - width);
        y = Math.clamp(y, 0, 650 - height);
    }


}
