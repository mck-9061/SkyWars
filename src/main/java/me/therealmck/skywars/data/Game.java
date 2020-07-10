package me.therealmck.skywars.data;

import me.therealmck.skywars.data.loot.LootTable;
import me.therealmck.skywars.data.players.GamePlayer;
import me.therealmck.skywars.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    private SkyWarsMap map;
    private List<GamePlayer> players;
    private SkyWarsSettings settings;

    public Game() {
        this.players = new ArrayList<>();
        this.settings = new SkyWarsSettings();
    }



    public void fillChests() {
        LootTable islandTable = settings.getIslandLootTable();
        LootTable midTable = settings.getMidLootTable();
        List<Block> islandChests = map.getIslandChests();
        List<Block> midChests = map.getMidChests();


        for (Block islandChest : islandChests) {
            if (islandChest instanceof Chest) {
                Inventory inv = ((Chest) islandChest).getBlockInventory();
                Random r = new Random();
                
                

                for (int i = 0; i < islandTable.getSwordLoot().getRolls(); i++) {
                    boolean shouldFill = r.nextInt(100) < islandTable.getSwordLoot().getChance();
                    if (shouldFill) inv.setItem(r.nextInt(inv.getSize()), islandTable.getSwordLoot().getItems().get(r.nextInt(islandTable.getSwordLoot().getItems().size())));
                }
                for (int i = 0; i < islandTable.getBowLoot().getRolls(); i++) {
                    boolean shouldFill = r.nextInt(100) < islandTable.getBowLoot().getChance();
                    if (shouldFill) inv.setItem(r.nextInt(inv.getSize()), islandTable.getBowLoot().getItems().get(r.nextInt(islandTable.getBowLoot().getItems().size())));
                }
                for (int i = 0; i < islandTable.getPearlLoot().getRolls(); i++) {
                    boolean shouldFill = r.nextInt(100) < islandTable.getPearlLoot().getChance();
                    if (shouldFill) inv.setItem(r.nextInt(inv.getSize()), islandTable.getPearlLoot().getItems().get(r.nextInt(islandTable.getPearlLoot().getItems().size())));
                }
                for (int i = 0; i < islandTable.getProjectileLoot().getRolls(); i++) {
                    boolean shouldFill = r.nextInt(100) < islandTable.getProjectileLoot().getChance();
                    if (shouldFill) inv.setItem(r.nextInt(inv.getSize()), islandTable.getProjectileLoot().getItems().get(r.nextInt(islandTable.getProjectileLoot().getItems().size())));
                }
            }
        }


        for (Block midChest : midChests) {
            if (midChest instanceof Chest) {
                Inventory inv = ((Chest) midChest).getBlockInventory();
                Random r = new Random();

                for (int i = 0; i < midTable.getSwordLoot().getRolls(); i++) {
                    boolean shouldFill = r.nextInt(100) < midTable.getSwordLoot().getChance();
                    if (shouldFill) inv.setItem(r.nextInt(inv.getSize()), midTable.getSwordLoot().getItems().get(r.nextInt(midTable.getSwordLoot().getItems().size())));
                }
                for (int i = 0; i < midTable.getBowLoot().getRolls(); i++) {
                    boolean shouldFill = r.nextInt(100) < midTable.getBowLoot().getChance();
                    if (shouldFill) inv.setItem(r.nextInt(inv.getSize()), midTable.getBowLoot().getItems().get(r.nextInt(midTable.getBowLoot().getItems().size())));
                }
                for (int i = 0; i < midTable.getPearlLoot().getRolls(); i++) {
                    boolean shouldFill = r.nextInt(100) < midTable.getPearlLoot().getChance();
                    if (shouldFill) inv.setItem(r.nextInt(inv.getSize()), midTable.getPearlLoot().getItems().get(r.nextInt(midTable.getPearlLoot().getItems().size())));
                }
                for (int i = 0; i < midTable.getProjectileLoot().getRolls(); i++) {
                    boolean shouldFill = r.nextInt(100) < midTable.getProjectileLoot().getChance();
                    if (shouldFill) inv.setItem(r.nextInt(inv.getSize()), midTable.getProjectileLoot().getItems().get(r.nextInt(midTable.getProjectileLoot().getItems().size())));
                }
            }
        }
    }

    public SkyWarsMap getMap() {
        return map;
    }

    public void setMap(SkyWarsMap map) {
        World world = map.getBukkitWorld();
        // Copy world to backup
        Utils.unloadWorld(world.getName()+"_back");
        Utils.copyFileStructure(world.getWorldFolder(), new File(Bukkit.getWorldContainer(), world.getName()+"_back"));
        new WorldCreator(world.getName()+"_back").createWorld();
        this.map = map;
    }

    public void restoreBackup() {
        World world = map.getBukkitWorld();
        String worldName = world.getName();
        World backupWorld = Bukkit.getWorld(world.getName()+"_back");

        Utils.unloadWorld(worldName);
        Utils.copyFileStructure(backupWorld.getWorldFolder(), new File(Bukkit.getWorldContainer(), worldName));
        new WorldCreator(worldName).createWorld();

        File oldWorld = backupWorld.getWorldFolder();
        Utils.unloadWorld(oldWorld.getName());
        oldWorld.delete();

        this.map = new SkyWarsMap(Bukkit.getWorld(worldName));
    }

    public List<GamePlayer> getPlayers() {
        return players;
    }

    public void addPlayer(GamePlayer player) {
        this.players.add(player);
    }

    public SkyWarsSettings getSettings() {
        return settings;
    }

    public void setSettings(SkyWarsSettings settings) {
        this.settings = settings;
    }

    public void wipePlayers() { this.players = new ArrayList<>(); }
}
