package com.lastspacecat.starfighterhero.game.upgrademenu;

import com.lastspacecat.starfighterhero.game.Main;
import javafx.css.PseudoClass;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.InputStream;

import static com.lastspacecat.starfighterhero.game.upgrademenu.UpgradeButton.State.*;

public class UpgradeButton extends StackPane {
    public enum State {
        SELECTED, AVAILABLE, LOCKED
    }

    private static final PseudoClass selected = PseudoClass.getPseudoClass("selected");
    private static final PseudoClass available = PseudoClass.getPseudoClass("available");
    private static final PseudoClass locked = PseudoClass.getPseudoClass("locked");

    private State state;
    private String name;
    private String desc;
    private final Rectangle connector;

    public UpgradeButton(String new_name, String new_desc, UpgradeMenu parent, State initial, Rectangle connector, int id){
        getStyleClass().add("upgrade-button");

        this.connector = connector;

        Rectangle background = new Rectangle(Main.screen_height*0.12, Main.screen_height*0.12);
        background.getStyleClass().add("upgrade-background");
        getChildren().add(background);

        InputStream stream = getClass().getResourceAsStream("/upgrade_icons/icon_" + id + ".png");
        if (stream != null) {
            Image icon_image = new Image(stream);
            ImageView icon_view = new ImageView(icon_image);
            icon_view.setFitWidth(background.getWidth());
            icon_view.setFitHeight(background.getHeight());
            getChildren().add(icon_view);
        }

        Button select_button = new Button("");
        select_button.setPrefSize(Main.screen_height*0.12, Main.screen_height*0.12);
        select_button.setOnAction(event-> {
                    parent.set_current_upgrade(id);
                    parent.set_desc(name, desc);
                });
        select_button.getStyleClass().add("upgrade-select-button");
        getChildren().add(select_button);
        name = new_name;
        desc = new_desc;

        set_state(initial);
    }

    public State get_state() {return state;}

    public void set_state(State new_state){
        pseudoClassStateChanged(selected, new_state == SELECTED);
        pseudoClassStateChanged(available, new_state == AVAILABLE);
        pseudoClassStateChanged(locked, new_state == LOCKED);
        this.state = new_state;

        if (this.connector != null) {
            if (new_state == LOCKED) {
                connector.setFill(Color.web("#aaaaaa"));
            }
            if (new_state == SELECTED) {
                connector.setFill(Color.web("#90d5ff"));
            }
            if (new_state == AVAILABLE) {
                connector.setFill(Color.web("#88e788"));
            }
        }

    }
}
