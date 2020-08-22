package me.therealmck.skywars;

import me.therealmck.skywars.commands.*;
import me.therealmck.skywars.data.Game;
import me.therealmck.skywars.data.Kit;
import me.therealmck.skywars.data.Queue;
import me.therealmck.skywars.data.SkyWarsMap;
import me.therealmck.skywars.guis.KitGui;
import me.therealmck.skywars.guis.listeners.*;
import me.therealmck.skywars.listeners.*;
import me.therealmck.skywars.placeholderapi.SkyWarsPlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main extends JavaPlugin {
    public static Queue queue;
    public static List<SkyWarsMap> maps = new ArrayList<>();
    public static HashMap<Player, Game> activeCustomGames = new HashMap<>();
    public static List<Game> waitingGames = new ArrayList<>();
    public static List<Game> runningGames = new ArrayList<>();
    public static List<Kit> kits = new ArrayList<>();
    public static Main instance;
    public static List<Player> pregame = new ArrayList<>();

    public static List<Player> preventInventoryCloseList = new ArrayList<>();

    @Override
    public void onEnable() {
        instance = this;
        queue = new Queue();
        // Get all maps from config
        new BukkitRunnable() {
            @Override
            public void run() {
                // Config
                createMapConfig();
                createPlayerDataConfig();
                createSkyWarsConfig();
                createKitsConfig();
                saveResource("configtemplate.yml", true);


                for (String key : mapConfig.getKeys(false)) {
                    try {
                        ConfigurationSection section = mapConfig.getConfigurationSection(key);
                        assert section != null;
                        System.out.println(Bukkit.getWorld(key));
                        maps.add(new SkyWarsMap(key));
                    } catch (Exception e) {
                        System.out.println("Map "+key+" couldn't be loaded. Does it exist?");
                    }
                }

                for (SkyWarsMap map : maps) {
                    Game game = new Game();
                    game.setMap(map);
                    waitingGames.add(game);
                }


                for (String key : kitsConfig.getKeys(false)) {
                    kits.add(new Kit(key));
                }
            }
        }.runTaskLater(this, 1);

        //PlaceholderAPI
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null){
            new SkyWarsPlaceholderExpansion(this).register();
        }


        // Commands
        getCommand("skywars").setExecutor(new SkyWarsCommand());
        getCommand("addislandchest").setExecutor(new AddIslandChest());
        getCommand("addmidchest").setExecutor(new AddMidChest());
        getCommand("addspawnpoint").setExecutor(new AddSpawnPoint());
        getCommand("addworld").setExecutor(new AddWorld());
        getCommand("setlobby").setExecutor(new SetLobby());

        // SkyWars command aliases
        List<String> aliases = skyWarsConfig.getStringList("Aliases");
        getCommand("skywars").setAliases(aliases);

        // Event listeners
        getServer().getPluginManager().registerEvents(new EventChooserGuiListener(), this);
        getServer().getPluginManager().registerEvents(new IslandLootGuiListener(), this);
        getServer().getPluginManager().registerEvents(new MainCustomGameGuiListener(), this);
        getServer().getPluginManager().registerEvents(new MidLootGuiListener(), this);
        getServer().getPluginManager().registerEvents(new ModifierGuiListener(), this);

        getServer().getPluginManager().registerEvents(new DeathListener(), this);
        getServer().getPluginManager().registerEvents(new DisconnectListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryCloseListener(), this);
        getServer().getPluginManager().registerEvents(new KillListener(), this);
        getServer().getPluginManager().registerEvents(new LoginListener(), this);
        getServer().getPluginManager().registerEvents(new TeammateDamageListener(), this);
        getServer().getPluginManager().registerEvents(new TeleportCanceller(), this);

        getServer().getPluginManager().registerEvents(new NPCClickListener(), this);
        getServer().getPluginManager().registerEvents(new KitGuiOpenListener(), this);
        getServer().getPluginManager().registerEvents(new TeamPickerGuiListener(), this);


        // Begin task to update games
        new BukkitRunnable() {
            @Override
            public void run() {
                List<Game> toRemove = new ArrayList<>();
                for (Game game : waitingGames) {
                    if (game.getPlayers().size() >= skyWarsConfig.getInt("MinimumPlayers")) {
                        toRemove.add(game);
                        runningGames.add(game);
                        game.fillChests();
                        game.beginGame();
                    }
                }

                waitingGames.removeAll(toRemove);
            }
        }.runTaskTimer(this, 0L, 60L);
    }

    @Override
    public void onDisable() {

    }


    // Configuration files
    public static File skyWarsFile;
    public static FileConfiguration skyWarsConfig;

    public void createSkyWarsConfig() {
        skyWarsFile = new File(getDataFolder(), "config.yml");
        if (!skyWarsFile.exists()) {
            skyWarsFile.getParentFile().mkdirs();
            saveResource("config.yml", false);
        }

        skyWarsConfig = new YamlConfiguration();
        try {
            skyWarsConfig.load(skyWarsFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }


    public static void saveSkyWarConfig() {
        try {
            skyWarsConfig.save(skyWarsFile);
        } catch (Exception e) {e.printStackTrace();}
    }


    public static File mapFile;
    public static FileConfiguration mapConfig;

    private void createMapConfig() {
        mapFile = new File(getDataFolder(), "maps.yml");
        if (!mapFile.exists()) {
            mapFile.getParentFile().mkdirs();
            saveResource("maps.yml", false);
        }

        mapConfig = new YamlConfiguration();
        try {
            mapConfig.load(mapFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static void saveMapConfig() {
        try {
            mapConfig.save(mapFile);
        } catch (Exception e) {e.printStackTrace();}
    }


    public static File playerDataFile;
    public static FileConfiguration playerDataConfig;

    private void createPlayerDataConfig() {
        playerDataFile = new File(getDataFolder(), "playerdata.yml");
        if (!playerDataFile.exists()) {
            playerDataFile.getParentFile().mkdirs();
            saveResource("playerdata.yml", false);
        }

        playerDataConfig = new YamlConfiguration();
        try {
            playerDataConfig.load(playerDataFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static void savePlayerDataConfig() {
        try {
            playerDataConfig.save(playerDataFile);
        } catch (Exception e) {e.printStackTrace();}
    }



    public static File kitsFile;
    public static FileConfiguration kitsConfig;

    private void createKitsConfig() {
        kitsFile = new File(getDataFolder(), "kits.yml");
        if (!kitsFile.exists()) {
            kitsFile.getParentFile().mkdirs();
            saveResource("kits.yml", false);
        }

        kitsConfig = new YamlConfiguration();
        try {
            kitsConfig.load(kitsFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static void saveKitsConfig() {
        try {
            kitsConfig.save(kitsFile);
        } catch (Exception e) {e.printStackTrace();}
    }
}
