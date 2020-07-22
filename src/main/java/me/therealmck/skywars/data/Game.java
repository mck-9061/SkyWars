package me.therealmck.skywars.data;

import me.therealmck.skywars.Main;
import me.therealmck.skywars.data.loot.LootTable;
import me.therealmck.skywars.data.players.GamePlayer;
import me.therealmck.skywars.guis.TeamPickerGui;
import me.therealmck.skywars.utils.Utils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Game {
    private SkyWarsMap map;
    private List<GamePlayer> players;
    private SkyWarsSettings settings;
    private List<Team> teams;
    private boolean isCustom = false;
    private HashMap<Player, Integer> teammatePrefs = new HashMap<>();
    private TeamPickerGui teamPickerGui;
    private HashMap<Player, Kit> kits = new HashMap<>();

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

    public void wipePlayers() {
        this.players = new ArrayList<>();
        kits = new HashMap<>();
        teammatePrefs = new HashMap<>();
    }

    public boolean warpPlayers() {
        for (GamePlayer player : players) {
            Main.preventInventoryCloseList.remove(player.getBukkitPlayer());
            player.getBukkitPlayer().closeInventory();
            player.getBukkitPlayer().getInventory().setItem(0, Utils.getItemStackWithNameAndLore(Material.CHEST, "§6Select Kit", new ArrayList<>()));
            Main.pregame.add(player.getBukkitPlayer());
        }

        List<GamePlayer> random = new ArrayList<>();
        List<GamePlayer> editList = new ArrayList<>(players);

        for (GamePlayer player : players) {
            if (!editList.contains(player)) continue;

            int slot = teammatePrefs.get(player.getBukkitPlayer());
            if (slot > 26) random.add(player);
            else {
                Player preffered = (Player) Utils.getPlayerFromSkull(teamPickerGui.getBukkitInventory().getItem(slot));
                GamePlayer gp = null;
                for (GamePlayer g : players) if (g.getBukkitPlayer().equals(preffered)) gp = g;

                if (Utils.getPlayerFromSkull(teamPickerGui.getBukkitInventory().getItem(teammatePrefs.get(preffered))).equals(player.getBukkitPlayer()) || random.contains(gp)) {
                    editList.remove(player);
                    editList.remove(gp);
                    random.remove(gp);

                    Team team = new Team();
                    team.setPlayer1(player);
                    team.setPlayer2(gp);
                    teams.add(team);
                } else random.add(player);
            }
        }

        List<GamePlayer> available = new ArrayList<>(random);
        for (GamePlayer player : random) {
            Random r = new Random();
            available.remove(player);
            GamePlayer teammate = null;
            if (!(available.size() == 0)) teammate = available.get(r.nextInt(available.size()));

            available.remove(teammate);
            Team team = new Team();
            team.setPlayer1(player);
            team.setPlayer2(teammate);

            teams.add(team);
        }

        List<Block> cageBlocks = new ArrayList<>();



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

                // construct cage
                Location cageBaseBlock = spawn.clone();
                cageBaseBlock.setY(cageBaseBlock.getY() + 4);

                List<Block> ironBlocks = new ArrayList<>();
                List<Block> ironBars = new ArrayList<>();

                ironBlocks.add(cageBaseBlock.getBlock());
                ironBlocks.add(new Location(cageBaseBlock.getWorld(), cageBaseBlock.getBlockX()+1, cageBaseBlock.getBlockY(), cageBaseBlock.getBlockZ()).getBlock());
                ironBlocks.add(new Location(cageBaseBlock.getWorld(), cageBaseBlock.getBlockX()-1, cageBaseBlock.getBlockY(), cageBaseBlock.getBlockZ()).getBlock());
                ironBlocks.add(new Location(cageBaseBlock.getWorld(), cageBaseBlock.getBlockX(), cageBaseBlock.getBlockY(), cageBaseBlock.getBlockZ()+1).getBlock());
                ironBlocks.add(new Location(cageBaseBlock.getWorld(), cageBaseBlock.getBlockX(), cageBaseBlock.getBlockY(), cageBaseBlock.getBlockZ()-1).getBlock());
                ironBlocks.add(new Location(cageBaseBlock.getWorld(), cageBaseBlock.getBlockX()+1, cageBaseBlock.getBlockY(), cageBaseBlock.getBlockZ()+1).getBlock());
                ironBlocks.add(new Location(cageBaseBlock.getWorld(), cageBaseBlock.getBlockX()+1, cageBaseBlock.getBlockY(), cageBaseBlock.getBlockZ()-1).getBlock());
                ironBlocks.add(new Location(cageBaseBlock.getWorld(), cageBaseBlock.getBlockX()-1, cageBaseBlock.getBlockY(), cageBaseBlock.getBlockZ()+1).getBlock());
                ironBlocks.add(new Location(cageBaseBlock.getWorld(), cageBaseBlock.getBlockX()-1, cageBaseBlock.getBlockY(), cageBaseBlock.getBlockZ()-1).getBlock());

                for (int height = 1; height < 4; height++) {
                    ironBars.add(new Location(cageBaseBlock.getWorld(), cageBaseBlock.getBlockX()+2, cageBaseBlock.getBlockY()+height, cageBaseBlock.getBlockZ()-1).getBlock());
                    ironBars.add(new Location(cageBaseBlock.getWorld(), cageBaseBlock.getBlockX()+2, cageBaseBlock.getBlockY()+height, cageBaseBlock.getBlockZ()).getBlock());
                    ironBars.add(new Location(cageBaseBlock.getWorld(), cageBaseBlock.getBlockX()+2, cageBaseBlock.getBlockY()+height, cageBaseBlock.getBlockZ()+1).getBlock());
                    ironBars.add(new Location(cageBaseBlock.getWorld(), cageBaseBlock.getBlockX()-2, cageBaseBlock.getBlockY()+height, cageBaseBlock.getBlockZ()-1).getBlock());
                    ironBars.add(new Location(cageBaseBlock.getWorld(), cageBaseBlock.getBlockX()-2, cageBaseBlock.getBlockY()+height, cageBaseBlock.getBlockZ()).getBlock());
                    ironBars.add(new Location(cageBaseBlock.getWorld(), cageBaseBlock.getBlockX()-2, cageBaseBlock.getBlockY()+height, cageBaseBlock.getBlockZ()+1).getBlock());
                    ironBars.add(new Location(cageBaseBlock.getWorld(), cageBaseBlock.getBlockX()-1, cageBaseBlock.getBlockY()+height, cageBaseBlock.getBlockZ()+2).getBlock());
                    ironBars.add(new Location(cageBaseBlock.getWorld(), cageBaseBlock.getBlockX(), cageBaseBlock.getBlockY()+height, cageBaseBlock.getBlockZ()+2).getBlock());
                    ironBars.add(new Location(cageBaseBlock.getWorld(), cageBaseBlock.getBlockX()+1, cageBaseBlock.getBlockY()+height, cageBaseBlock.getBlockZ()+2).getBlock());
                    ironBars.add(new Location(cageBaseBlock.getWorld(), cageBaseBlock.getBlockX()-1, cageBaseBlock.getBlockY()+height, cageBaseBlock.getBlockZ()-2).getBlock());
                    ironBars.add(new Location(cageBaseBlock.getWorld(), cageBaseBlock.getBlockX(), cageBaseBlock.getBlockY()+height, cageBaseBlock.getBlockZ()-2).getBlock());
                    ironBars.add(new Location(cageBaseBlock.getWorld(), cageBaseBlock.getBlockX()+1, cageBaseBlock.getBlockY()+height, cageBaseBlock.getBlockZ()-2).getBlock());
                }

                ironBlocks.add(new Location(cageBaseBlock.getWorld(), cageBaseBlock.getBlockX(), cageBaseBlock.getBlockY()+3, cageBaseBlock.getBlockZ()).getBlock());
                ironBlocks.add(new Location(cageBaseBlock.getWorld(), cageBaseBlock.getBlockX()+1, cageBaseBlock.getBlockY()+3, cageBaseBlock.getBlockZ()).getBlock());
                ironBlocks.add(new Location(cageBaseBlock.getWorld(), cageBaseBlock.getBlockX()-1, cageBaseBlock.getBlockY()+3, cageBaseBlock.getBlockZ()).getBlock());
                ironBlocks.add(new Location(cageBaseBlock.getWorld(), cageBaseBlock.getBlockX(), cageBaseBlock.getBlockY()+3, cageBaseBlock.getBlockZ()+1).getBlock());
                ironBlocks.add(new Location(cageBaseBlock.getWorld(), cageBaseBlock.getBlockX(), cageBaseBlock.getBlockY()+3, cageBaseBlock.getBlockZ()-1).getBlock());
                ironBlocks.add(new Location(cageBaseBlock.getWorld(), cageBaseBlock.getBlockX()+1, cageBaseBlock.getBlockY()+3, cageBaseBlock.getBlockZ()+1).getBlock());
                ironBlocks.add(new Location(cageBaseBlock.getWorld(), cageBaseBlock.getBlockX()+1, cageBaseBlock.getBlockY()+3, cageBaseBlock.getBlockZ()-1).getBlock());
                ironBlocks.add(new Location(cageBaseBlock.getWorld(), cageBaseBlock.getBlockX()-1, cageBaseBlock.getBlockY()+3, cageBaseBlock.getBlockZ()+1).getBlock());
                ironBlocks.add(new Location(cageBaseBlock.getWorld(), cageBaseBlock.getBlockX()-1, cageBaseBlock.getBlockY()+3, cageBaseBlock.getBlockZ()-1).getBlock());

                cageBlocks.addAll(ironBars);
                cageBlocks.addAll(ironBlocks);

                for (Block b : ironBlocks) b.setType(Material.IRON_BLOCK);
                for (Block b : ironBars) b.setType(Material.IRON_BARS);

                p1.getBukkitPlayer().teleport(spawn);
                p2.getBukkitPlayer().teleport(spawn);
            }

            Bukkit.getScheduler().runTaskLater(Main.instance, () -> { for (GamePlayer player : players) player.getBukkitPlayer().sendTitle("5", "", 0, 20, 0); }, 200);
            Bukkit.getScheduler().runTaskLater(Main.instance, () -> { for (GamePlayer player : players) player.getBukkitPlayer().sendTitle("4", "", 0, 20, 0); }, 220);
            Bukkit.getScheduler().runTaskLater(Main.instance, () -> { for (GamePlayer player : players) player.getBukkitPlayer().sendTitle("3", "", 0, 20, 0); }, 240);
            Bukkit.getScheduler().runTaskLater(Main.instance, () -> { for (GamePlayer player : players) player.getBukkitPlayer().sendTitle("2", "", 0, 20, 0); }, 260);
            Bukkit.getScheduler().runTaskLater(Main.instance, () -> { for (GamePlayer player : players) player.getBukkitPlayer().sendTitle("1", "", 0, 20, 0); }, 280);

            Bukkit.getScheduler().runTaskLater(Main.instance, () -> {
                for (Block b : cageBlocks) b.setType(Material.AIR);

                for (GamePlayer player : players) {
                    player.getBukkitPlayer().getInventory().setItem(0, new ItemStack(Material.AIR));
                    player.getBukkitPlayer().closeInventory();
                    Main.pregame.remove(player.getBukkitPlayer());
                    if (kits.containsKey(player.getBukkitPlayer())) {
                        for (ItemStack item : kits.get(player.getBukkitPlayer()).getItems()) player.getBukkitPlayer().getInventory().addItem(item.clone());
                    }
                }
            }, 300);
            return true;
        }
    }

    public void beginGame() {
        // Begin the game. Chests have already been filled.
        // Show every player an inventory to choose teams, then warp teams once everyone has picked a teammate.

        teamPickerGui = new TeamPickerGui(players.get(0).getBukkitPlayer(), this);
        teammatePrefs = new HashMap<>();
        for (GamePlayer player : players) {
            player.getBukkitPlayer().openInventory(teamPickerGui.getBukkitInventory());
            Main.preventInventoryCloseList.add(player.getBukkitPlayer());
            teammatePrefs.put(player.getBukkitPlayer(), 35);
        }

        // Give players 15 seconds to pick teammates, then warp them

        Bukkit.getScheduler().runTaskLater(Main.instance, () -> {
            warpPlayers();
        }, 300);

        // TODO: Modifiers
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
        kits = new HashMap<>();
        teammatePrefs = new HashMap<>();
    }

    public boolean isCustom() {
        return isCustom;
    }

    public void setCustom(boolean custom) {
        isCustom = custom;
    }

    public void removePlayer(GamePlayer player) {
        players.remove(player);
    }

    public Team getTeam(GamePlayer player) {
        Team toReturn = null;
        for (Team team : teams) {
            if (team.getPlayer1().equals(player) || team.getPlayer2().equals(player)) toReturn = team;
        }
        return toReturn;
    }

    public HashMap<Player, Integer> getTeammatePrefs() {
        return teammatePrefs;
    }

    public void setTeammatePref(Player player, Integer slot) {
        teammatePrefs.put(player, slot);
    }

    public void setKit(Player player, Kit kit) { kits.put(player, kit); }
}
