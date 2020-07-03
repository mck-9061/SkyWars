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

public class EventChooserGui {
    private Inventory bukkitInventory;
    private Player host;
    private Game game;

    public EventChooserGui(Player host, Game game) {
        this.host = host;
        this.game = game;
        this.bukkitInventory = Bukkit.createInventory(host, 27, "§6Random Event Settings");

        for (int slot = 0; slot < 27; slot++) {
            bukkitInventory.setItem(slot, new ItemStack(Material.RED_STAINED_GLASS_PANE));
        }

        bukkitInventory.setItem(10, Utils.getItemStackWithNameAndLore(Material.ANVIL, "Anvil Rain", Collections.singletonList(String.valueOf(game.getSettings().isAnvilRainEvent()))));
        bukkitInventory.setItem(12, Utils.getItemStackWithNameAndLore(Material.DIRT, "Block Decay", Collections.singletonList(String.valueOf(game.getSettings().isBlockDecayEvent()))));
        bukkitInventory.setItem(14, Utils.getItemStackWithNameAndLore(Material.DIAMOND_HORSE_ARMOR, "Tamed Horse Mount", Collections.singletonList(String.valueOf(game.getSettings().isHorseMountEvent()))));
        bukkitInventory.setItem(16, Utils.getItemStackWithNameAndLore(Material.ROTTEN_FLESH, "Zombie Horde", Collections.singletonList(String.valueOf(game.getSettings().isZombieHordeEvent()))));

        bukkitInventory.setItem(26, Utils.getItemStackWithNameAndLore(Material.BARRIER, "Back", new ArrayList<>()));


    }

    public Inventory getBukkitInventory() {
        return bukkitInventory;
    }

    public Player getHost() {
        return host;
    }
}
