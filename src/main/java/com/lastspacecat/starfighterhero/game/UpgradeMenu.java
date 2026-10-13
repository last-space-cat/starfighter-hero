package com.lastspacecat.starfighterhero.game;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;

public class UpgradeMenu extends Pane {
    public UpgradeMenu(ScreenManager handling_manager, CampaignState current_state){
        HBox box = new HBox(Screen.getPrimary().getBounds().getWidth()*0.1);
        Button button_continue = init_up_button("Continue");
        Button button_menu = init_up_button("Main Menu");
        box.getChildren().addAll(button_continue, button_menu);
        box.setAlignment(Pos.BOTTOM_CENTER);
        box.setPadding(new Insets(0, 0, Main.screen_height*0.04, 0));
        getChildren().add(box);

        button_continue.setOnAction(event->{
            handling_manager.go_to_game(current_state.getCurrent_level());
            handling_manager.hide_win_screen();});
        button_menu.setOnAction(event->{handling_manager.go_to_main(); handling_manager.hide_win_screen();});
    }

    private Button init_up_button(String text){
        Button new_button = new Button(text);
        new_button.setPrefSize(
                Main.screen_width*0.2,
                Main.screen_height*0.1);
        new_button.getStyleClass().add("win-screen-button");
        return new_button;
    }
}
