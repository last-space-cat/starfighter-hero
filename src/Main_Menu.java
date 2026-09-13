import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;


public class Main_Menu extends StackPane{
    public Main_Menu() {
        Button button_play = init_menu_button("New Game");
        Button button_load = init_menu_button("Load Game");
        Button button_credits = init_menu_button("Credits");
        Button button_quit = init_menu_button("Quit");

        VBox box = new VBox(20);
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(button_play, button_load, button_credits, button_quit);

        getChildren().add(box);
        setAlignment(Pos.CENTER);

        Image back_image = new Image(getClass().getResourceAsStream("main_menu/resources/main_menu_background.png"));
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
        new_button.setPrefSize(200, 50);
        new_button.setStyle("-fx-font-size: 20;" +
                "-fx-font-family: 'Courier New';" +
                "-fx-background-color: #000000;" +
                "-fx-border-width: 3;" +
                "-fx-border-color: #ffffff;" +
                "-fx-text-fill: #ffffff;"
        );
        return new_button;
    }
}
