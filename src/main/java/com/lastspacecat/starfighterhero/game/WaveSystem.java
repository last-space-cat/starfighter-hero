package com.lastspacecat.starfighterhero.game;

import com.lastspacecat.starfighterhero.enemies.*;
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

    public boolean isFinished(){
        return current_wave >= level.waves().size();
    }

    public void update (double dt, List<Enemy> push_queue){
        time_in_wave += dt;

        while (current_wave < level.waves().size()){
            LevelData.WaveData wave = level.waves().get(current_wave);
            if (time_in_wave < wave.delay()) break;

            for (LevelData.EnemyData e : wave.enemies()){
                if (e.type() != EnemyType.SHOOTER && e.type() != EnemyType.MODULAR && e.type() != EnemyType.KAMIKAZE && e.type() != EnemyType.SHIELD) {
                    Enemy enemy = new Enemy(
                            Main.screen_width* 0.95,
                            Main.screen_height* e.position(),
                            e.type());
                    push_queue.add(enemy);
                }
                else {
                    if (e.type() == EnemyType.MODULAR) {
                        Enemy enemy = new ModularEnemy(
                                Main.screen_width * 0.95,
                                Main.screen_height * e.position(),
                                e.type(),
                                push_queue
                        );
                        push_queue.add(enemy);
                    }
                    if (e.type() == EnemyType.SHIELD) {
                        Enemy enemy = new ShieldEnemy(
                                Main.screen_width * 0.95,
                                Main.screen_height * e.position(),
                                e.type()
                        );
                        push_queue.add(enemy);
                    }
                    if (e.type() == EnemyType.SHOOTER) {
                        Enemy enemy = new ShooterEnemy(
                                Main.screen_width * 0.95,
                                Main.screen_height * e.position(),
                                e.type()
                        );
                        push_queue.add(enemy);
                    }
                    if (e.type() == EnemyType.KAMIKAZE) {
                        Enemy enemy = new KamikazeEnemy(
                                Main.screen_width * 0.95,
                                Main.screen_height * e.position(),
                                e.type()
                        );
                        push_queue.add(enemy);
                    }
                }
            }

            time_in_wave -= wave.delay();
            current_wave++;
        }
    }
}
