package game;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.input.KeyCombination;
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
        MainMenu main_menu = new MainMenu(manager);
        LoadMenu load_menu = new LoadMenu(manager);
        GameField game_field;
        game_field = new GameField(manager, Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight());
        PauseMenu pause_menu = new PauseMenu(manager);
        manager.set_main_reference(main_menu);
        manager.set_load_reference(load_menu);
        manager.set_game_reference(game_field);
        manager.set_pause_reference(pause_menu);
        //НЕ ЗАБЫВАТЬ ЗДЕСЬ ВЫЗЫВАТЬ СЕТТЕРЫ НА ВСЕ НУЖНЫЕ ЭКРАНЫ
        manager.go_to_main();

        String css = this.getClass().getResource("/main/resources/styles.css").toExternalForm();
        main_scene.getStylesheets().add(css);

        primaryStage.setTitle("Starfighter Hero");
        primaryStage.setScene(main_scene);
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        primaryStage.show();
    }

    public static void main(String[] args){
        Application.launch(args);
    }
}