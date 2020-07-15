package me.therealmck.skywars.commands;

import me.therealmck.skywars.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetLobby implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (commandSender instanceof Player && commandSender.hasPermission("skywars.admin.setlobby")) {
            Main.skyWarsConfig.set("LobbyLocation", ((Player) commandSender).getLocation());
            commandSender.sendMessage("Success!");
        } else {
            commandSender.sendMessage("Something went wrong. You may not be a player, or you may not have permission.");
        }

        return true;
    }
}
