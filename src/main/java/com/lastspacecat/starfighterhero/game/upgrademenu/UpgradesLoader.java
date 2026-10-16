package com.lastspacecat.starfighterhero.game.upgrademenu;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lastspacecat.starfighterhero.game.Main;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.InputStream;
import java.util.List;

public class UpgradesLoader {
    private UpgradesLoader(){}

    public static HBox load_upgrades(List<UpgradeButton> upgrade_list, UpgradeMenu parent){
        HBox box_trees = new HBox(Main.screen_width*0.06);

        try (InputStream is = UpgradesLoader.class.getResourceAsStream("/upgrades.json")){
            JsonNode tree_root = new ObjectMapper().readTree(is);
            int id = 0;
            for (JsonNode node : tree_root.get("trees")){
                boolean is_first = true;
                VBox current_tree = new VBox();
                current_tree.setAlignment(Pos.CENTER);
                for (JsonNode upgrade : node.get("upgrades")){
                    UpgradeButton.State initial;
                    Rectangle connector;
                    if (!is_first){
                        connector = new Rectangle(Main.screen_width*0.002, Main.screen_height*0.055);
                        connector.setFill(Color.web("#ffffff"));
                        current_tree.getChildren().add(connector);
                        initial = UpgradeButton.State.LOCKED;
                    }
                    else {
                        connector = null;
                        is_first = false;
                        initial = UpgradeButton.State.AVAILABLE;
                    }
                    UpgradeButton current_button = new UpgradeButton(
                            upgrade.get("name").asText(), upgrade.get("desc").asText(), parent, initial, connector, id);
                    current_tree.getChildren().add(current_button);
                    upgrade_list.add(current_button);
                    id++;
                }
                box_trees.getChildren().add(current_tree);
            }
        }
        catch (Exception e){
            throw new RuntimeException("Failed to load upgrade tree");
        }
        return box_trees;
    }
}
