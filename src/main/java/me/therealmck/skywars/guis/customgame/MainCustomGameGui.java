package me.therealmck.skywars.guis.customgame;

import me.therealmck.skywars.data.Game;
import me.therealmck.skywars.utils.MessageHelper;
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
        MessageHelper lang = new MessageHelper();
        this.host = host;
        this.game = game;
        this.bukkitInventory = Bukkit.createInventory(host, 27, lang.getCustomGameMainGui());

        for (int slot = 0; slot < 27; slot++) {
            bukkitInventory.setItem(slot, new ItemStack(Material.RED_STAINED_GLASS_PANE));
        }

        bukkitInventory.setItem(10, Utils.getItemStackWithNameAndLore(Material.GRASS_BLOCK, lang.getCustomGameMainGuiIslandLoot(), new ArrayList<>()));
        bukkitInventory.setItem(12, Utils.getItemStackWithNameAndLore(Material.DIAMOND_BLOCK, lang.getCustomGameMainGuiMidLoot(), new ArrayList<>()));
        bukkitInventory.setItem(14, Utils.getItemStackWithNameAndLore(Material.BREWING_STAND, lang.getCustomGameMainGuiModifiers(), new ArrayList<>()));
        bukkitInventory.setItem(16, Utils.getItemStackWithNameAndLore(Material.CLOCK, lang.getCustomGameMainGuiEvents(), new ArrayList<>()));

        bukkitInventory.setItem(18, Utils.getItemStackWithNameAndLore(Material.GREEN_CONCRETE, lang.getCustomGameMainGuiSelectTeams(), new ArrayList<>()));
        bukkitInventory.setItem(26, Utils.getItemStackWithNameAndLore(Material.BARRIER, lang.getCustomGameMainGuiDeleteRoom(), new ArrayList<>()));


    }

    public Inventory getBukkitInventory() {
        return bukkitInventory;
    }

    public Player getHost() {
        return host;
    }
}
