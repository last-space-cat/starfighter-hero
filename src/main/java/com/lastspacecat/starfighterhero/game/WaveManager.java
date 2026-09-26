package com.lastspacecat.starfighterhero.game;

import com.lastspacecat.starfighterhero.enemies.EnemyType;

import java.util.List;

public class WaveManager {
    private int level;
    private record EnemyData(EnemyType type, double position){}
    private record WaveData(double delay, List<EnemyData> enemies){}
    private record LevelData(String name, List<WaveData> waves){}

    public WaveManager() {
        level = 1; //ПОЗЖЕ ПОМЕНЯТЬ НА ЗАГРУЗКУ ИЗ СОСТОЯНИЯ КАМПАНИИ
        init_level(level);
    }

    private void init_level(int level){
        String path = "levels/level_" + level + ".json";
        //ЗДЕСЬ ВСЁ ПОМЕНЯТЬ, КОГДА БУДЕТ ЗАГРУЗКА ИЗ ФАЙЛА


    }
}
