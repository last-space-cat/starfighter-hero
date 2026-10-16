package com.lastspacecat.starfighterhero.game.upgrademenu;

import com.lastspacecat.starfighterhero.game.CampaignState;
import com.lastspacecat.starfighterhero.game.Main;
import com.lastspacecat.starfighterhero.game.ScreenManager;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.List;

public class UpgradeMenu extends Pane {
    final CampaignState current_state;
    private final Label description;
    private final Label header;
    private List<UpgradeButton> upgrade_list = new ArrayList<>();
    private int current_upgrade;
    private final UpgradePointCounter point_counter;

    public UpgradeMenu(ScreenManager handling_manager, CampaignState state){
        current_state = state;
        current_upgrade = -1;
        HBox box_button = new HBox(Main.screen_width*0.1);
        Button button_continue = init_up_button("Continue");
        Button button_menu = init_up_button("Main Menu");
        box_button.getChildren().addAll(button_continue, button_menu);
        getChildren().add(box_button);
        box_button.relocate(Main.screen_width*0.25, Main.screen_height*0.85);

        button_continue.setOnAction(event->{
            handling_manager.go_to_game(current_state.getCurrent_level());
            handling_manager.hide_win_screen();});
        button_menu.setOnAction(event->{handling_manager.go_to_main(); handling_manager.hide_win_screen();});

        BorderPane box_text = new BorderPane();

        header = new Label("");
        header.getStyleClass().add("up-screen-header");
        box_text.setTop(header);
        box_text.setAlignment(header, Pos.CENTER);

        description = new Label("");
        description.getStyleClass().add("up-screen-text");
        description.setWrapText(true);
        description.setMaxWidth(Main.screen_width*0.25);
        box_text.setCenter(description);

        Button button_confirm = init_up_button("Confirm");
        box_text.setBottom(button_confirm);
        box_text.setAlignment(button_confirm, Pos.CENTER);
        button_confirm.setOnAction(event->{if (current_upgrade != -1) confirm_upgrade();});

        box_text.setPrefSize(Main.screen_width*0.3, Main.screen_height*0.6);
        box_text.getStyleClass().add("up-desc-box");
        getChildren().add(box_text);
        box_text.relocate(Main.screen_width*0.65, Main.screen_height*0.2);

        Image back_image = new Image(getClass().getResourceAsStream("/main_menu_background.png"));
        BackgroundImage back = new BackgroundImage(back_image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, false, true)
        );
        setBackground(new Background(back));

        HBox box_trees = UpgradesLoader.load_upgrades(upgrade_list, this);
        getChildren().add(box_trees);
        box_trees.relocate(Main.screen_width*0.12, Main.screen_height*0.12);

        point_counter = new UpgradePointCounter();
        getChildren().add(point_counter);
        point_counter.relocate(Main.screen_width*0.65, Main.screen_height*0.1);
    }

    private Button init_up_button(String text){
        Button new_button = new Button(text);
        new_button.setPrefSize(
                Main.screen_width*0.2,
                Main.screen_height*0.1);
        new_button.getStyleClass().add("up-screen-button");
        return new_button;
    }

    public void set_desc(String name, String desc){
        header.setText(name);
        description.setText(desc);
    }

    public void set_current_upgrade(int new_upgrade){this.current_upgrade = new_upgrade;}

    public void update_from_state(){
        set_desc("", "");
        point_counter.set_counter(current_state);
        for (int i = 0; i < current_state.upgrade_states.size(); i++){
            if (current_state.upgrade_states.get(i) == 0) {
                upgrade_list.get(i).set_state(UpgradeButton.State.LOCKED);
            }
            if (current_state.upgrade_states.get(i) == 1) {
                upgrade_list.get(i).set_state(UpgradeButton.State.AVAILABLE);
            }
            if (current_state.upgrade_states.get(i) == 2) {
                upgrade_list.get(i).set_state(UpgradeButton.State.SELECTED);
            }
        }
    }

    private void confirm_upgrade(){
        if (current_state.getUpgrade_points() > 0 && upgrade_list.get(current_upgrade).get_state() == UpgradeButton.State.AVAILABLE){
            current_state.setUpgrade_points(current_state.getUpgrade_points()-1);
            current_state.upgrade_states.set(current_upgrade, 2);
            if (current_upgrade%4 != 3){
                current_state.upgrade_states.set(current_upgrade+1, 1);
            }
            update_from_state();
        }
    }
}