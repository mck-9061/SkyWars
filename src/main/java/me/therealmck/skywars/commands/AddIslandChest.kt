package me.therealmck.skywars.commands

import me.therealmck.skywars.Main
import me.therealmck.skywars.data.SkyWarsMap
import me.therealmck.skywars.utils.MessageHelper
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class AddIslandChest : CommandExecutor {
    override fun onCommand(commandSender: CommandSender, command: Command, s: String, args: Array<String>): Boolean {
        val lang = MessageHelper()
        if (commandSender is Player && commandSender.hasPermission("skywars.admin.addislandchest")) {
            val block = commandSender.getTargetBlock(null, 5)
            var map: SkyWarsMap? = null
            for (m in Main.maps) {
                if (m.bukkitWorld!!.name == block.world.name) map = m
            }
            if (map == null) {
                commandSender.sendMessage(lang.adminLocationAddFail)
                return true
            }
            if (block.type == Material.CHEST) {
                map.addIslandChest(block)
                commandSender.sendMessage(lang.adminChestAdded)
            } else {
                commandSender.sendMessage(lang.adminChestFailed)
            }
        }
        return true
    }
}