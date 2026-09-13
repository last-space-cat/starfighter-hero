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

        Main_Menu menu = new Main_Menu();
        Scene main_scene = new Scene(menu);

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


