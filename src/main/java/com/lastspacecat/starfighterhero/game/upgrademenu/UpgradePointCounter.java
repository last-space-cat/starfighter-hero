package com.lastspacecat.starfighterhero.game.upgrademenu;

import com.lastspacecat.starfighterhero.game.CampaignState;
import com.lastspacecat.starfighterhero.game.Main;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class UpgradePointCounter extends StackPane {
    private Label counter;

    public UpgradePointCounter(){
        getStyleClass().add("upgrade-point-counter");
        setPrefSize(Main.screen_width*0.3, Main.screen_height*0.08);
        counter = new Label("Очки улучшений: N/A");
        counter.getStyleClass().add("upgrade-counter-text");
        getChildren().add(counter);
    }

    public void set_counter(CampaignState state){
        counter.setText("Очки улучшений: " + state.getUpgrade_points());
    }
}
