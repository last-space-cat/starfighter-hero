package com.lastspacecat.starfighterhero.game;

import com.lastspacecat.starfighterhero.enemies.Enemy;
import com.lastspacecat.starfighterhero.enemies.EnemyType;
import com.lastspacecat.starfighterhero.enemies.ModularEnemy;
import javafx.stage.Screen;

import java.util.List;

public class WaveSystem {
    private int current_wave = 0;
    private double time_in_wave = 0.0;
    private LevelData level;

    public WaveSystem(){
    }

    public void setLevel(LevelData level){
        this.level = level;
        current_wave = 0;
        time_in_wave = 0.0;
    }

    public void update (double dt, List<Enemy> push_queue){
        time_in_wave += dt;

        while (current_wave < level.waves().size()){
            LevelData.WaveData wave = level.waves().get(current_wave);
            if (time_in_wave < wave.delay()) break;

            for (LevelData.EnemyData e : wave.enemies()){
                if (e.type() != EnemyType.MODULAR) {
                    Enemy enemy = new Enemy(
                            Screen.getPrimary().getBounds().getWidth() * 0.95,
                            Screen.getPrimary().getBounds().getHeight() * e.position(),
                            e.type());
                    push_queue.add(enemy);
                }
                else{
                    Enemy enemy = new ModularEnemy(
                            Screen.getPrimary().getBounds().getWidth() * 0.95,
                            Screen.getPrimary().getBounds().getHeight() * e.position(),
                            e.type(),
                            push_queue
                            );
                    push_queue.add(enemy);
                }

            }

            time_in_wave -= wave.delay();
            current_wave++;
        }
    }
}
