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

public class VictoryScreen extends StackPane {

    public VictoryScreen(ScreenManager handling_manager){
        Rectangle victory_background = new Rectangle(
                Main.screen_width*0.6,
                Main.screen_height*0.5
        );
        victory_background.setFill(Color.web("#88e78888"));
        victory_background.setStroke(Color.web("#88e788"));
        victory_background.setStrokeWidth(5);
        victory_background.setStrokeType(StrokeType.INSIDE);
        getChildren().add(victory_background);

        Text victory_header = new Text("LEVEL CLEARED!");
        victory_header.getStyleClass().add("win-screen-header");
        setAlignment(victory_header, Pos.TOP_CENTER);
        setMargin(victory_header, new Insets(Main.screen_height*0.1, 0, 0, 0));
        getChildren().add(victory_header);

        HBox box = new HBox(Screen.getPrimary().getBounds().getWidth()*0.1);
        Button button_continue = init_win_button("Continue");
        Button button_menu = init_win_button("Main Menu");
        box.getChildren().addAll(button_continue, button_menu);
        box.setAlignment(Pos.BOTTOM_CENTER);
        box.setPadding(new Insets(0, 0, Main.screen_height*0.04, 0));
        getChildren().add(box);

        button_continue.setOnAction(event->{handling_manager.go_to_game(1); handling_manager.hide_win_screen();});
        button_menu.setOnAction(event->{handling_manager.go_to_main(); handling_manager.hide_win_screen();});
    }

    private Button init_win_button(String text){
        Button new_button = new Button(text);
        new_button.setPrefSize(
                Main.screen_width*0.2,
                Main.screen_height*0.1);
        new_button.getStyleClass().add("win-screen-button");
        return new_button;
    }
}
