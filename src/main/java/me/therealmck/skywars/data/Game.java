package me.therealmck.skywars.data;

import me.therealmck.skywars.Main;
import me.therealmck.skywars.data.loot.LootTable;
import me.therealmck.skywars.data.players.GamePlayer;
import me.therealmck.skywars.guis.TeamPickerGui;
import me.therealmck.skywars.utils.Utils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

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
    private List<BukkitRunnable> tasks = new ArrayList<>();

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
            islandChestLoc.setWorld(Bukkit.getWorld(map.getBukkitWorld().getName()));
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
            midChestLoc.setWorld(Bukkit.getWorld(map.getBukkitWorld().getName()));
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
        World newWorld = new WorldCreator(worldName).createWorld();

        // Reload spawn/chest locations with new World object
        Main.instance.createSkyWarsConfig();

        this.map = new SkyWarsMap(newWorld.getName());
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
        System.out.println("warp players called");
        for (GamePlayer player : players) {
            Main.preventInventoryCloseList.remove(player.getBukkitPlayer());
            player.getBukkitPlayer().closeInventory();
            player.getBukkitPlayer().getInventory().setItem(0, Utils.getItemStackWithNameAndLore(Material.CHEST, "§6Select Kit", new ArrayList<>()));
            Main.pregame.add(player.getBukkitPlayer());
        }

        List<GamePlayer> random = new ArrayList<>();
        List<GamePlayer> chosen = new ArrayList<>();
        List<GamePlayer> editList = new ArrayList<>(players);

        for (GamePlayer player : players) {
            if (!editList.contains(player)) continue;

            int slot = teammatePrefs.get(player.getBukkitPlayer());
            if (teamPickerGui.getBukkitInventory().getItem(slot).getType().equals(Material.RED_STAINED_GLASS_PANE)) random.add(player);
            if (teamPickerGui.getBukkitInventory().getItem(slot).getType().equals(Material.BARRIER)) random.add(player);
            else {
                chosen.add(player);
            }
        }

        for (GamePlayer player : chosen) {
            int slot = teammatePrefs.get(player.getBukkitPlayer());

            try {
                Player preferred = Bukkit.getPlayer(Utils.getPlayerFromSkull(teamPickerGui.getBukkitInventory().getItem(slot)).getName());
                GamePlayer gp = null;
                for (GamePlayer g : players) if (g.getBukkitPlayer().equals(preferred)) gp = g;

                if (random.contains(gp) || Utils.getPlayerFromSkull(teamPickerGui.getBukkitInventory().getItem(teammatePrefs.get(preferred))).equals(player.getBukkitPlayer())) {
                    editList.remove(player);
                    editList.remove(gp);
                    random.remove(gp);

                    Team team = new Team();
                    team.setPlayer1(player);
                    team.setPlayer2(gp);
                    teams.add(team);
                } else random.add(player);
            } catch (Exception e) { random.add(player); }
        }

        List<GamePlayer> available = new ArrayList<>(random);
        for (GamePlayer player : random) {
            if (!available.contains(player)) continue;

            available.remove(player);
            GamePlayer teammate = null;
            if (!(available.size() == 0)) teammate = available.get(0);

            available.remove(teammate);
            Team team = new Team();
            team.setPlayer1(player);
            team.setPlayer2(teammate);

            teams.add(team);
        }

        System.out.print("teams found");

        List<Block> cageBlocks = new ArrayList<>();



        // Warp players to starting points.
        System.out.println(map.getSpawns().size());
        System.out.println(players.size());
        if (map.getSpawns().size() <= players.size()/2) return false;
        else {
            List<Location> editableSpawnList = map.getSpawns();

            for (Team team : teams) {
                GamePlayer p1 = team.getPlayer1();
                GamePlayer p2 = team.getPlayer2();

                Random r = new Random();
                Location spawn = editableSpawnList.get(r.nextInt(editableSpawnList.size()));
                editableSpawnList.remove(spawn);

                System.out.println(map.getBukkitWorld().getName());
                System.out.println(spawn.getWorld().getName());

                spawn.setWorld(Bukkit.getWorld(map.getBukkitWorld().getName()));

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
                    ironBars.add(new Location(cageBaseBlock.getWorld(), cageBaseBlock.getBlockX()+2, cageBaseBlock.getBlockY()+height, cageBaseBlock.getBlockZ()-2).getBlock());
                    ironBars.add(new Location(cageBaseBlock.getWorld(), cageBaseBlock.getBlockX()+2, cageBaseBlock.getBlockY()+height, cageBaseBlock.getBlockZ()-1).getBlock());
                    ironBars.add(new Location(cageBaseBlock.getWorld(), cageBaseBlock.getBlockX()+2, cageBaseBlock.getBlockY()+height, cageBaseBlock.getBlockZ()).getBlock());
                    ironBars.add(new Location(cageBaseBlock.getWorld(), cageBaseBlock.getBlockX()+2, cageBaseBlock.getBlockY()+height, cageBaseBlock.getBlockZ()+1).getBlock());
                    ironBars.add(new Location(cageBaseBlock.getWorld(), cageBaseBlock.getBlockX()+2, cageBaseBlock.getBlockY()+height, cageBaseBlock.getBlockZ()+2).getBlock());
                    ironBars.add(new Location(cageBaseBlock.getWorld(), cageBaseBlock.getBlockX()-2, cageBaseBlock.getBlockY()+height, cageBaseBlock.getBlockZ()-1).getBlock());
                    ironBars.add(new Location(cageBaseBlock.getWorld(), cageBaseBlock.getBlockX()-2, cageBaseBlock.getBlockY()+height, cageBaseBlock.getBlockZ()).getBlock());
                    ironBars.add(new Location(cageBaseBlock.getWorld(), cageBaseBlock.getBlockX()-2, cageBaseBlock.getBlockY()+height, cageBaseBlock.getBlockZ()+1).getBlock());
                    ironBars.add(new Location(cageBaseBlock.getWorld(), cageBaseBlock.getBlockX()-2, cageBaseBlock.getBlockY()+height, cageBaseBlock.getBlockZ()+2).getBlock());
                    ironBars.add(new Location(cageBaseBlock.getWorld(), cageBaseBlock.getBlockX()-1, cageBaseBlock.getBlockY()+height, cageBaseBlock.getBlockZ()+2).getBlock());
                    ironBars.add(new Location(cageBaseBlock.getWorld(), cageBaseBlock.getBlockX(), cageBaseBlock.getBlockY()+height, cageBaseBlock.getBlockZ()+2).getBlock());
                    ironBars.add(new Location(cageBaseBlock.getWorld(), cageBaseBlock.getBlockX()+1, cageBaseBlock.getBlockY()+height, cageBaseBlock.getBlockZ()+2).getBlock());
                    ironBars.add(new Location(cageBaseBlock.getWorld(), cageBaseBlock.getBlockX()-1, cageBaseBlock.getBlockY()+height, cageBaseBlock.getBlockZ()-2).getBlock());
                    ironBars.add(new Location(cageBaseBlock.getWorld(), cageBaseBlock.getBlockX(), cageBaseBlock.getBlockY()+height, cageBaseBlock.getBlockZ()-2).getBlock());
                    ironBars.add(new Location(cageBaseBlock.getWorld(), cageBaseBlock.getBlockX()+1, cageBaseBlock.getBlockY()+height, cageBaseBlock.getBlockZ()-2).getBlock());
                    ironBars.add(new Location(cageBaseBlock.getWorld(), cageBaseBlock.getBlockX()-2, cageBaseBlock.getBlockY()+height, cageBaseBlock.getBlockZ()-2).getBlock());
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

                System.out.println("cages made");

                spawn.setY(spawn.getY()+5);

                System.out.println(p1);
                System.out.println(p2);

                System.out.println(p1.getBukkitPlayer().getName());
                System.out.println(p2.getBukkitPlayer().getName());

                p1.getBukkitPlayer().teleport(spawn);
                p2.getBukkitPlayer().teleport(spawn);

                System.out.println("players teleported");
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

            // Modifiers
            for (GamePlayer player : players) {
                player.getBukkitPlayer().setMaxHealth(settings.getMaxHealth());
                player.getBukkitPlayer().setWalkSpeed((float) (0.2*settings.getSpeedMultiplier()));
                if (settings.getJumpMultiplier() > 1) {
                    player.getBukkitPlayer().addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 9999, settings.getJumpMultiplier()-1));
                }
            }

            Random r = new Random();
            // Schedule random events
            if (settings.isZombieHordeEvent()) {
                BukkitRunnable task = new BukkitRunnable() {
                    @Override
                    public void run() {
                        for (GamePlayer player : players) player.getBukkitPlayer().sendMessage("Zombie horde event!");
                        Random r = new Random();
                        for (int i = 0; i < 20; i++) {
                            Location spawn = map.getMidChests().get(r.nextInt(map.getMidChests().size()));
                            spawn.setY(spawn.getY()+1);
                            map.getBukkitWorld().spawnEntity(spawn, EntityType.ZOMBIE);
                        }
                    }
                };
                task.runTaskLater(Main.instance, r.nextInt(6700)+300);
                tasks.add(task);
            }

            if (settings.isAnvilRainEvent()) {
                BukkitRunnable task = new BukkitRunnable() {
                    @Override
                    public void run() {
                        for (GamePlayer player : players) player.getBukkitPlayer().sendMessage("Anvil rain event!");
                        Random r = new Random();
                        for (int i = 0; i < 20; i++) {
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    Location spawn = players.get(r.nextInt(players.size())).getBukkitPlayer().getLocation();
                                    spawn.setY(spawn.getY()+16);
                                    spawn.setX(spawn.getX()+(r.nextInt(6)-3));
                                    spawn.setZ(spawn.getZ()+(r.nextInt(6)-3));
                                    map.getBukkitWorld().spawnFallingBlock(spawn, Material.ANVIL, (byte) 0);
                                }
                            }.runTaskLater(Main.instance, i*5);
                        }
                    }
                };
                task.runTaskLater(Main.instance, r.nextInt(6700)+300);
                tasks.add(task);
            }

            if (settings.isHorseMountEvent()) {
                BukkitRunnable task = new BukkitRunnable() {
                    @Override
                    public void run() {
                        for (GamePlayer player : players) {
                            player.getBukkitPlayer().sendMessage("Horse mount event!");

                            Horse horse = (Horse) map.getBukkitWorld().spawnEntity(player.getBukkitPlayer().getLocation(), EntityType.HORSE);
                            horse.getInventory().setSaddle(new ItemStack(Material.SADDLE));

                            horse.addPassenger(player.getBukkitPlayer());
                        }
                    }
                };
                task.runTaskLater(Main.instance, r.nextInt(6700)+300);
                tasks.add(task);
            }

            if (settings.isBlockDecayEvent()) {
                BukkitRunnable task = new BukkitRunnable() {
                    @Override
                    public void run() {
                        for (GamePlayer player : players) {
                            player.getBukkitPlayer().sendMessage("Block decay event!");

                            // TODO: This
                        }
                    }
                };
                task.runTaskLater(Main.instance, r.nextInt(6700)+300);
                tasks.add(task);
            }
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
            for (GamePlayer player : players) {
                Main.preventInventoryCloseList.remove(player.getBukkitPlayer());
                player.getBukkitPlayer().closeInventory();
            }
            warpPlayers();
        }, 300);
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
        Game game = this;
        new BukkitRunnable() {
            @Override
            public void run() {
                tasks = new ArrayList<>();
                players = new ArrayList<>();
                kits = new HashMap<>();
                teammatePrefs = new HashMap<>();
                teams = new ArrayList<>();
                restoreBackup();
                setSettings(new SkyWarsSettings());
                Main.runningGames.remove(game);
                Main.waitingGames.add(game);
                Main.queue.processQueue(game);
            }
        }.runTaskLater(Main.instance, delay);
    }

    public void cancelTasks() {
        for (BukkitRunnable task : tasks) {
            task.cancel();
        }
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
