package com.lastspacecat.starfighterhero.game;

import java.util.HashMap;
import java.util.Map;

public class ActiveBonuses {
    private final Map<DroppedBonus.BonusType, Double> activeEffects = new HashMap<>();
    private boolean has_shield_regen = false;
    private double shield_regen_cooldown = 15.0;
    private int shield_hp = 0;
    private int shield_max_hp = 1;
    private boolean has_nuke = false;

    public ActiveBonuses(){}

    public void update(double dt){
        activeEffects.entrySet().removeIf(entry -> {
            double timeLeft = entry.getValue() - dt;
            if (timeLeft <= 0) { return true; }
            entry.setValue(timeLeft);
            return false;
        });

        if (has_shield_regen) {
            shield_regen_cooldown -= dt;
            if (shield_regen_cooldown <= 0) {
                put(DroppedBonus.BonusType.SHIELD, 15.0);
                shield_regen_cooldown = 15.0;
            }
        }
    }

    public void clear(){
        has_nuke = false;
        shield_hp = 0;
        activeEffects.clear();
    }

    public void take_shield_damage(int amount){
        shield_hp -= amount;
        if (shield_hp <= 0) { activeEffects.remove(DroppedBonus.BonusType.SHIELD); }
    }

    public void put(DroppedBonus.BonusType type, double duration){
        activeEffects.put(type, duration);
        if (type == DroppedBonus.BonusType.SHIELD) { shield_hp = shield_max_hp; }
    }

    public Map<DroppedBonus.BonusType, Double> getActiveEffects() { return activeEffects; }
    public boolean isShieldActive() {return activeEffects.containsKey(DroppedBonus.BonusType.SHIELD);}
    public boolean isTripleFireActive() {return activeEffects.containsKey(DroppedBonus.BonusType.TRIPLE_FIRE);}
    public boolean isLaserActive() {return activeEffects.containsKey(DroppedBonus.BonusType.LASER);}
    public boolean getHas_nuke() {return has_nuke; }

    public void setHas_nuke(boolean value) { has_nuke = value; }

    public void update_from_state(CampaignState state){
        if (state.upgrade_states.get(7) == 2) { has_shield_regen = true; }
        else { has_shield_regen = false; }

        if (state.upgrade_states.get(14) == 2){ shield_max_hp = 3; }
        else{ shield_max_hp = 1; }
    }
}
