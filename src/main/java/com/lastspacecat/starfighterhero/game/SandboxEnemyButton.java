package com.lastspacecat.starfighterhero.game;

import com.lastspacecat.starfighterhero.enemies.*;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.util.List;

public class SandboxEnemyButton extends StackPane {
    private List<Enemy> enemy_queue;
    private SandboxField field;

    public SandboxEnemyButton(EnemyType v, List<Enemy> queue, SandboxField parent){
        enemy_queue = queue;
        field = parent;
        Image image = v.get_sprite();
        ImageView image_view = new ImageView(image);
        image_view.setFitWidth(Main.screen_width*0.05);
        image_view.setFitHeight(Main.screen_width*0.05);
        image_view.setPreserveRatio(true);
        image_view.setMouseTransparent(true);

        Button button = new Button();
        button.getStyleClass().add("menu-button");
        button.setPrefSize(Main.screen_width*0.08, Main.screen_width*0.08);
        button.setOnAction(event -> spawn(v));
        button.setFocusTraversable(false);

        getChildren().add(button);
        getChildren().add(image_view);
    }

    private void spawn(EnemyType v){
        if (v != EnemyType.SHOOTER && v != EnemyType.MODULAR &&
                v != EnemyType.KAMIKAZE && v != EnemyType.SHIELD &&
                v != EnemyType.BOSS_FRIGATE && v != EnemyType.BOSS_CRUISER &&
                v != EnemyType.BOSS_DREADNOUGHT) {
            Enemy enemy = new Enemy(
                    Main.screen_width* 0.95,
                    Main.screen_height* 0.5,
                    v);
            enemy_queue.add(enemy);
        }
        else {
            if (v == EnemyType.MODULAR) {
                enemy_queue.add(new ModularEnemy(Main.screen_width * 0.95, Main.screen_height * 0.5, v, enemy_queue));
            }
            if (v == EnemyType.SHIELD) {
                enemy_queue.add(new ShieldEnemy(Main.screen_width * 0.95, Main.screen_height * 0.5, v));
            }
            if (v == EnemyType.SHOOTER) {
                ShooterEnemy enemy = new ShooterEnemy(Main.screen_width * 0.95, Main.screen_height * 0.5, v); enemy.setLaserList(field.getLasers()); enemy_queue.add(enemy);
            }
            if (v == EnemyType.KAMIKAZE) {
                enemy_queue.add(new KamikazeEnemy(Main.screen_width * 0.95, Main.screen_height * 0.5, v));
            }
            if (v == EnemyType.BOSS_FRIGATE) {
                enemy_queue.add(new BossFrigate(Main.screen_width * 0.95, Main.screen_height * 0.5, v, field.getRockets()));
            }
            if (v == EnemyType.BOSS_CRUISER) {
                enemy_queue.add(new BossCruiser(Main.screen_width * 0.95, Main.screen_height * 0.5, v, field.getZones()));
            }
            if (v == EnemyType.BOSS_DREADNOUGHT) {
                enemy_queue.add(new BossDreadnought(Main.screen_width * 0.95, Main.screen_height * 0.5, v, field.getRockets(), field.getZones(), field.getSpawnQueue()));
            }
        }
    }
}
