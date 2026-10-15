package com.lastspacecat.starfighterhero.enemies;

import com.lastspacecat.starfighterhero.game.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.List;
import java.util.Random;

public class BossDreadnought extends Enemy {
    private final List<EnemyRocket> rocketList;
    private final List<NuclearZone> zoneList;
    private final List<Enemy> spawnQueue;
    private final Random rand = new Random();

    private final boolean[] shieldActive = {true, true, true};
    private final double[] shieldCooldown = {0, 0, 0};
    private double sectorTimer = 0;
    private double movementTimer = 0;
    private int currentSectorIndex = 0;

    public BossDreadnought(double starting_x, double starting_y, EnemyType type,
                           List<EnemyRocket> rocketList, List<NuclearZone> zoneList, List<Enemy> spawnQueue) {
        super(starting_x, starting_y, type);
        this.rocketList = rocketList;
        this.zoneList = zoneList;
        this.spawnQueue = spawnQueue;
    }

    @Override
    public void update(double dt, PlayerShip player) {
        if (x > Main.screen_width * 0.72) {
            x -= type.get_velocity() * dt;
        }

        movementTimer += dt;
        y = (Main.screen_height * 0.35) + Math.sin(movementTimer * 0.5) * (Main.screen_height * 0.22);

        double hpPercentage = (double) getCurrentHP() / type.get_maxHP();

        for (int i = 0; i < 3; i++) {
            if (!shieldActive[i] && hpPercentage > 0.33) {
                shieldCooldown[i] += dt;
                if (shieldCooldown[i] >= 8.0) {
                    shieldActive[i] = true;
                    shieldCooldown[i] = 0;
                }
            }
        }

        sectorTimer += dt;

        if (hpPercentage <= 0.33) {
            shieldActive[0] = false; shieldActive[1] = false; shieldActive[2] = false;
            if (sectorTimer >= 1.5) {
                sectorTimer = 0;
                triggerSectorAttack(0, player); triggerSectorAttack(1, player); triggerSectorAttack(2, player);
            }
            return;
        }

        if (sectorTimer >= 5.0) {
            sectorTimer = 0;

            if (hpPercentage > 0.66) {
                triggerSectorAttack(currentSectorIndex, player);
                currentSectorIndex = (currentSectorIndex + 1) % 3;
            } else {
                int s1 = rand.nextInt(3);
                int s2 = (s1 + 1) % 3;
                triggerSectorAttack(s1, player);
                triggerSectorAttack(s2, player);
            }
        }
    }

    private void triggerSectorAttack(int sector, PlayerShip player) {
        double hpPercentage = (double) getCurrentHP() / type.get_maxHP();
        if (hpPercentage > 0.33) {
            shieldActive[sector] = false;
            shieldCooldown[sector] = 0;
        }

        if (sector == 0) {
            rocketList.add(new EnemyRocket(this.x, this.y + get_height() * 0.2));
        } else if (sector == 1) {
            double targetX = player.get_x() + PlayerShip.width / 2;
            double targetY = player.get_y() + PlayerShip.height / 2;
            zoneList.add(new NuclearZone(targetX, targetY));
        } else if (sector == 2) {
            spawnQueue.add(new Enemy(this.x - 40, this.y + get_height() * 0.7, EnemyType.BASE));
        }
    }

    @Override
    public void takeDamage(int amount) {
        if (!shieldActive[0] || !shieldActive[1] || !shieldActive[2]) {
            super.takeDamage(amount);
        }
    }

    public void renderShields(GraphicsContext gc) {
        gc.setLineWidth(4);
        double secH = get_height() / 3;
        for (int i = 0; i < 3; i++) {
            if (shieldActive[i]) {
                gc.setStroke(Color.rgb(0, 255, 255, 0.7));
                gc.strokeRect(x - 15, y + (i * secH), get_width() + 20, secH);
            }
        }
    }
}
