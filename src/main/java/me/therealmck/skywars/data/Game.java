package me.therealmck.skywars.data;

import me.therealmck.skywars.Main;
import me.therealmck.skywars.data.loot.LootTable;
import me.therealmck.skywars.data.players.GamePlayer;
import me.therealmck.skywars.utils.Utils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    private SkyWarsMap map;
    private List<GamePlayer> players;
    private SkyWarsSettings settings;
    private List<Team> teams;

    public Game() {
        this.players = new ArrayList<>();
        this.settings = new SkyWarsSettings();
        this.teams = new ArrayList<>();
    }



    public void fillChests() {
        LootTable islandTable = settings.getIslandLootTable();
        LootTable midTable = settings.getMidLootTable();
        List<Location> islandChests = map.getIslandChests();
        List<Location> midChests = map.getMidChests();


        for (Location islandChestLoc : islandChests) {
            Block islandChest = islandChestLoc.getBlock();
            if (islandChest.getType().equals(Material.CHEST)) {
                Inventory inv = ((Chest) islandChest.getState()).getBlockInventory();
                Random r = new Random();
                
                

                for (int i = 0; i < islandTable.getSwordLoot().getRolls(); i++) {
                    boolean shouldFill = r.nextInt(100) <= islandTable.getSwordLoot().getChance();
                    if (shouldFill) inv.setItem(r.nextInt(inv.getSize()), islandTable.getSwordLoot().getItems().get(r.nextInt(islandTable.getSwordLoot().getItems().size())));
                }
                for (int i = 0; i < islandTable.getBowLoot().getRolls(); i++) {
                    boolean shouldFill = r.nextInt(100) <= islandTable.getBowLoot().getChance();
                    if (shouldFill) inv.setItem(r.nextInt(inv.getSize()), islandTable.getBowLoot().getItems().get(r.nextInt(islandTable.getBowLoot().getItems().size())));
                }
                for (int i = 0; i < islandTable.getPearlLoot().getRolls(); i++) {
                    boolean shouldFill = r.nextInt(100) <= islandTable.getPearlLoot().getChance();
                    if (shouldFill) inv.setItem(r.nextInt(inv.getSize()), islandTable.getPearlLoot().getItems().get(r.nextInt(islandTable.getPearlLoot().getItems().size())));
                }
                for (int i = 0; i < islandTable.getProjectileLoot().getRolls(); i++) {
                    boolean shouldFill = r.nextInt(100) <= islandTable.getProjectileLoot().getChance();
                    if (shouldFill) inv.setItem(r.nextInt(inv.getSize()), islandTable.getProjectileLoot().getItems().get(r.nextInt(islandTable.getProjectileLoot().getItems().size())));
                }
                for (int i = 0; i < islandTable.getArmorLoot().getRolls(); i++) {
                    boolean shouldFill = r.nextInt(100) <= islandTable.getArmorLoot().getChance();
                    if (shouldFill) inv.setItem(r.nextInt(inv.getSize()), islandTable.getArmorLoot().getItems().get(r.nextInt(islandTable.getArmorLoot().getItems().size())));
                }
                for (int i = 0; i < islandTable.getMiscLoot().getRolls(); i++) {
                    boolean shouldFill = r.nextInt(100) <= islandTable.getMiscLoot().getChance();
                    if (shouldFill) inv.setItem(r.nextInt(inv.getSize()), islandTable.getMiscLoot().getItems().get(r.nextInt(islandTable.getMiscLoot().getItems().size())));
                }
            }
        }


        for (Location midChestLoc : midChests) {
            Block midChest = midChestLoc.getBlock();
            if (midChest.getType().equals(Material.CHEST)) {
                Inventory inv = ((Chest) midChest.getState()).getBlockInventory();
                Random r = new Random();

                for (int i = 0; i < midTable.getSwordLoot().getRolls(); i++) {
                    boolean shouldFill = r.nextInt(100) <= midTable.getSwordLoot().getChance();
                    if (shouldFill) inv.setItem(r.nextInt(inv.getSize()), midTable.getSwordLoot().getItems().get(r.nextInt(midTable.getSwordLoot().getItems().size())));
                }
                for (int i = 0; i < midTable.getBowLoot().getRolls(); i++) {
                    boolean shouldFill = r.nextInt(100) <= midTable.getBowLoot().getChance();
                    if (shouldFill) inv.setItem(r.nextInt(inv.getSize()), midTable.getBowLoot().getItems().get(r.nextInt(midTable.getBowLoot().getItems().size())));
                }
                for (int i = 0; i < midTable.getPearlLoot().getRolls(); i++) {
                    boolean shouldFill = r.nextInt(100) <= midTable.getPearlLoot().getChance();
                    if (shouldFill) inv.setItem(r.nextInt(inv.getSize()), midTable.getPearlLoot().getItems().get(r.nextInt(midTable.getPearlLoot().getItems().size())));
                }
                for (int i = 0; i < midTable.getProjectileLoot().getRolls(); i++) {
                    boolean shouldFill = r.nextInt(100) <= midTable.getProjectileLoot().getChance();
                    if (shouldFill) inv.setItem(r.nextInt(inv.getSize()), midTable.getProjectileLoot().getItems().get(r.nextInt(midTable.getProjectileLoot().getItems().size())));
                }
                for (int i = 0; i < midTable.getArmorLoot().getRolls(); i++) {
                    boolean shouldFill = r.nextInt(100) <= midTable.getArmorLoot().getChance();
                    if (shouldFill) inv.setItem(r.nextInt(inv.getSize()), midTable.getArmorLoot().getItems().get(r.nextInt(midTable.getArmorLoot().getItems().size())));
                }
                for (int i = 0; i < midTable.getMiscLoot().getRolls(); i++) {
                    boolean shouldFill = r.nextInt(100) <= midTable.getMiscLoot().getChance();
                    if (shouldFill) inv.setItem(r.nextInt(inv.getSize()), midTable.getMiscLoot().getItems().get(r.nextInt(midTable.getMiscLoot().getItems().size())));
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

    public boolean warpPlayers() {
        // Warp players to starting points.
        if (map.getSpawns().size() < players.size()/2) return false;
        else {
            List<Location> editableSpawnList = map.getSpawns();

            for (Team team : teams) {
                GamePlayer p1 = team.getPlayer1();
                GamePlayer p2 = team.getPlayer2();

                Random r = new Random();
                Location spawn = editableSpawnList.get(r.nextInt(editableSpawnList.size()));
                editableSpawnList.remove(spawn);

                p1.getBukkitPlayer().teleport(spawn);
                p2.getBukkitPlayer().teleport(spawn);
            }
            return true;
        }

        // TODO: construct cage and let out after 10 seconds
    }

    public void beginGame() {
        // Begin the game. Chests have already been filled.
        // Show every player an inventory to choose teams, then warp teams once everyone has picked a teammate.

        // TODO: Team picker

        // Once teams have been picked, warp teams.
        warpPlayers();

    }

    public void setPlayers(List<GamePlayer> players) {
        this.players = players;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public void wipePlayersWithDelay(int delay) {
        new BukkitRunnable() {
            @Override
            public void run() {
                players = new ArrayList<>();
            }
        }.runTaskLater(Main.instance, delay);
    }
}
