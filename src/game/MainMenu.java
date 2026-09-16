package game;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Screen;

public class MainMenu extends StackPane{
    public MainMenu(ScreenManager handling_manager) {
        Button button_play = init_menu_button("New Game");
        Button button_load = init_menu_button("Load Game");
        Button button_settings = init_menu_button("Settings");
        Button button_quit = init_menu_button("Quit");

        //ДОБАВИТЬ ЛОГИКУ НА 3 КНОПКУ
        button_play.setOnAction(event-> handling_manager.go_to_game());
        button_load.setOnAction(event-> handling_manager.go_to_load());
        button_quit.setOnAction(event-> Platform.exit());

        VBox box = new VBox(20);
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(button_play, button_load, button_settings, button_quit);

        getChildren().add(box);
        setAlignment(Pos.CENTER);

        Image back_image = new Image(getClass().getResourceAsStream("/main_menu/resources/main_menu_background.png"));
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
                Screen.getPrimary().getBounds().getWidth()*0.2,
                Screen.getPrimary().getBounds().getHeight()*0.1);
        new_button.getStyleClass().add("menu-button");
        return new_button;
    }
}
