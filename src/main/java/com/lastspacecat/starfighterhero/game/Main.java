package com.lastspacecat.starfighterhero.game;

import javafx.application.Application;
import javafx.scene.input.KeyCombination;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class Main extends Application{
    public static final double screen_width = Screen.getPrimary().getBounds().getWidth();
    public static final double screen_height = Screen.getPrimary().getBounds().getHeight();

    @Override
    public void start (Stage primaryStage) throws IOException {
        primaryStage.setX(Screen.getPrimary().getBounds().getMinX());
        primaryStage.setY(Screen.getPrimary().getBounds().getMinY());
        primaryStage.setWidth(screen_width);
        primaryStage.setHeight(screen_height);

        Scene main_scene = new Scene(new StackPane());
        ScreenManager manager = new ScreenManager(main_scene);
        MainMenu main_menu = new MainMenu(manager);
        LoadMenu load_menu = new LoadMenu(manager);
        GameField game_field;
        game_field = new GameField(manager, screen_width, screen_height);
        PauseMenu pause_menu = new PauseMenu(manager);
        DefeatScreen defeat_menu = new DefeatScreen(manager);
        VictoryScreen victory_menu = new VictoryScreen(manager);
        manager.set_main_reference(main_menu);
        manager.set_load_reference(load_menu);
        manager.set_game_reference(game_field);
        manager.set_pause_reference(pause_menu);
        manager.set_def_reference(defeat_menu);
        manager.set_win_reference(victory_menu);
        //НЕ ЗАБЫВАТЬ ЗДЕСЬ ВЫЗЫВАТЬ СЕТТЕРЫ НА ВСЕ НУЖНЫЕ ЭКРАНЫ
        manager.go_to_main();

        String css = this.getClass().getResource("/styles.css").toExternalForm();
        main_scene.getStylesheets().add(css);

        primaryStage.setTitle("Starfighter Hero");
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        primaryStage.setScene(main_scene);
        primaryStage.show();
    }

    public static void main(String[] args){
        Application.launch(args);
    }
}

