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

public class IslandLootGui {
    private Inventory bukkitInventory;
    private Player host;
    private Game game;

    public IslandLootGui(Player host, Game game) {
        MessageHelper lang = new MessageHelper();
        this.host = host;
        this.game = game;
        this.bukkitInventory = Bukkit.createInventory(host, 27, lang.getCustomGameIslandLootGui());

        for (int slot = 0; slot < 27; slot++) {
            bukkitInventory.setItem(slot, new ItemStack(Material.RED_STAINED_GLASS_PANE));
        }

        bukkitInventory.setItem(9, Utils.getItemStackWithNameAndLore(Material.DIAMOND_SWORD, lang.getSwordLoot(), Collections.singletonList(game.settings.islandLootTable.getSwordLevel().name().split("_")[1])));
        bukkitInventory.setItem(11, Utils.getItemStackWithNameAndLore(Material.BOW, lang.getBowLoot(), Collections.singletonList(game.settings.islandLootTable.getBowLevel().name().split("_")[1])));
        bukkitInventory.setItem(12, Utils.getItemStackWithNameAndLore(Material.ENDER_PEARL, lang.getPearlLoot(), Collections.singletonList(game.settings.islandLootTable.getPearlLevel().name().split("_")[1])));
        bukkitInventory.setItem(14, Utils.getItemStackWithNameAndLore(Material.SNOWBALL, lang.getProjectileLoot(), Collections.singletonList(game.settings.islandLootTable.getProjectileLevel().name().split("_")[1])));
        bukkitInventory.setItem(15, Utils.getItemStackWithNameAndLore(Material.DIAMOND_CHESTPLATE, lang.getArmorLoot(), Collections.singletonList(game.settings.islandLootTable.getArmorLevel().name().split("_")[1])));
        bukkitInventory.setItem(17, Utils.getItemStackWithNameAndLore(Material.OAK_PLANKS, lang.getMiscLoot(), Collections.singletonList(game.settings.islandLootTable.getMiscLevel().name().split("_")[1])));

        bukkitInventory.setItem(26, Utils.getItemStackWithNameAndLore(Material.BARRIER, lang.getGuiBack(), new ArrayList<>()));


    }

    public Inventory getBukkitInventory() {
        return bukkitInventory;
    }

    public Player getHost() {
        return host;
    }
}
