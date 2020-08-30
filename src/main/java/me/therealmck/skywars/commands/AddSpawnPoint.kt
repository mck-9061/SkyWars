package me.therealmck.skywars.commands

import me.therealmck.skywars.Main
import me.therealmck.skywars.data.SkyWarsMap
import me.therealmck.skywars.utils.MessageHelper
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class AddSpawnPoint : CommandExecutor {
    // This command sets a spawn point of a SkyWars map.
    override fun onCommand(commandSender: CommandSender, command: Command, s: String, strings: Array<String>): Boolean {
        val lang = MessageHelper()
        if (commandSender is Player && commandSender.hasPermission("skywars.admin.addspawnpoint")) {
            val location = commandSender.location
            var map: SkyWarsMap? = null
            for (m in Main.maps) {
                if (m.bukkitWorld!!.name == location.world!!.name) map = m
            }
            if (map == null) {
                commandSender.sendMessage(lang.adminLocationAddFail)
                return true
            }
            map.addSpawn(location)
            commandSender.sendMessage(lang.adminSpawnAdded)
        }
        return true
    }
}