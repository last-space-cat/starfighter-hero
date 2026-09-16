package game;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Screen;

public class PauseMenu extends StackPane {

    PauseMenu(ScreenManager handling_manager){
        Rectangle pause_background = new Rectangle(
                Screen.getPrimary().getBounds().getWidth()*0.3,
                Screen.getPrimary().getBounds().getHeight()*0.5
        );
        pause_background.setFill(Color.web("#88e78888"));
        pause_background.setStroke(Color.web("#88e788"));
        pause_background.setStrokeWidth(5);
        pause_background.setStrokeType(StrokeType.INSIDE);
        getChildren().add(pause_background);

        VBox box = new VBox(30);
        Button button_resume = init_pause_button("Resume");
        button_resume.setOnAction(event->handling_manager.toggle_pause());
        Button button_menu = init_pause_button("Main Menu");
        button_menu.setOnAction(event->handling_manager.go_to_main());
        box.getChildren().addAll(button_resume, button_menu);
        box.setAlignment(Pos.CENTER);
        getChildren().add(box);
    }

    private Button init_pause_button(String text){
        Button new_button = new Button(text);
        new_button.setPrefSize(
                Screen.getPrimary().getBounds().getWidth()*0.2,
                Screen.getPrimary().getBounds().getHeight()*0.1);
        new_button.getStyleClass().add("menu-button");
        return new_button;
    }
}
