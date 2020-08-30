package me.therealmck.skywars.commands

import me.therealmck.skywars.Main
import me.therealmck.skywars.utils.MessageHelper
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class SetLobby : CommandExecutor {
    override fun onCommand(commandSender: CommandSender, command: Command, s: String, args: Array<String>): Boolean {
        val lang = MessageHelper()
        if (commandSender is Player && commandSender.hasPermission("skywars.admin.setlobby")) {
            Main.skyWarsConfig["LobbyLocation"] = commandSender.location
            Main.saveSkyWarConfig()
            commandSender.sendMessage(lang.adminLobbySet)
        } else {
            commandSender.sendMessage(lang.unauthorized)
        }
        return true
    }
}