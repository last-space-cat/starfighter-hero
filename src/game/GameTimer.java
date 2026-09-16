package game;

import javafx.animation.AnimationTimer;

public class GameTimer extends AnimationTimer {
    private long saved_time = 0;
    private GameField parent;

    public void unpause(){
        saved_time = 0;
        start();
    }
    @Override
    public void handle(long l){
        if (saved_time == 0){
            saved_time = l;
            return;
        }
        double dt = (double) (l - saved_time) /1000000000;
        saved_time = l;
        update(dt);
    }

    private void update(double dt){
        parent.update(dt);
    }

    public void set_parent(GameField parent) {
        this.parent = parent;
    }
}
