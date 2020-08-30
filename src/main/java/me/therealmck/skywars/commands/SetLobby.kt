package me.therealmck.skywars.commands;

import me.therealmck.skywars.Main;
import me.therealmck.skywars.utils.MessageHelper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetLobby implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        MessageHelper lang = new MessageHelper();
        if (commandSender instanceof Player && commandSender.hasPermission("skywars.admin.setlobby")) {
            Main.skyWarsConfig.set("LobbyLocation", ((Player) commandSender).getLocation());
            Main.saveSkyWarConfig();
            commandSender.sendMessage(lang.getAdminLobbySet());
        } else {
            commandSender.sendMessage(lang.getUnauthorized());
        }

        return true;
    }
}
