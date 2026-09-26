package com.lastspacecat.starfighterhero.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ScrollingBackground {
    private final Image backgroundImage;
    private double bgOffset = 0;
    private double speed;
    private final double tileSize = 256;
    private final List<Image> planetSprites = new ArrayList<>();
    private SpaceObject activePlanet = null;
    private double planetSpawnTimer = 0;
    private final double PLANET_COOLDOWN = 30.0;
    private final Random random = new Random();

    public ScrollingBackground(String backgroundPath, double speed) {
        this.backgroundImage = new Image(getClass().getResourceAsStream(backgroundPath));
        this.speed = speed;

        loadPlanets();
    }

    private void loadPlanets() {
        for (int i = 1; i <= 5; i++) {
            Image img = new Image(getClass().getResourceAsStream("/planets/planet_" + i + ".png"));
            if (!img.isError()) {
                planetSprites.add(img);
            }
        }
    }

    public void update(double dt, double canvasWidth, double canvasHeight) {
        bgOffset -= speed * dt;
        if (bgOffset <= -tileSize) {
            bgOffset = 0;
        }

        if (activePlanet == null) {
            planetSpawnTimer += dt;
            if (planetSpawnTimer >= PLANET_COOLDOWN) {
                spawnRandomPlanet(canvasWidth, canvasHeight);
                planetSpawnTimer = 0;
            }
        } else {
            activePlanet.update(dt);
            if (activePlanet.isOutOfWidth()) {
                activePlanet = null;
            }
        }
    }

    private void spawnRandomPlanet(double canvasWidth, double canvasHeight) {
        if (planetSprites.isEmpty()) return;
        Image randomSprite = planetSprites.get(random.nextInt(planetSprites.size()));

        double size = 400 + (150 * random.nextDouble());
        double startX = canvasWidth + size;
        double startY = canvasHeight * 0.05;
        double planetSpeed = this.speed * 1.2;

        activePlanet = new SpaceObject(randomSprite, startX, startY, planetSpeed, size);
    }

    public void render(GraphicsContext graphContext, double canvasWidth, double canvasHeight) {
        if (backgroundImage != null && !backgroundImage.isError()) {
            int tilesX = (int) Math.ceil(canvasWidth / tileSize) + 1;
            int tilesY = (int) Math.ceil(canvasHeight / tileSize);

            for (int x = 0; x < tilesX; x++) {
                for (int y = 0; y < tilesY; y++) {
                    double drawX = bgOffset + (x * tileSize);
                    double drawY = y * tileSize;
                    graphContext.drawImage(backgroundImage, drawX, drawY, tileSize, tileSize);
                }
            }
        } else {
            graphContext.setFill(Color.BLACK);
            graphContext.fillRect(0, 0, canvasWidth, canvasHeight);
        }

        if (activePlanet != null) {
            activePlanet.render(graphContext);
        }
    }

    public void setSpeed(double newSpeed) {
        this.speed = newSpeed;
    }

    public void reset() {
        this.bgOffset = 0;
        this.activePlanet = null;
        this.planetSpawnTimer = 0;
    }
}

