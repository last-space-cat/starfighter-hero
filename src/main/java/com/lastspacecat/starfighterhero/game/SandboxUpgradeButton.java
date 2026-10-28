package com.lastspacecat.starfighterhero.game;

import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.text.TextAlignment;

public class SandboxUpgradeButton extends StackPane {
    private int branch_id;
    private int unit_id;
    private final CampaignState current_state;
    private String text_base;
    private final Button button;

    public SandboxUpgradeButton(int id, CampaignState state){
        branch_id = id/4;
        unit_id = id%4;
        current_state = state;

        button = new Button();
        button.getStyleClass().add("menu-button");
        button.setPrefSize(Main.screen_width*0.08, Main.screen_width*0.08);
        button.setOnAction(event -> apply());
        button.wrapTextProperty().setValue(true);
        button.setTextAlignment(TextAlignment.CENTER);
        button.setFocusTraversable(false);
        if (branch_id == 0) { text_base = "Attack ";}
        if (branch_id == 1) { text_base = "Defence ";}
        if (branch_id == 2) { text_base = "Attack Boost ";}
        if (branch_id == 3) { text_base = "Defence Boost ";}

        button.setText(text_base + unit_id);
        getChildren().add(button);
    }

    private void apply(){
        unit_id++;
        unit_id = unit_id%5;
        button.setText(text_base + unit_id);

        int i = 0;
        while (i < unit_id){
            current_state.upgrade_states.set(branch_id*4 + i, 2);
            i++;
        }
        while (i < 4){
            current_state.upgrade_states.set(branch_id*4 + i, 0);
            i++;
        }
    }
}
