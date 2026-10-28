package com.lastspacecat.starfighterhero.game;

import com.lastspacecat.starfighterhero.game.upgrademenu.UpgradeMenu;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;

public class ScreenManager {
    private final Scene scene;
    private Parent main_reference;
    private Parent load_reference;
    private GameField game_reference;
    private PauseMenu pause_reference;
    private DefeatScreen def_reference;
    private VictoryScreen win_reference;
    private UpgradeMenu up_reference;
    private SandboxField sand_reference;
    // КОГДА ЭКРАНОВ СТАНЕТ БОЛЬШЕ, НУЖНО ДОБАВИТЬ НА КАЖДЫЙ ИЗ НИХ ССЫЛКУ ЗДЕСЬ

    public ScreenManager(Scene scene){
        this.scene = scene;
    }

    public void set_main_reference(Parent new_ref){this.main_reference = new_ref; }
    public void set_load_reference(Parent new_ref){this.load_reference = new_ref; }
    public void set_game_reference(GameField new_ref){this.game_reference = new_ref; }
    public void set_pause_reference(PauseMenu new_ref){this.pause_reference = new_ref; }
    public void set_def_reference(DefeatScreen new_ref){this.def_reference = new_ref; }
    public void set_win_reference(VictoryScreen new_ref){this.win_reference = new_ref; }
    public void set_upgrade_reference(UpgradeMenu new_ref){this.up_reference = new_ref; }
    public void set_sand_reference(SandboxField new_ref){this.sand_reference = new_ref; }

    public void go_to_main(){
        this.scene.setRoot(main_reference);
        if (!game_reference.is_paused()){
            game_reference.pause();
        }
    }
    public void go_to_load(){this.scene.setRoot(load_reference); }

    public void go_to_game(int level){
        this.scene.setRoot(game_reference);
        game_reference.requestFocus();
        game_reference.start_level("/levels/level_" + level + ".json");
    }

    public void go_to_upgrades(){
        this.scene.setRoot(up_reference);
        up_reference.update_from_state();
    }

    public void toggle_pause(){
        if (this.scene.getRoot() == game_reference) {
            if (!game_reference.is_paused()) {
                game_reference.getChildren().add(pause_reference);
                pause_reference.relocate(
                        Screen.getPrimary().getBounds().getWidth() * 0.35,
                        Screen.getPrimary().getBounds().getHeight() * 0.2
                );
                game_reference.pause();
            } else {
                game_reference.getChildren().remove(pause_reference);
                game_reference.requestFocus();
                game_reference.unpause();
            }
        }

        if (this.scene.getRoot() == sand_reference) {
            if (!sand_reference.is_paused()) {
                sand_reference.getChildren().add(pause_reference);
                pause_reference.relocate(
                        Screen.getPrimary().getBounds().getWidth() * 0.35,
                        Screen.getPrimary().getBounds().getHeight() * 0.2
                );
                sand_reference.pause();
            } else {
                sand_reference.getChildren().remove(pause_reference);
                sand_reference.requestFocus();
                sand_reference.unpause();
            }
        }
    }

    public void show_def_screen(){
        game_reference.getChildren().add(def_reference);
        def_reference.relocate(
                Screen.getPrimary().getBounds().getWidth()*0.2,
                Screen.getPrimary().getBounds().getHeight()*0.2
        );
        game_reference.pause();
    }

    public void hide_def_screen(){
        game_reference.getChildren().remove(def_reference);
    }

    public void show_win_screen(){
        game_reference.getChildren().add(win_reference);
        win_reference.relocate(
                Screen.getPrimary().getBounds().getWidth()*0.2,
                Screen.getPrimary().getBounds().getHeight()*0.2
        );
        game_reference.pause();
    }

    public void hide_win_screen(){game_reference.getChildren().remove(win_reference);}

    public void go_to_sandbox(){
        this.scene.setRoot(sand_reference);
        sand_reference.requestFocus();
        sand_reference.start_sandbox();
    }
}