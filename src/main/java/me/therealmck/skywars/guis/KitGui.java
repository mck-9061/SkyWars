package me.therealmck.skywars.guis;

import me.therealmck.skywars.Main;
import me.therealmck.skywars.data.Game;
import me.therealmck.skywars.data.Kit;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public class KitGui {
    private Inventory bukkitInventory;
    private Game game;
    private Player player;

    public KitGui(Player player, Game game) {
        this.game = game;
        this.player = player;

        bukkitInventory = Bukkit.createInventory(null, 54, "§6Select Kit");

        int count = 0;

        for (Kit kit : Main.kits) {
            bukkitInventory.setItem(count, kit.getIcon(player));
        }
    }

    public Inventory getBukkitInventory() {

        return bukkitInventory;
    }
}
