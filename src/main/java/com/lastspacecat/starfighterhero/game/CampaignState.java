package com.lastspacecat.starfighterhero.game;

public class CampaignState {
    private int current_level;
    private final int max_level = 5;

    public CampaignState(){
        current_level = 1;
    }

    public int getCurrent_level() {
        return current_level;
    }

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
