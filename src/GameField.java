import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import java.util.EnumSet;
import java.util.Set;

//Игровое поле. Здесь происходит игровой цикл, отрисовываются враги, препятствия и игрок.

public class GameField extends Pane {
    private final Canvas canvas;
    private final GraphicsContext graph_context;
    private final PlayerShip player;
    private final Set<KeyCode> pressed_keys = EnumSet.noneOf(KeyCode.class);
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
    }

    private void render(){
        graph_context.setFill(Color.BLACK);
        graph_context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        graph_context.setFill(Color.LIGHTGREEN); //заменить на спрайт
        graph_context.fillRect(player.get_x(), player.get_y(), PlayerShip.width, PlayerShip.height);

        //добавить отрисовку врагов
    }
}