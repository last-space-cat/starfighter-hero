package com.lastspacecat.starfighterhero.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.EnumMap;
import java.util.Map;

public class DroppedBonus {
    public enum BonusType {
        SHIELD("bonus_shield.png"),
        LASER("bonus_laser.png"),
        MINES("bonus_mines.png"),
        REPAIR("bonus_repair.png"),
        TRIPLE_FIRE("bonus_triple.png");

        private final Image sprite;

        BonusType(String spritePath) {
            java.io.InputStream is = getClass().getResourceAsStream("/bonus_icons/" + spritePath);
            this.sprite = (is != null) ? new Image(is) : null;
        }

        public Image getSprite() {
            return this.sprite;
        }
    }

    private static final Map<BonusType, Double> durations  = new EnumMap<>(BonusType.class);

    static {
        durations.put(BonusType.SHIELD, 15.0);
        durations.put(BonusType.MINES, 0.0);
        durations.put(BonusType.LASER, 5.0);
        durations.put(BonusType.REPAIR, 0.0);
        durations.put(BonusType.TRIPLE_FIRE, 10.0);
    }

    private double x, y;
    private final BonusType type;
    private final double speed = Main.screen_width * 0.08;
    private final double size = Main.screen_height * 0.08;
    private static int heal_amount = 1;

    public DroppedBonus(double x, double y, BonusType type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public void update(double dt) {
        x -= speed * dt;
    }

    public void render(GraphicsContext gc) {
        Image image = type.getSprite();
        if (image != null && !image.isError()) {
            gc.drawImage(image, x, y, size, size);
        }
    }

    public boolean checkCollision(PlayerShip player) {
        return player.get_x() < this.x + this.size &&
                player.get_x() + PlayerShip.width > this.x &&
                player.get_y() < this.y + this.size &&
                player.get_y() + PlayerShip.height > this.y;
    }

    public void collect(PlayerShip player, MineSystem mineSystem) {
        if (this.type == BonusType.REPAIR) {
            player.heal(heal_amount);
        } else if (this.type == BonusType.MINES) {
            mineSystem.deployMines();
        } else {
            double duration = durations.get(this.type);
            player.activateBonus(this.type, duration);
        }
    }

    public boolean isOutOfBounds() {
        return x < -size;
    }

    public static void update_from_state(CampaignState state){
        if (state.upgrade_states.get(8) == 2) { durations.put(BonusType.LASER, 10.0); }
        else { durations.put(BonusType.LASER, 5.0); }

        if (state.upgrade_states.get(12) == 2){
            if (state.upgrade_states.get(15) == 2) { heal_amount = 5; }
            else { heal_amount = 2; }
        } else { heal_amount = 1; }
    }
}