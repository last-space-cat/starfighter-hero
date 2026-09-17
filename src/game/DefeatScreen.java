package game;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Screen;

public class DefeatScreen extends StackPane{

    public DefeatScreen(ScreenManager handling_manager) {
        Rectangle defeat_background = new Rectangle(
                Screen.getPrimary().getBounds().getWidth()*0.6,
                Screen.getPrimary().getBounds().getHeight()*0.5
        );
        defeat_background.setFill(Color.web("#88e78888"));
        defeat_background.setStroke(Color.web("#88e788"));
        defeat_background.setStrokeWidth(5);
        defeat_background.setStrokeType(StrokeType.INSIDE);
        getChildren().add(defeat_background);

        Label defeat_header = new Label("YOU LOST!");
        defeat_header.getStyleClass().add("end-screen-header");
        defeat_header.setAlignment(Pos.TOP_CENTER);
        defeat_header.setPadding(new Insets(0, 0, Screen.getPrimary().getBounds().getWidth()*0.1, 0));
        getChildren().add(defeat_header);

        HBox box = new HBox(Screen.getPrimary().getBounds().getWidth()*0.1);
        Button button_retry = init_def_button("Retry");
        Button button_menu = init_def_button("Main Menu");
        box.getChildren().addAll(button_retry, button_menu);
        box.setAlignment(Pos.BOTTOM_CENTER);
        box.setPadding(new Insets(0, 0, 30, 0));
        getChildren().add(box);

        button_retry.setOnAction(event->handling_manager.go_to_game());
        button_menu.setOnAction(event->handling_manager.go_to_main());
    }

    private Button init_def_button(String text){
        Button new_button = new Button(text);
        new_button.setPrefSize(
                Screen.getPrimary().getBounds().getWidth()*0.2,
                Screen.getPrimary().getBounds().getHeight()*0.1);
        new_button.getStyleClass().add("menu-button");
        return new_button;
    }
}
