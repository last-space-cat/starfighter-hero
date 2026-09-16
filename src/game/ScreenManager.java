package game;

import javafx.scene.Parent;
import javafx.scene.Scene;

public class ScreenManager {
    private final Scene scene;
    private Parent main_reference;
    private Parent load_reference;
    private Parent game_reference;
    // КОГДА ЭКРАНОВ СТАНЕТ БОЛЬШЕ, НУЖНО ДОБАВИТЬ НА КАЖДЫЙ ИЗ НИХ ССЫЛКУ ЗДЕСЬ

    public ScreenManager(Scene scene){
        this.scene = scene;
    }

    public void set_main_reference(Parent new_ref){this.main_reference = new_ref; }
    public void set_load_reference(Parent new_ref){this.load_reference = new_ref; }
    public void set_game_reference(Parent new_ref){this.game_reference = new_ref; }
    // КОГДА ЭКРАНОВ СТАНЕТ БОЛЬШЕ, НУЖНО ДОБАВИТЬ НА КАЖДЫЙ ИЗ НИХ СЕТТЕР ЗДЕСЬ

    public void go_to_main(){this.scene.setRoot(main_reference); }
    public void go_to_load(){this.scene.setRoot(load_reference); }
    public void go_to_game(){
        this.scene.setRoot(game_reference);
        game_reference.requestFocus();
    }
    // КОГДА ЭКРАНОВ СТАНЕТ БОЛЬШЕ, НУЖНО ДОБАВИТЬ НА КАЖДЫЙ ИЗ НИХ УСТАНОВЩИК ЗДЕСЬ
}
