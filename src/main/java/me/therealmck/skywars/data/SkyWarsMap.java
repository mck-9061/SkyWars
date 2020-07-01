package me.therealmck.skywars.data;

import me.therealmck.skywars.Main;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public class SkyWarsMap {
    private World bukkitWorld;
    private List<Location> spawns;
    private List<Block> islandChests;
    private List<Block> midChests;

    public SkyWarsMap(World bukkitWorld) {
        this.bukkitWorld = bukkitWorld;
        this.spawns = new ArrayList<>();
        this.islandChests = new ArrayList<>();
        this.midChests = new ArrayList<>();
    }

    public SkyWarsMap(World bukkitWorld, List<?> spawns, List<?> islandChests, List<?> midChests) {
        this.bukkitWorld = bukkitWorld;
        this.spawns = (List<Location>) spawns;
        this.islandChests = (List<Block>) islandChests;
        this.midChests = (List<Block>) midChests;
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
        islandChests.add(chest);
        ConfigurationSection section = Main.mapConfig.getConfigurationSection(getBukkitWorld().getName());
        assert section != null;
        section.set("IslandChests", islandChests);
        Main.saveMapConfig();
    }

    public void addMidChest(Block chest) {
        midChests.add(chest);
        ConfigurationSection section = Main.mapConfig.getConfigurationSection(getBukkitWorld().getName());
        assert section != null;
        section.set("MidChests", midChests);
        Main.saveMapConfig();
    }

    public List<Block> getIslandChests() {
        return islandChests;
    }

    public List<Block> getMidChests() {
        return midChests;
    }
}
