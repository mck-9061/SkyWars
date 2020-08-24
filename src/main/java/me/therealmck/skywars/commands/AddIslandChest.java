package me.therealmck.skywars.commands;

import me.therealmck.skywars.Main;
import me.therealmck.skywars.data.SkyWarsMap;
import me.therealmck.skywars.utils.MessageHelper;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AddIslandChest implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        MessageHelper lang = new MessageHelper();
        if (commandSender instanceof Player && commandSender.hasPermission("skywars.admin.addislandchest")) {
            Block block = ((Player) commandSender).getTargetBlock(null, 5);
            SkyWarsMap map = null;

            for (SkyWarsMap m : Main.maps) {
                if (m.getBukkitWorld().getName().equals(block.getWorld().getName())) map = m;
            }

            if (map == null) {
                commandSender.sendMessage(lang.getAdminLocationAddFail());
                return true;
            }

            if (block.getType().equals(Material.CHEST)) {
                map.addIslandChest(block);
                commandSender.sendMessage(lang.getAdminChestAdded());
            } else {
                commandSender.sendMessage(lang.getAdminChestFailed());
            }
        }
        return true;
    }
}
