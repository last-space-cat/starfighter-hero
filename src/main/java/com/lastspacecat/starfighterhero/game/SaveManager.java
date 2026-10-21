package com.lastspacecat.starfighterhero.game;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class SaveManager {
    private static final int slot_count = 3;
    private static final int upgrade_count = 16;

    private SaveManager(){}

    private static Path get_save_path(){
        String os = System.getProperty("os.name").toLowerCase(Locale.ROOT);
        Path save_path;

        if (os.contains("win")) {
            save_path = Paths.get(System.getenv("APPDATA"));
        }
        else if (os.contains("mac")){
            save_path = Paths.get(System.getProperty("user.home"), "Library", "Application Support");
        }
        else{
            save_path = Paths.get(System.getProperty("user.home"), ".starfighter-hero");
        }

        save_path = save_path.resolve("Starfighter Hero");
        save_path = save_path.resolve("saves.txt");
        return save_path;
    }

    public static List<String> read_from_file() throws IOException {
        Path save_path = get_save_path();
        if (!Files.exists(save_path)){
            return new ArrayList<>(List.of("", "", ""));
        }
        else{
            List<String> lines = new ArrayList<>(Files.readAllLines(save_path));
            while (lines.size() < slot_count) lines.add("");
            return lines.subList(0, slot_count);
        }
    }

    public static void save_campaign(int save_slot, int level, int upgrade_points, List<Integer> upgrades) throws IOException {
        if (save_slot < 0 || save_slot >= slot_count){
            throw new IllegalArgumentException("Impossible to write in slot: " + save_slot);
        }
        if (upgrades.size() != upgrade_count){
            throw new IllegalArgumentException("Expected " + upgrade_count + " upgrades");
        }

        Path save_path = get_save_path();
        List<String> lines = read_from_file();
        String upgrades_string = upgrades.stream().map(String::valueOf).collect(Collectors.joining(","));
        lines.set(save_slot, level + ";" + upgrade_points + ";" + upgrades_string);

        Path directory = save_path.getParent();
        Files.createDirectories(directory);
        Path temp = Files.createTempFile(directory, "saves", ".tmp");
        try {
            Files.write(temp, lines);
            try {
                Files.move(temp, save_path, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
            } catch (AtomicMoveNotSupportedException e) {
                Files.move(temp, save_path, StandardCopyOption.REPLACE_EXISTING);
            }
        } finally {
            Files.deleteIfExists(temp);
        }
    }

    public static void load_campaign(int save_slot, CampaignState state) throws IOException {
        if (save_slot < 0 || save_slot >= slot_count){
            throw new IllegalArgumentException("Impossible to write in slot: " + save_slot);
        }

        List<String> lines = read_from_file();
        String state_string = lines.get(save_slot);

        if (state_string.isBlank()) state.setAll(1, 0, List.of(1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0)); //нет информации в сохранении = загружаем как новвую игру

        List<String> parts = List.of(state_string.split(";"));
        if (parts.size() != 3){
            throw new IOException("Failed to parse slot " + save_slot + "; Likely corrupted");
        }

        try {
            int level = Integer.parseInt(parts.get(0));
            int points = Integer.parseInt(parts.get(1));

            if (level < 0 || level > CampaignState.max_level || points < 0){
                throw new IOException("Slot data impossible to parse; save likely edited");
            }

            List<Integer> upgrades = new ArrayList<>(upgrade_count);
            List<String> values = List.of(parts.get(2).split(","));
            for (String str : values){
                int n = Integer.parseInt(str.trim());
                if (n < 0 || n > 2) throw new IOException("Upgrade out of range: " + n);
                upgrades.add(n);
            }
            if (upgrades.size() != upgrade_count) {
                throw new IOException("Expected " + upgrade_count + " upgrades, got " + upgrades.size());
            }

            state.setAll(level, points, upgrades);
        } catch (NumberFormatException e){ clear_saves(); }
    }

    public static void clear_saves() throws IOException {
        Files.deleteIfExists(get_save_path());
    }
}
