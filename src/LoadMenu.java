import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

//Меню загрузки сохранений. Позже нужно добавить функционал загрузки из файлов.

public class LoadMenu extends StackPane {
    public LoadMenu(ScreenManager handling_manager){
        Button slot_1 = init_menu_button("Empty save");
        Button slot_2 = init_menu_button("Empty save");
        Button slot_3 = init_menu_button("Empty save");
        Button back_button = init_menu_button("Back");

        back_button.setOnAction(event -> handling_manager.go_to_main());

        VBox box = new VBox(20);
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(slot_1, slot_2, slot_3, back_button);
        VBox.setMargin(back_button, new Insets(30, 0, 0, 0));

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
        new_button.getStyleClass().add("menu-button");
        return new_button;
    } // ВРЕМЕННЫЙ МЕТОД, ПОЗЖЕ НАДО ДОБАВИТЬ ФУНКЦИОНАЛ ЗАГРУЗКИ СЕЙВОВ ИЗ ФАЙЛА
}