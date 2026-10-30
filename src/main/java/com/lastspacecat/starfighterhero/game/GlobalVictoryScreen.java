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
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;

public class GlobalVictoryScreen extends StackPane {
    public GlobalVictoryScreen(ScreenManager handling_manager, CampaignState state){
        Rectangle victory_background = new Rectangle(
                Main.screen_width*0.6,
                Main.screen_height*0.5
        );
        victory_background.setFill(Color.web("#88e78888"));
        victory_background.setStroke(Color.web("#88e788"));
        victory_background.setStrokeWidth(5);
        victory_background.setStrokeType(StrokeType.INSIDE);
        getChildren().add(victory_background);

        Text victory_header = new Text("Congratulations, Starfighter Hero. You have won the game.");
        victory_header.setWrappingWidth(Main.screen_width*0.4);
        victory_header.getStyleClass().add("global-win-screen-header");
        victory_header.setTextAlignment(TextAlignment.CENTER);
        setAlignment(victory_header, Pos.TOP_CENTER);
        setMargin(victory_header, new Insets(Main.screen_height*0.1, 0, 0, 0));
        getChildren().add(victory_header);

        HBox box = new HBox(Screen.getPrimary().getBounds().getWidth()*0.1);
        Button button_menu = init_win_button("Main Menu");
        box.getChildren().add(button_menu);
        box.setAlignment(Pos.BOTTOM_CENTER);
        box.setPadding(new Insets(0, 0, Main.screen_height*0.04, 0));
        getChildren().add(box);

        button_menu.setOnAction(event->{handling_manager.go_to_main(); handling_manager.hide_global_win_screen(); state.reset(); });
    }

    private Button init_win_button(String text){
        Button new_button = new Button(text);
        new_button.setPrefSize(
                Main.screen_width*0.15,
                Main.screen_height*0.1);
        new_button.getStyleClass().add("win-screen-button");
        return new_button;
    }
}
