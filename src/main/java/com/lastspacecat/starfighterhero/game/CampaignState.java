package com.lastspacecat.starfighterhero.game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CampaignState {
    private int current_level;
    public static final int max_level = 4;
    public List<Integer> upgrade_states;
    private int upgrade_points;

    public CampaignState(){
        current_level = 1;
        upgrade_points = 0;
        upgrade_states = new ArrayList<>(List.of(1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0));
    }

    public void setAll(int level, int points, List<Integer> upgrades){
        current_level = level;
        upgrade_points = points;
        upgrade_states = upgrades;
    }

    public void reset(){
        current_level = 1;
        upgrade_points = 0;
        upgrade_states = new ArrayList<>(List.of(1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0));
    }

    public int getCurrent_level() {
        return current_level;
    }

    public int getUpgrade_points() {return upgrade_points;}

    public int getMax_level() {return max_level; }

    public void setUpgrade_points(int upgrade_points) {this.upgrade_points = upgrade_points;}

    public void setCurrent_level(int current_level) {
        this.current_level = current_level;
    }

    public void save_state(int save_slot){
        try {
            SaveManager.save_campaign(save_slot, current_level, upgrade_points, upgrade_states);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void load_state(int save_slot){
        try {
            SaveManager.load_campaign(save_slot, this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
