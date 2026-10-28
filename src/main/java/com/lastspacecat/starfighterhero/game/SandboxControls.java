package com.lastspacecat.starfighterhero.game;

import com.lastspacecat.starfighterhero.enemies.*;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;


public class SandboxControls extends HBox {
    private int controls_state;
    private List<Enemy> enemy_queue;
    private SandboxField field;
    private HBox main_buttons;
    private final List<SandboxEnemyButton> enemy_buttons = new ArrayList<>();
    private final List<SandboxUpgradeButton> upgrade_buttons = new ArrayList<>();
    private final List<SandboxBonusButton> bonus_buttons = new ArrayList<>();

    public SandboxControls(List<Enemy> queue, SandboxField parent, CampaignState state){
        main_buttons = new HBox();
        getChildren().add(main_buttons);
        Button scroll = new Button(">>");
        scroll.getStyleClass().add("menu-button");
        scroll.setFocusTraversable(false);
        scroll.setPrefSize(Main.screen_width*0.08, Main.screen_width*0.08);
        scroll.setOnAction(event->scroll_through_state());
        getChildren().add(scroll);

        enemy_queue = queue;
        field = parent;
        setAlignment(Pos.CENTER);
        for (EnemyType v : EnemyType.values()){
            SandboxEnemyButton button = new SandboxEnemyButton(v, enemy_queue, field);
            enemy_buttons.add(button);
        }

        for (int i = 0; i < 13; i=i+4){
            SandboxUpgradeButton button = new SandboxUpgradeButton(i, state);
            upgrade_buttons.add(button);
        }

        for (DroppedBonus.BonusType v : DroppedBonus.BonusType.values()){
            SandboxBonusButton button = new SandboxBonusButton(v, field);
            bonus_buttons.add(button);
        }

        main_buttons.getChildren().addAll(enemy_buttons);
        controls_state = 0;
    }

    private void scroll_through_state(){
        controls_state++;
        controls_state = controls_state%3;

        if (controls_state == 0){
            main_buttons.getChildren().removeAll(upgrade_buttons);
            main_buttons.getChildren().removeAll(bonus_buttons);
            main_buttons.getChildren().addAll(enemy_buttons);
        }

        if (controls_state == 1){
            main_buttons.getChildren().removeAll(enemy_buttons);
            main_buttons.getChildren().removeAll(bonus_buttons);
            main_buttons.getChildren().addAll(upgrade_buttons);
        }

        if (controls_state == 2){
            main_buttons.getChildren().removeAll(upgrade_buttons);
            main_buttons.getChildren().removeAll(enemy_buttons);
            main_buttons.getChildren().addAll(bonus_buttons);
        }
    }
}
