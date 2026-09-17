package game;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;

import java.util.ArrayList;
import java.util.List;

public class HUD extends BorderPane {
    private final HBox lives;
    private final List<ImageView> life_views;
    private PlayerShip player;
    private Image life_image = new Image(getClass().getResourceAsStream("/main/resources/life_symbol.png"));

    HUD(PlayerShip player_ref){
        this.player = player_ref;
        lives = new HBox(30);
        life_views = new ArrayList<ImageView>();

        for (int i = 0; i < player.get_current_HP(); i++){
            ImageView current_view = new ImageView(life_image);
            current_view.setFitWidth(Screen.getPrimary().getBounds().getWidth()*0.05);
            current_view.setFitHeight(Screen.getPrimary().getBounds().getWidth()*0.05);
            life_views.add(current_view);
        }

        lives.getChildren().addAll(life_views);
        lives.setAlignment(Pos.TOP_LEFT);
        lives.setPadding(new Insets(20, 0, 0, 20));

        getChildren().add(lives);
    }

    public void update(){
        while (player.get_current_HP() > life_views.size()){
            ImageView current_view = new ImageView(life_image);
            current_view.setFitWidth(Screen.getPrimary().getBounds().getWidth()*0.05);
            current_view.setFitHeight(Screen.getPrimary().getBounds().getWidth()*0.05);
            life_views.add(current_view);
        }

        while (player.get_current_HP() < life_views.size() && !life_views.isEmpty()) {
            life_views.removeLast();
        }

        lives.getChildren().clear();
        lives.getChildren().addAll(life_views);
    }
}