package game;

import enemies.*;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.EnumSet;
import java.util.Set;

public class GameField extends Pane {
    private final Canvas canvas;
    private final GraphicsContext graph_context;
    private final PlayerShip player;
    private final Set<KeyCode> pressed_keys = EnumSet.noneOf(KeyCode.class);
    private final List<Enemy> enemies = new ArrayList<>();
    private final List<Enemy> enemy_spawn_queue = new ArrayList<>();
    private final BattleSystem battleManager = new BattleSystem();
    private AnimationTimer game_loop;
    private long duration;

    public GameField(ScreenManager handling_manager, double width, double height){
        canvas = new Canvas(width, height);
        graph_context = canvas.getGraphicsContext2D();
        player = new PlayerShip(40, 320);
        getChildren().add(canvas);

        Button pause_button = new Button("||");
        pause_button.setPrefSize(50, 50);
        pause_button.getStyleClass().add("menu-button");
        pause_button.relocate(1200, 20);
        getChildren().add(pause_button);
        pause_button.setOnAction(event -> handling_manager.go_to_main());
        //ВРЕМЕННО КНОПКА ПАУЗЫ ОТПРАВЛЯЕТ НА ГЛАВНОЕ МЕНЮ, ПОМЕНЯТЬ ПОСЛЕ СОЗДАНИЯ ФУНКЦИОНАЛА ПАУЗЫ
        
        setup_input();
        // ТЕСТ
        enemies.add(new Enemy(1200, 50, EnemyType.BASE));
        enemies.add(new Enemy(1200, 150, EnemyType.SPEEDY));
        enemies.add(new Enemy(1200, 250, EnemyType.ARMORED));
        enemies.add(new Enemy(1200, 350, EnemyType.BONUS));
        enemies.add(new KamikazeEnemy(1200, 100, EnemyType.KAMIKAZE));
        enemies.add(new ShieldEnemy(1400, 300, EnemyType.SHIELD));
        enemies.add(new ShooterEnemy(1200, 400, EnemyType.SHOOTER));
        enemies.add(new ModularEnemy(1200, 200, EnemyType.MODULAR, enemy_spawn_queue));
        start_loop();
    }

    private void setup_input(){
        setFocusTraversable(true);
        setOnMouseClicked(event->requestFocus());

        setOnKeyPressed(event-> pressed_keys.add(event.getCode()));
        setOnKeyReleased(event-> pressed_keys.remove(event.getCode()));

        focusedProperty().addListener((obs, was, is)-> {if (!is) pressed_keys.clear();});
    }

    private void start_loop(){
        duration = 0;
        game_loop = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (duration == 0) { duration = l; return; } //пропускаем первый кадр, чтобы dt не ломался
                double dt = (double) (l - duration)/1000000000;
                duration = l;

                update(dt);
                render();
            }
        };

        game_loop.start();
    }

    private void update(double dt){
        player.update(dt, pressed_keys);
        battleManager.update_bullets(dt, player, pressed_keys);
        for (Enemy enemy : enemies) {
            enemy.update(dt, player);
        }
        battleManager.check_collisions(enemies);
        if (!enemy_spawn_queue.isEmpty()) {
            enemies.addAll(enemy_spawn_queue);
            enemy_spawn_queue.clear(); // Очищаем буфер для следующих спавнов
        }
    }

    private void render(){
        graph_context.setFill(Color.BLACK);
        graph_context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        graph_context.setFill(Color.LIGHTGREEN);
        graph_context.fillRect(player.get_x(), player.get_y(), PlayerShip.width, PlayerShip.height);

        battleManager.render_bullets(graph_context);
        for (Enemy enemy : enemies) {
            graph_context.setFill(enemy.get_color());
            graph_context.fillRect(enemy.get_x(), enemy.get_y(), enemy.get_width(), enemy.get_height());

            if (enemy instanceof ShieldEnemy) {
                ((ShieldEnemy) enemy).render_shield(graph_context);
            }
        }
    }
}