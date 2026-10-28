package com.lastspacecat.starfighterhero.game;

import com.lastspacecat.starfighterhero.enemies.*;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;


public class SandboxControls extends HBox {
    private int controls_state;
    private List<Enemy> enemy_queue;
    private SandboxField field;
    private final List<SandboxEnemyButton> enemy_buttons = new ArrayList<>();
    private final List<Button> upgrade_buttons = new ArrayList<>();
    private final List<Button> bonus_buttons = new ArrayList<>();

    public SandboxControls(List<Enemy> queue, SandboxField parent){
        enemy_queue = queue;
        field = parent;
        setAlignment(Pos.CENTER);
        for (EnemyType v : EnemyType.values()){
            SandboxEnemyButton button = new SandboxEnemyButton(v, enemy_queue, field);
            enemy_buttons.add(button);
        }

        getChildren().addAll(enemy_buttons);
        controls_state = 0;
    }


}
