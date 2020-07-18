package me.therealmck.skywars.listeners;

import me.therealmck.skywars.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryCloseListener implements Listener {
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        if (Main.preventInventoryCloseList.contains(player)) player.openInventory(event.getInventory());

    }
}
