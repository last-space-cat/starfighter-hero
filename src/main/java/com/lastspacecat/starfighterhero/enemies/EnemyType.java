package com.lastspacecat.starfighterhero.enemies;
import javafx.scene.image.Image;

public enum EnemyType {
    BASE(150, 80, 80, "BASE_enemy.png", 1),
    SHOOTER(100, 100, 100, "SHOOTER_enemy.png", 1),
    SPEEDY(400, 80, 80, "SPEEDY_enemy.png", 1),
    KAMIKAZE(200, 80, 80, "KAMIKAZE_enemy.png", 3),
    ARMORED(60, 90, 90, "ARMORED_enemy.png", 10),
    SHIELD(120, 80, 80, "SHIELD_enemy.png", 1),
    MODULAR(110, 160, 160, "MODULAR_enemy.png", 3),
    BONUS(130, 80, 80, "BONUS_enemy.png", 3);

    private final double velocity;
    private final double width;
    private final double height;
    private final Image sprite;
    private final int maxHP;

    EnemyType(double velocity, int width, int height, String spritePath, int maxHP) {
        this.velocity = velocity;
        this.width = width;
        this.height = height;
        this.sprite = new Image(getClass().getResourceAsStream("/EnemiesSprite/" + spritePath));
        this.maxHP = maxHP;
    }

    public double get_velocity() { return velocity; }
    public double get_width() { return width; }
    public double get_height() { return height; }
    public Image get_sprite() { return sprite; }
    public int get_maxHP() {return maxHP; }
}
