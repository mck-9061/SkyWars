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

public class MidLootGui {
    private Inventory bukkitInventory;
    private Player host;
    private Game game;

    public MidLootGui(Player host, Game game) {
        this.host = host;
        this.game = game;
        this.bukkitInventory = Bukkit.createInventory(host, 27, "§6Mid Loot Settings");

        for (int slot = 0; slot < 27; slot++) {
            bukkitInventory.setItem(slot, new ItemStack(Material.RED_STAINED_GLASS_PANE));
        }

        bukkitInventory.setItem(9, Utils.getItemStackWithNameAndLore(Material.DIAMOND_SWORD, "Sword Loot Level", Collections.singletonList(game.getSettings().getMidLootTable().getSwordLevel().name().split("_")[1])));
        bukkitInventory.setItem(11, Utils.getItemStackWithNameAndLore(Material.BOW, "Bow Loot Level", Collections.singletonList(game.getSettings().getMidLootTable().getBowLevel().name().split("_")[1])));
        bukkitInventory.setItem(12, Utils.getItemStackWithNameAndLore(Material.ENDER_PEARL, "Pearl Loot Level", Collections.singletonList(game.getSettings().getMidLootTable().getPearlLevel().name().split("_")[1])));
        bukkitInventory.setItem(14, Utils.getItemStackWithNameAndLore(Material.SNOWBALL, "Projectile Loot Level", Collections.singletonList(game.getSettings().getMidLootTable().getProjectileLevel().name().split("_")[1])));
        bukkitInventory.setItem(15, Utils.getItemStackWithNameAndLore(Material.DIAMOND_CHESTPLATE, "Armor Loot Level", Collections.singletonList(game.getSettings().getMidLootTable().getArmorLevel().name().split("_")[1])));
        bukkitInventory.setItem(17, Utils.getItemStackWithNameAndLore(Material.OAK_PLANKS, "Misc. Loot Level", Collections.singletonList(game.getSettings().getMidLootTable().getMiscLevel().name().split("_")[1])));

        bukkitInventory.setItem(26, Utils.getItemStackWithNameAndLore(Material.BARRIER, "Back", new ArrayList<>()));


    }

    public Inventory getBukkitInventory() {
        return bukkitInventory;
    }

    public Player getHost() {
        return host;
    }
}
