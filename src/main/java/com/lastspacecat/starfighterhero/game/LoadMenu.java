package com.lastspacecat.starfighterhero.game;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class LoadMenu extends StackPane {
    private int chosen_save;

    public LoadMenu(ScreenManager handling_manager, CampaignState state){
        Label load_header = new Label("");
        load_header.getStyleClass().add("load-menu-header");

        chosen_save = -1;
        Button slot_1 = init_menu_button("Save 1");
        Button slot_2 = init_menu_button("Save 2");
        Button slot_3 = init_menu_button("Save 3");
        Button back_button = init_menu_button("Back");
        Button save_button = init_menu_button("Save");
        Button load_button = init_menu_button("Load");

        slot_1.setOnAction(event-> {chosen_save = 0; load_header.setText("Selected slot: " + (chosen_save + 1));});
        slot_2.setOnAction(event-> {chosen_save = 1; load_header.setText("Selected slot: " + (chosen_save + 1));});
        slot_3.setOnAction(event-> {chosen_save = 2; load_header.setText("Selected slot: " + (chosen_save + 1));});
        back_button.setOnAction(event -> handling_manager.go_to_main());
        save_button.setOnAction(event -> { state.save_state(chosen_save); load_header.setText("Saved in slot: " + (chosen_save + 1));});
        load_button.setOnAction(event -> { state.load_state(chosen_save); load_header.setText("Loaded from slot: " + (chosen_save + 1));});

        HBox hbox = new HBox(Main.screen_width*0.1);
        hbox.setAlignment(Pos.BOTTOM_CENTER);
        hbox.getChildren().addAll(back_button, save_button, load_button);

        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(load_header, slot_1, slot_2, slot_3, hbox);
        VBox.setMargin(hbox, new Insets(60, 0, 0, 0));

        getChildren().add(vbox);
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
