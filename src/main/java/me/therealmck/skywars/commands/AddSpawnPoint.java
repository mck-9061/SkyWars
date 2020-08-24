package me.therealmck.skywars.commands;

import me.therealmck.skywars.Main;
import me.therealmck.skywars.data.SkyWarsMap;
import me.therealmck.skywars.utils.MessageHelper;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddSpawnPoint implements CommandExecutor {
    // This command sets a spawn point of a SkyWars map.

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        MessageHelper lang = new MessageHelper();
        if (commandSender instanceof Player && commandSender.hasPermission("skywars.admin.addspawnpoint")) {
            Location location = ((Player) commandSender).getLocation();
            SkyWarsMap map = null;

            for (SkyWarsMap m : Main.maps) {
                if (m.getBukkitWorld().getName().equals(location.getWorld().getName())) map = m;
            }

            if (map == null) {
                commandSender.sendMessage(lang.getAdminLocationAddFail());
                return true;
            }

            map.addSpawn(location);
            commandSender.sendMessage(lang.getAdminSpawnAdded());
        }
        return true;
    }
}
