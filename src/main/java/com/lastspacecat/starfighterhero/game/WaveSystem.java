package com.lastspacecat.starfighterhero.game;

import com.lastspacecat.starfighterhero.enemies.*;
import java.util.List;

public class WaveSystem {
    private int current_wave = 0;
    private double time_in_wave = 0.0;
    private LevelData level;
    private GameField field;

    public WaveSystem(){}
    public void setField(GameField field) {this.field = field;}

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
                if (e.type() != EnemyType.SHOOTER && e.type() != EnemyType.MODULAR &&
                        e.type() != EnemyType.KAMIKAZE && e.type() != EnemyType.SHIELD &&
                        e.type() != EnemyType.BOSS_FRIGATE && e.type() != EnemyType.BOSS_CRUISER &&
                        e.type() != EnemyType.BOSS_DREADNOUGHT) {
                    Enemy enemy = new Enemy(
                            Main.screen_width* 0.95,
                            Main.screen_height* e.position(),
                            e.type());
                    push_queue.add(enemy);
                }
                else {
                    if (e.type() == EnemyType.MODULAR) {
                        push_queue.add(new ModularEnemy(Main.screen_width * 0.95, Main.screen_height * e.position(), e.type(), push_queue));
                    }
                    if (e.type() == EnemyType.SHIELD) {
                        push_queue.add(new ShieldEnemy(Main.screen_width * 0.95, Main.screen_height * e.position(), e.type()));
                    }
                    if (e.type() == EnemyType.SHOOTER) {
                        ShooterEnemy enemy = new ShooterEnemy(Main.screen_width * 0.95, Main.screen_height * e.position(), e.type()); enemy.setLaserList(field.getLasers()); push_queue.add(enemy);
                    }
                    if (e.type() == EnemyType.KAMIKAZE) {
                        push_queue.add(new KamikazeEnemy(Main.screen_width * 0.95, Main.screen_height * e.position(), e.type()));
                    }
                    if (e.type() == EnemyType.BOSS_FRIGATE) {
                        push_queue.add(new BossFrigate(Main.screen_width * 0.95, Main.screen_height * e.position(), e.type(), field.getRockets()));
                    }
                    if (e.type() == EnemyType.BOSS_CRUISER) {
                        push_queue.add(new BossCruiser(Main.screen_width * 0.95, Main.screen_height * e.position(), e.type(), field.getZones()));
                    }
                    if (e.type() == EnemyType.BOSS_DREADNOUGHT) {
                        push_queue.add(new BossDreadnought(Main.screen_width * 0.95, Main.screen_height * e.position(), e.type(), field.getRockets(), field.getZones(), field.getSpawnQueue()));
                    }
                }
            }

            time_in_wave -= wave.delay();
            current_wave++;
        }
    }
}
