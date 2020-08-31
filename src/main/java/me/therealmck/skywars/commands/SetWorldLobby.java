package me.therealmck.skywars.commands;

import me.therealmck.skywars.Main;
import me.therealmck.skywars.data.SkyWarsMap;
import me.therealmck.skywars.utils.MessageHelper;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetWorldLobby implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        MessageHelper lang = new MessageHelper();
        if (commandSender instanceof Player && commandSender.hasPermission("skywars.admin.setworldlobby")) {
            Location location = ((Player) commandSender).getLocation();
            SkyWarsMap map = null;

            for (SkyWarsMap m : Main.maps) {
                if (m.getBukkitWorld().getName().equals(location.getWorld().getName())) map = m;
            }

            if (map == null) {
                commandSender.sendMessage("You're not in a SkyWars Map.");
                return true;
            }

            map.setLobby(location);
            commandSender.sendMessage("Lobby set.");
        }
        return true;
    }
}
