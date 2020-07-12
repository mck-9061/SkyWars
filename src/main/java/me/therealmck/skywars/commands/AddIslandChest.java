package me.therealmck.skywars.commands;

import me.therealmck.skywars.Main;
import me.therealmck.skywars.data.SkyWarsMap;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;

public class AddIslandChest implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (commandSender instanceof Player && commandSender.hasPermission("skywars.admin.addislandchest")) {
            Block block = ((Player) commandSender).getTargetBlock(null, 5);
            SkyWarsMap map = null;

            for (SkyWarsMap m : Main.maps) {
                if (m.getBukkitWorld().getName().equals(block.getWorld().getName())) map = m;
            }

            if (map == null) {
                commandSender.sendMessage("This world isn't a skywars map.");
                return true;
            }

            if (block.getType().equals(Material.CHEST)) {
                map.addIslandChest(block);
                commandSender.sendMessage("Chest added successfully.");
            } else {
                commandSender.sendMessage("You're not looking at a chest.");
            }
        }
        return true;
    }
}
