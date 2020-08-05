package me.therealmck.skywars.data;

import me.therealmck.skywars.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public class SkyWarsMap {
    private World bukkitWorld;
    private List<Location> spawns;
    private List<Location> islandChests;
    private List<Location> midChests;
    private Location lobby;

    public SkyWarsMap(World bukkitWorld) {
        this.bukkitWorld = bukkitWorld;
        this.spawns = new ArrayList<>();
        this.islandChests = new ArrayList<>();
        this.midChests = new ArrayList<>();
    }

    public SkyWarsMap(String worldName) {
        this.spawns = new ArrayList<>();
        this.islandChests = new ArrayList<>();
        this.midChests = new ArrayList<>();

        ConfigurationSection section = Main.mapConfig.getConfigurationSection(worldName);
        this.bukkitWorld = Bukkit.getWorld(worldName);

        this.lobby = section.getLocation("lobby");
        List<Location> spawns = (List<Location>) section.getList("Spawns");
        List<Location> islandChests = (List<Location>) section.get("IslandChests");
        List<Location> midChests = (List<Location>) section.get("MidChests");

        for (Location spawn : spawns) {
            Location s = spawn.clone();
            s.setWorld(Bukkit.getWorld(worldName));
            this.spawns.add(s);
        }
        for (Location chest : islandChests) {
            Location s = chest.clone();
            s.setWorld(Bukkit.getWorld(worldName));
            this.islandChests.add(s);
        }
        for (Location chest : midChests) {
            Location s = chest.clone();
            s.setWorld(Bukkit.getWorld(worldName));
            this.midChests.add(s);
        }
    }

    public World getBukkitWorld() {
        return bukkitWorld;
    }

    public List<Location> getSpawns() {
        return spawns;
    }

    public void addSpawn(Location spawn) {
        spawns.add(spawn);
        ConfigurationSection section = Main.mapConfig.getConfigurationSection(getBukkitWorld().getName());
        assert section != null;
        section.set("Spawns", spawns);
        Main.saveMapConfig();
    }

    public void addIslandChest(Block chest) {
        islandChests.add(chest.getLocation());
        ConfigurationSection section = Main.mapConfig.getConfigurationSection(getBukkitWorld().getName());
        assert section != null;
        section.set("IslandChests", islandChests);
        Main.saveMapConfig();
    }

    public void addMidChest(Block chest) {
        midChests.add(chest.getLocation());
        ConfigurationSection section = Main.mapConfig.getConfigurationSection(getBukkitWorld().getName());
        assert section != null;
        section.set("MidChests", midChests);
        Main.saveMapConfig();
    }

    public List<Location> getIslandChests() {
        return islandChests;
    }

    public List<Location> getMidChests() {
        return midChests;
    }
}
