import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class Main extends Application{

    @Override
    public void start (Stage primaryStage) throws IOException {
        Rectangle2D screen_bounds = Screen.getPrimary().getBounds();
        primaryStage.setX(screen_bounds.getMinX());
        primaryStage.setY(screen_bounds.getMinY());
        primaryStage.setWidth(screen_bounds.getWidth());
        primaryStage.setHeight(screen_bounds.getHeight());

        Scene main_scene = new Scene(new StackPane());
        ScreenManager manager = new ScreenManager(main_scene);
        Main_Menu main_menu = new Main_Menu(manager);
        LoadMenu load_menu = new LoadMenu(manager);
        manager.set_main_reference(main_menu);
        manager.set_load_reference(load_menu);
        manager.go_to_main();

        String css = this.getClass().getResource("main/resources/styles.css").toExternalForm();
        main_scene.getStylesheets().add(css);

        primaryStage.setTitle("Starfighter Hero");
        primaryStage.setScene(main_scene);
        primaryStage.show();
    }

    public static void main(String[] args){
        Application.launch(args);
    }
}