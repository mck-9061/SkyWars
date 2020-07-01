package me.therealmck.skywars.data;

import me.therealmck.skywars.data.loot.LootTable;

public class SkyWarsSettings {
    private int maxHealth;
    private int jumpMultiplier;
    private int speedMultiplier;

    private LootTable islandLootTable;
    private LootTable midLootTable;

    public SkyWarsSettings(int maxHealth, int jumpMultiplier, int speedMultiplier, LootTable table, LootTable midTable) {
        this.maxHealth = maxHealth;
        this.jumpMultiplier = jumpMultiplier;
        this.speedMultiplier = speedMultiplier;
        this.islandLootTable = table;
        this.midLootTable = midTable;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getJumpMultiplier() {
        return jumpMultiplier;
    }

    public int getSpeedMultiplier() {
        return speedMultiplier;
    }

    public LootTable getIslandLootTable() {
        return islandLootTable;
    }

    public LootTable getMidLootTable() {
        return midLootTable;
    }
}
