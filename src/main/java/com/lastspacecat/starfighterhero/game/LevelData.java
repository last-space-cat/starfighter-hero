package com.lastspacecat.starfighterhero.game;

import com.lastspacecat.starfighterhero.enemies.EnemyType;
import java.util.List;

public record LevelData(String name, List<WaveData> waves) {
    public record WaveData(double delay, List<EnemyData> enemies){}
    public record EnemyData(EnemyType type, double position){}
}
