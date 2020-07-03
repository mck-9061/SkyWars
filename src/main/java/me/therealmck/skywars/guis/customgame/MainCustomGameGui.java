package me.therealmck.skywars.guis.customgame;

import me.therealmck.skywars.data.Game;
import me.therealmck.skywars.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;

public class MainCustomGameGui {
    private Inventory bukkitInventory;
    private Player host;
    private Game game;

    public MainCustomGameGui(Player host, Game game) {
        this.host = host;
        this.game = game;
        this.bukkitInventory = Bukkit.createInventory(host, 27, "§6Custom Game Settings");

        for (int slot = 0; slot < 27; slot++) {
            bukkitInventory.setItem(slot, new ItemStack(Material.RED_STAINED_GLASS_PANE));
        }

        bukkitInventory.setItem(10, Utils.getItemStackWithNameAndLore(Material.GRASS_BLOCK, "Island Loot Level", new ArrayList<>()));
        bukkitInventory.setItem(12, Utils.getItemStackWithNameAndLore(Material.DIAMOND_BLOCK, "Mid Loot Level", new ArrayList<>()));
        bukkitInventory.setItem(14, Utils.getItemStackWithNameAndLore(Material.BREWING_STAND, "Modifiers", new ArrayList<>()));
        bukkitInventory.setItem(16, Utils.getItemStackWithNameAndLore(Material.CLOCK, "Events", new ArrayList<>()));

        bukkitInventory.setItem(18, Utils.getItemStackWithNameAndLore(Material.GREEN_CONCRETE, "Select teams and begin game", new ArrayList<>()));
        bukkitInventory.setItem(26, Utils.getItemStackWithNameAndLore(Material.BARRIER, "Delete room", new ArrayList<>()));


    }

    public Inventory getBukkitInventory() {
        return bukkitInventory;
    }

    public Player getHost() {
        return host;
    }
}
