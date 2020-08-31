package me.therealmck.skywars.commands;

import me.therealmck.skywars.Main;
import me.therealmck.skywars.data.SkyWarsMap;
import me.therealmck.skywars.utils.MessageHelper;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class AddWorld implements CommandExecutor {
    // This command adds the world the user is in to a list of worlds used as SkyWars maps.

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        MessageHelper lang = new MessageHelper();
        if (commandSender instanceof Player && commandSender.hasPermission("skywars.admin.addworld")) {
            World world = ((Player) commandSender).getWorld();

            for (SkyWarsMap map : Main.maps) {
                if (map.getBukkitWorld().getName().equals(world.getName())) {
                    commandSender.sendMessage(lang.getAdminMapAlreadyExists());
                    return true;
                }
            }

            SkyWarsMap map = new SkyWarsMap(world);
            ConfigurationSection section = Main.mapConfig.createSection(world.getName());
            section.set("Spawns", map.getSpawns());
            section.set("IslandChests", map.getIslandChests());
            section.set("MidChests", map.getMidChests());
            section.set("lobby", map.getLobby());
            Main.saveMapConfig();
            Main.maps.add(map);
            commandSender.sendMessage(lang.getAdminMapAdded());
            return true;
        } else {
            commandSender.sendMessage(lang.getUnauthorized());
        }


        return true;
    }
}
