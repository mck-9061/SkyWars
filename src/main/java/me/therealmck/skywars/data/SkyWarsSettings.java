package me.therealmck.skywars.data;

import me.therealmck.skywars.data.loot.LootTable;

import java.util.List;

public class SkyWarsSettings {
    private int maxHealth;
    private int jumpMultiplier;
    private int speedMultiplier;

    private LootTable islandLootTable;
    private LootTable midLootTable;

    private Boolean anvilRainEvent = false;
    private Boolean blockDecayEvent = false;
    private Boolean horseMountEvent = false;
    private Boolean zombieHordeEvent = false;


    public SkyWarsSettings(int maxHealth, int jumpMultiplier, int speedMultiplier, LootTable table, LootTable midTable) {
        this.maxHealth = maxHealth;
        this.jumpMultiplier = jumpMultiplier;
        this.speedMultiplier = speedMultiplier;
        this.islandLootTable = table;
        this.midLootTable = midTable;
    }

    public SkyWarsSettings() {
        this.maxHealth = 20;
        this.jumpMultiplier = 1;
        this.speedMultiplier = 1;
        this.islandLootTable = new LootTable();
        this.midLootTable = new LootTable();
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


    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void setJumpMultiplier(int jumpMultiplier) {
        this.jumpMultiplier = jumpMultiplier;
    }

    public void setSpeedMultiplier(int speedMultiplier) {
        this.speedMultiplier = speedMultiplier;
    }

    public void setIslandLootTable(LootTable islandLootTable) {
        this.islandLootTable = islandLootTable;
    }

    public void setMidLootTable(LootTable midLootTable) {
        this.midLootTable = midLootTable;
    }

    public boolean isAnvilRainEvent() {
        return anvilRainEvent;
    }

    public void setAnvilRainEvent(boolean anvilRainEvent) {
        this.anvilRainEvent = anvilRainEvent;
    }

    public boolean isBlockDecayEvent() {
        return blockDecayEvent;
    }

    public void setBlockDecayEvent(boolean blockDecayEvent) {
        this.blockDecayEvent = blockDecayEvent;
    }

    public boolean isHorseMountEvent() {
        return horseMountEvent;
    }

    public void setHorseMountEvent(boolean horseMountEvent) {
        this.horseMountEvent = horseMountEvent;
    }

    public boolean isZombieHordeEvent() {
        return zombieHordeEvent;
    }

    public void setZombieHordeEvent(boolean zombieHordeEvent) {
        this.zombieHordeEvent = zombieHordeEvent;
    }
}
