package com.lastspacecat.starfighterhero.game;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class SandboxBonusButton extends StackPane {
    SandboxField field;

    public SandboxBonusButton(DroppedBonus.BonusType v, SandboxField parent){
        field = parent;
        Image image = v.getSprite();
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

    private void spawn(DroppedBonus.BonusType v){
        field.getBonusManager().spawnBonus(Main.screen_width * 0.95, Main.screen_height * 0.5, v);
    }
}
