package enemies;
import javafx.scene.paint.Color;

public enum EnemyType {
    BASE(150, 40, 40, Color.RED, 1),
    SHOOTER(100, 45, 40, Color.YELLOW, 1),
    SPEEDY(400, 35, 30, Color.LIGHTBLUE, 1),
    KAMIKAZE(200, 40, 40, Color.ORANGE, 3),
    ARMORED(60, 90, 90, Color.DARKGRAY, 10),
    SHIELD(120, 50, 50, Color.CYAN, 1),
    MODULAR(110, 60, 50, Color.PURPLE, 3),
    BONUS(130, 40, 40, Color.GOLD, 3);

    private final double velocity;
    private final double width;
    private final double height;
    private final Color color;
    private final int maxHP;

    EnemyType(double velocity, int width, int height, Color color, int maxHP) {
        this.velocity = velocity;
        this.width = width;
        this.height = height;
        this.color = color;
        this.maxHP = maxHP;
    }

    public double get_velocity() { return velocity; }
    public double get_width() { return width; }
    public double get_height() { return height; }
    public Color get_color() { return color; }
    public int get_maxHP() {return maxHP; }
}
