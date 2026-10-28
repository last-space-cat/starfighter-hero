package com.lastspacecat.starfighterhero.game;

import com.lastspacecat.starfighterhero.game.DroppedBonus.BonusType;
import javafx.scene.canvas.GraphicsContext;
import java.util.ArrayList;
import java.util.List;

public class BonusSystem {
    private final List<DroppedBonus> activeBonuses = new ArrayList<>();

    public void clear() {
        activeBonuses.clear();
    }

    public void spawnBonus(double x, double y, BonusType type) {
        activeBonuses.add(new DroppedBonus(x, y, type));
    }

    public void update(double dt, PlayerShip player, MineSystem mineSystem, GameField field) {
        for (int i = 0; i < activeBonuses.size(); i++) {
            DroppedBonus bonus = activeBonuses.get(i);
            bonus.update(dt);

            if (bonus.checkCollision(player)) {
                bonus.collect(player, mineSystem, field);
                activeBonuses.remove(i);
                i--;
                continue;
            }
            if (bonus.isOutOfBounds()) { activeBonuses.remove(i); i--; }
        }
    }

    public void update(double dt, PlayerShip player, MineSystem mineSystem, SandboxField field) {
        for (int i = 0; i < activeBonuses.size(); i++) {
            DroppedBonus bonus = activeBonuses.get(i);
            bonus.update(dt);

            if (bonus.checkCollision(player)) {
                bonus.collect(player, mineSystem, field);
                activeBonuses.remove(i);
                i--;
                continue;
            }
            if (bonus.isOutOfBounds()) { activeBonuses.remove(i); i--; }
        }
    }



    public void render(GraphicsContext gc) {
        for (DroppedBonus bonus : activeBonuses) {
            bonus.render(gc);
        }
    }
}