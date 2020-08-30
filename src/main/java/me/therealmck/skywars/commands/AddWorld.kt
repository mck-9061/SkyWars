package me.therealmck.skywars.commands

import me.therealmck.skywars.Main
import me.therealmck.skywars.data.SkyWarsMap
import me.therealmck.skywars.utils.MessageHelper
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class AddWorld : CommandExecutor {
    // This command adds the world the user is in to a list of worlds used as SkyWars maps.
    override fun onCommand(commandSender: CommandSender, command: Command, s: String, strings: Array<String>): Boolean {
        val lang = MessageHelper()
        if (commandSender is Player && commandSender.hasPermission("skywars.admin.addworld")) {
            val world = commandSender.world
            for (map in Main.maps) {
                if (map.bukkitWorld!!.name == world.name) {
                    commandSender.sendMessage(lang.adminMapAlreadyExists)
                    return true
                }
            }
            val map = SkyWarsMap(world)
            val section = Main.mapConfig.createSection(world.name)
            section["Spawns"] = map.getSpawns()
            section["IslandChests"] = map.getIslandChests()
            section["MidChests"] = map.getMidChests()
            Main.saveMapConfig()
            Main.maps.add(map)
            commandSender.sendMessage(lang.adminMapAdded)
            return true
        } else {
            commandSender.sendMessage(lang.unauthorized)
        }
        return true
    }
}