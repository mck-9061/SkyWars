package me.therealmck.skywars.guis;

import me.therealmck.skywars.Main;
import me.therealmck.skywars.data.Game;
import me.therealmck.skywars.data.Kit;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public class KitGui implements Listener {
    private Inventory bukkitInventory;
    private HashMap<Integer, Kit> slots = new HashMap<>();
    private Game game;

    public KitGui(Player player, Game game) {
        this.bukkitInventory = Bukkit.createInventory(player, 54, "§6Select Kit");
        this.game = game;
        int count = 0;

        for (Kit kit : Main.kits) {
            if (player.hasPermission(kit.getPermission())) {
                bukkitInventory.setItem(count, kit.getIcon());
                slots.put(count, kit);
                count++;
            }
        }
    }

    public Inventory getBukkitInventory() {
        return bukkitInventory;
    }

    public HashMap<Integer, Kit> getSlots() {
        return slots;
    }


    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) return;
        
        if (!Main.pregame.contains(event.getWhoClicked())) return;
        if (!event.getView().getTitle().equals("§6Select Kit")) return;

        event.setCancelled(true);

        int slot = event.getSlot();

        if (slots.containsKey(slot)) {

        }
    }
}
