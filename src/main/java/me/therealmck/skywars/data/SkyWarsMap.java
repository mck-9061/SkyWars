package me.therealmck.skywars.data;

import me.therealmck.skywars.Main;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.List;

public class SkyWarsMap {
    private World bukkitWorld;
    private List<Location> spawns;
    private int playerCount;

    public SkyWarsMap(World bukkitWorld, int playerCount) {
        this.bukkitWorld = bukkitWorld;
        this.playerCount = playerCount;
    }

    public World getBukkitWorld() {
        return bukkitWorld;
    }

    public List<Location> getSpawns() {
        return spawns;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public void addSpawn(Location spawn) {
        spawns.add(spawn);
    }


    public static SkyWarsMap fromBukkitWorld(World bukkitWorld) {
        for (SkyWarsMap map : Main.maps) {
            if (map.bukkitWorld.equals(bukkitWorld)) return map;
        }

        return null;
    }
}
