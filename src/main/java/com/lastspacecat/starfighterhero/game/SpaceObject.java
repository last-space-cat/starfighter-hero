package com.lastspacecat.starfighterhero.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class SpaceObject {
    private final Image sprite;
    private double x, y;
    private final double speed;
    private final double width, height;

    public SpaceObject(Image sprite, double startX, double startY, double speed, double size) {
        this.sprite = sprite;
        this.x = startX;
        this.y = startY;
        this.speed = speed;
        this.width = size;
        this.height = size;
    }

    public void update(double dt) {
        x -= speed * dt;
    }

    public void render(GraphicsContext graphContext) {
        if (sprite != null && !sprite.isError()) {
            graphContext.drawImage(sprite, x, y, width, height);
        }
    }

    public boolean isOutOfWidth() {
        return x < -width;
    }
}
