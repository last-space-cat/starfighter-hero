package com.lastspacecat.starfighterhero.game;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;
import javafx.stage.Screen;

public class DefeatScreen extends StackPane{

    public DefeatScreen(ScreenManager handling_manager) {
        Rectangle defeat_background = new Rectangle(
                Screen.getPrimary().getBounds().getWidth()*0.6,
                Screen.getPrimary().getBounds().getHeight()*0.5
        );
        defeat_background.setFill(Color.web("#ff240088"));
        defeat_background.setStroke(Color.web("#ff2400"));
        defeat_background.setStrokeWidth(5);
        defeat_background.setStrokeType(StrokeType.INSIDE);
        getChildren().add(defeat_background);

        Text defeat_header = new Text("YOU LOST!");
        defeat_header.getStyleClass().add("end-screen-header");
        setAlignment(defeat_header, Pos.TOP_CENTER);
        setMargin(defeat_header, new Insets(80, 0, 0, 0));
        getChildren().add(defeat_header);

        HBox box = new HBox(Screen.getPrimary().getBounds().getWidth()*0.1);
        Button button_retry = init_def_button("Retry");
        Button button_menu = init_def_button("Main Menu");
        box.getChildren().addAll(button_retry, button_menu);
        box.setAlignment(Pos.BOTTOM_CENTER);
        box.setPadding(new Insets(0, 0, 30, 0));
        getChildren().add(box);

        button_retry.setOnAction(event->{handling_manager.go_to_game(); handling_manager.hide_def_screen();});
        button_menu.setOnAction(event->{handling_manager.go_to_main(); handling_manager.hide_def_screen();});
    }

    private Button init_def_button(String text){
        Button new_button = new Button(text);
        new_button.setPrefSize(
                Screen.getPrimary().getBounds().getWidth()*0.2,
                Screen.getPrimary().getBounds().getHeight()*0.1);
        new_button.getStyleClass().add("end-screen-button");
        return new_button;
    }
}
