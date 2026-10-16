package com.lastspacecat.starfighterhero.game;

import java.util.ArrayList;
import java.util.List;

public class CampaignState {
    private int current_level;
    private final int max_level = 5;
    public final List<Integer> upgrade_states = new ArrayList<>(List.of(1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0));
    private int upgrade_points;

    public CampaignState(){
        current_level = 1;
        upgrade_points = 0;
    }

    public int getCurrent_level() {
        return current_level;
    }

    public int getUpgrade_points() {return upgrade_points;}

    public void setUpgrade_points(int upgrade_points) {this.upgrade_points = upgrade_points;}

    public void setCurrent_level(int current_level) {
        this.current_level = current_level;
    }

    public void load_from_save(int save_slot){
        if (save_slot > 3 || save_slot <= 0) return;
        else {
            //ДОБАВИТЬ СЮДА ЗАГРУЗКУ ИЗ ФАЙЛА
            return;
        }
    }
}
