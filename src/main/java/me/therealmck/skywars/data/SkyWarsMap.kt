package me.therealmck.skywars.data

import me.therealmck.skywars.Main
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.block.Block
import java.util.*

class SkyWarsMap {
    var bukkitWorld: World?
        private set
    private var spawns: MutableList<Location>
    private var islandChests: MutableList<Location>
    private var midChests: MutableList<Location>
    private var lobby: Location? = null

    constructor(bukkitWorld: World?) {
        this.bukkitWorld = bukkitWorld
        spawns = ArrayList()
        islandChests = ArrayList()
        midChests = ArrayList()
    }

    constructor(worldName: String?) {
        spawns = ArrayList()
        islandChests = ArrayList()
        midChests = ArrayList()
        val section = Main.mapConfig.getConfigurationSection(worldName!!)!!
        bukkitWorld = Bukkit.getWorld(worldName)
        lobby = section.getLocation("lobby")
        val spawns = section.getList("Spawns") as List<Location?>?
        val islandChests = section["IslandChests"] as List<Location>?
        val midChests = section["MidChests"] as List<Location>?
        for (spawn in spawns!!) {
            val s = spawn!!.clone()
            s.world = Bukkit.getWorld(worldName)
            this.spawns.add(s)
        }
        for (chest in islandChests!!) {
            val s = chest.clone()
            s.world = Bukkit.getWorld(worldName)
            this.islandChests.add(s)
        }
        for (chest in midChests!!) {
            val s = chest.clone()
            s.world = Bukkit.getWorld(worldName)
            this.midChests.add(s)
        }
    }

    fun getSpawns(): List<Location> {
        return spawns
    }

    fun addSpawn(spawn: Location) {
        spawns.add(spawn)
        val section = Main.mapConfig.getConfigurationSection(bukkitWorld!!.name)!!
        section["Spawns"] = spawns
        Main.saveMapConfig()
    }

    fun addIslandChest(chest: Block) {
        islandChests.add(chest.location)
        val section = Main.mapConfig.getConfigurationSection(bukkitWorld!!.name)!!
        section["IslandChests"] = islandChests
        Main.saveMapConfig()
    }

    fun addMidChest(chest: Block) {
        midChests.add(chest.location)
        val section = Main.mapConfig.getConfigurationSection(bukkitWorld!!.name)!!
        section["MidChests"] = midChests
        Main.saveMapConfig()
    }

    fun getIslandChests(): List<Location> {
        return islandChests
    }

    fun getMidChests(): List<Location> {
        return midChests
    }
}