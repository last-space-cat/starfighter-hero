package com.lastspacecat.starfighterhero.game;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;

public class LevelLoader {
    private static final ObjectMapper mapper = new ObjectMapper();

    private LevelLoader(){}

    public static LevelData load_level(String level_path){
        try (InputStream is = LevelLoader.class.getResourceAsStream(level_path)){
            if (is == null){
                throw new IllegalArgumentException("LevelData path is NULL");
            }
            return mapper.readValue(is, LevelData.class);
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to load level: " + level_path, e);
        }
    }
}
