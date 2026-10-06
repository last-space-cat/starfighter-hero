package com.lastspacecat.starfighterhero.enemies;
import com.lastspacecat.starfighterhero.game.Main;
import javafx.scene.image.Image;

public enum EnemyType {
    BASE(Main.screen_width*0.08, Main.screen_width*0.05, Main.screen_width*0.05, "BASE_enemy.png", 1),
    SHOOTER(Main.screen_width*0.05, Main.screen_width*0.05, Main.screen_width*0.05, "SHOOTER_enemy.png", 1),
    SPEEDY(Main.screen_width*0.16, Main.screen_width*0.05, Main.screen_width*0.05, "SPEEDY_enemy.png", 1),
    KAMIKAZE(Main.screen_width*0.10, Main.screen_width*0.05, Main.screen_width*0.05, "KAMIKAZE_enemy.png", 3),
    ARMORED(Main.screen_width*0.08, Main.screen_width*0.12, Main.screen_width*0.12, "ARMORED_enemy.png", 10),
    SHIELD(Main.screen_width*0.08, Main.screen_width*0.08, Main.screen_width*0.08, "SHIELD_enemy.png", 1),
    MODULAR(Main.screen_width*0.08, Main.screen_width*0.12, Main.screen_width*0.12, "MODULAR_enemy.png", 3),
    BONUS(Main.screen_width*0.10, Main.screen_width*0.08, Main.screen_width*0.08, "BONUS_enemy.png", 3);

    private final double velocity;
    private final double width;
    private final double height;
    private final Image sprite;
    private final int maxHP;

    EnemyType(double velocity, double width, double height, String spritePath, int maxHP) {
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
