package me.therealmck.skywars.listeners;

import me.therealmck.skywars.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class InventoryCloseListener implements Listener {
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();

        new BukkitRunnable() {
            @Override
            public void run() {
                if (Main.preventInventoryCloseList.contains(player)) player.openInventory(event.getInventory());
            }
        }.runTaskLater(Main.instance, 2);
    }
}
