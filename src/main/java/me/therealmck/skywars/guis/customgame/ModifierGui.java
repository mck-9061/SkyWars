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

public class ModifierGui {
    private Inventory bukkitInventory;
    private Player host;
    private Game game;

    public ModifierGui(Player host, Game game) {
        MessageHelper lang = new MessageHelper();
        this.host = host;
        this.game = game;
        this.bukkitInventory = Bukkit.createInventory(host, 27, lang.getCustomGameModifierGui());

        for (int slot = 0; slot < 27; slot++) {
            bukkitInventory.setItem(slot, new ItemStack(Material.RED_STAINED_GLASS_PANE));
        }

        bukkitInventory.setItem(11, Utils.getItemStackWithNameAndLore(Material.RABBIT_FOOT, lang.getJumpModifier(), Collections.singletonList(String.valueOf(game.settings.getJumpMultiplier()))));
        bukkitInventory.setItem(13, Utils.getItemStackWithNameAndLore(Material.SUGAR, lang.getSpeedModifier(), Collections.singletonList(String.valueOf(game.settings.getSpeedMultiplier()))));
        bukkitInventory.setItem(15, Utils.getItemStackWithNameAndLore(Material.RED_CONCRETE, lang.getHealthModifier(), Collections.singletonList(String.valueOf(game.settings.getMaxHealth()))));

        bukkitInventory.setItem(26, Utils.getItemStackWithNameAndLore(Material.BARRIER, lang.getGuiBack(), new ArrayList<>()));


    }

    public Inventory getBukkitInventory() {
        return bukkitInventory;
    }

    public Player getHost() {
        return host;
    }
}
