package com.lastspacecat.starfighterhero.game;

import com.lastspacecat.starfighterhero.enemies.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;
import java.util.EnumSet;
import java.util.Set;

public class GameField extends Pane {
    private final Canvas canvas;
    private final GraphicsContext graph_context;
    private final ScreenManager screen_manager;
    private final PlayerShip player;
    private final Set<KeyCode> pressed_keys = EnumSet.noneOf(KeyCode.class);
  
    private final ScrollingBackground backgroundManager = new ScrollingBackground("/game_background_temp.png", 40);
    private final BattleSystem battleManager = new BattleSystem();
    private WaveSystem waveManager = new WaveSystem();
  
    private GameTimer game_loop;
    private final HUD hud;
    private boolean paused = false;

    private LevelData level;
    private final List<Enemy> enemies = new ArrayList<>();
    private final List<Enemy> enemy_spawn_queue = new ArrayList<>();

    public GameField(ScreenManager handling_manager, double width, double height){
        screen_manager = handling_manager;
        canvas = new Canvas(width, height);
        graph_context = canvas.getGraphicsContext2D();
        player = new PlayerShip(Main.screen_width*0.05, Main.screen_height*0.5);
        getChildren().add(canvas);

        Button pause_button = new Button("||");
        pause_button.setPrefSize(Main.screen_height*0.07, Main.screen_height*0.07);
        pause_button.getStyleClass().add("menu-button");
        pause_button.relocate(Main.screen_width-Main.screen_height*0.07-20, 20);
        getChildren().add(pause_button);
        pause_button.setOnAction(event -> screen_manager.toggle_pause());

        hud = new HUD(player);
        getChildren().add(hud);

        game_loop = new GameTimer();
        game_loop.set_parent(this);

        setup_input();
    }

    private void setup_input(){
        setFocusTraversable(true);
        setOnMouseClicked(event->requestFocus());

        setOnKeyPressed(event-> pressed_keys.add(event.getCode()));
        setOnKeyReleased(event-> pressed_keys.remove(event.getCode()));

        focusedProperty().addListener((obs, was, is)-> {if (!is) pressed_keys.clear();});
    }

    public void start_level(String level_path){
        level = LevelLoader.load_level(level_path); //загружаем нужный уровень
        waveManager.setLevel(level); //передаем его менеджеру волн
        paused = false; //снимаем паузу, если она есть
        enemies.clear(); //очищаем список врагов
        enemy_spawn_queue.clear(); //очищаем очередь появления врагов
        battleManager.clear_bullets(); //очищаем список пуль
        player.init_player(); //отправляем игрока на стартовую позицию и восполняем ХП
        game_loop.unpause();
    }

    public void pause(){
        game_loop.stop();
        paused = true;
    }

    public void unpause(){
        game_loop.unpause();
        paused = false;
    }

    public void update(double dt){
        player.update(dt, pressed_keys, canvas.getWidth(), canvas.getHeight());
        battleManager.update_bullets(dt, player, pressed_keys, canvas.getWidth());
        backgroundManager.update(dt, canvas.getWidth(), canvas.getHeight());
      
        for (Enemy enemy : enemies) {
            enemy.update(dt, player);
        }

        battleManager.check_collisions(enemies);
        waveManager.update(dt, enemy_spawn_queue);
        hud.update();

        if (!enemy_spawn_queue.isEmpty()) {
            enemies.addAll(enemy_spawn_queue);
            enemy_spawn_queue.clear(); // Очищаем буфер для следующих спавнов
        }

        player.resolve_collisions(enemies);
        if (player.get_current_HP() <= 0){
            screen_manager.show_def_screen();
        }

        render();
    }

    private void render(){
        backgroundManager.render(graph_context, canvas.getWidth(), canvas.getHeight());

        graph_context.drawImage(player.get_sprite(), player.get_x(), player.get_y(), PlayerShip.width, PlayerShip.height);

        battleManager.render_bullets(graph_context);
        for (Enemy enemy : enemies) {
            graph_context.drawImage(enemy.get_sprite(), enemy.get_x(), enemy.get_y(), enemy.get_width(), enemy.get_height());

            if (enemy instanceof ShieldEnemy) {
                ((ShieldEnemy) enemy).render_shield(graph_context);
            }
        }
    }

    public boolean is_paused() {return paused;}
}