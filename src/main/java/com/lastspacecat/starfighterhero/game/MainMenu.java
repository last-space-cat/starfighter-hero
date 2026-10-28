package com.lastspacecat.starfighterhero.game;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class MainMenu extends StackPane{
    private final CampaignState current_state;

    public MainMenu(ScreenManager handling_manager, CampaignState state) {
        current_state = state;
        Button button_play = init_menu_button("New Game");
        Button button_resume = init_menu_button("Resume");
        Button button_load = init_menu_button("Load Game");
        Button button_sand = init_menu_button("Sandbox Mode");
        Button button_quit = init_menu_button("Quit");

        button_play.setOnAction(event->
            {current_state.reset(); handling_manager.go_to_game(current_state.getCurrent_level());});
        button_resume.setOnAction(event->
                {if (current_state.getUpgrade_points() > 0){handling_manager.go_to_upgrades();}
                else {handling_manager.go_to_game(current_state.getCurrent_level());}});
        button_load.setOnAction(event-> handling_manager.go_to_load());
        button_sand.setOnAction(event->handling_manager.go_to_sandbox());
        button_quit.setOnAction(event-> Platform.exit());

        VBox box = new VBox(Main.screen_height*0.02);
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(button_play, button_resume, button_load, button_sand, button_quit);

        getChildren().add(box);
        setAlignment(Pos.CENTER);

        Image back_image = new Image(getClass().getResourceAsStream("/main_menu_background.png"));
        BackgroundImage back = new BackgroundImage(back_image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, false, true)
        );
        setBackground(new Background(back));
    }

    private Button init_menu_button(String text){
        Button new_button = new Button(text);
        new_button.setPrefSize(
                Main.screen_width*0.2,
                Main.screen_height*0.1);
        new_button.getStyleClass().add("menu-button");
        return new_button;
    }
}
