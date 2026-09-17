package game;

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
    // КОГДА ЭКРАНОВ СТАНЕТ БОЛЬШЕ, НУЖНО ДОБАВИТЬ НА КАЖДЫЙ ИЗ НИХ ССЫЛКУ ЗДЕСЬ

    public ScreenManager(Scene scene){
        this.scene = scene;
    }

    public void set_main_reference(Parent new_ref){this.main_reference = new_ref; }
    public void set_load_reference(Parent new_ref){this.load_reference = new_ref; }
    public void set_game_reference(GameField new_ref){this.game_reference = new_ref; }
    public void set_pause_reference(PauseMenu new_ref){this.pause_reference = new_ref; }
    public void set_def_reference(DefeatScreen new_ref){this.def_reference = new_ref; }
    // КОГДА ЭКРАНОВ СТАНЕТ БОЛЬШЕ, НУЖНО ДОБАВИТЬ НА КАЖДЫЙ ИЗ НИХ СЕТТЕР ЗДЕСЬ

    public void go_to_main(){this.scene.setRoot(main_reference); }
    public void go_to_load(){this.scene.setRoot(load_reference); }
    public void go_to_game(){
        this.scene.setRoot(game_reference);
        game_reference.requestFocus();
    }

    public void toggle_pause(){
        if (!game_reference.is_paused()){
            game_reference.set_paused(true);
            game_reference.getChildren().add(pause_reference);
            pause_reference.relocate(
                    Screen.getPrimary().getBounds().getWidth()*0.35,
                    Screen.getPrimary().getBounds().getHeight()*0.2
            );
            game_reference.pause();
        }
        else{
            game_reference.set_paused(false);
            game_reference.getChildren().remove(pause_reference);
            game_reference.requestFocus();
            game_reference.unpause();
        }
    }

    public void show_def_screen(){
        game_reference.set_paused(true);
        game_reference.getChildren().add(def_reference);
        def_reference.relocate(
                Screen.getPrimary().getBounds().getWidth()*0.2,
                Screen.getPrimary().getBounds().getHeight()*0.2
        );
        game_reference.pause();
    }
    // КОГДА ЭКРАНОВ СТАНЕТ БОЛЬШЕ, НУЖНО ДОБАВИТЬ НА КАЖДЫЙ ИЗ НИХ УСТАНОВЩИК ЗДЕСЬ
}
