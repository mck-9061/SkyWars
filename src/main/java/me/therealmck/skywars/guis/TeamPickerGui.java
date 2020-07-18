package me.therealmck.skywars.guis;

import me.therealmck.skywars.Main;
import me.therealmck.skywars.data.Game;
import me.therealmck.skywars.data.players.GamePlayer;
import me.therealmck.skywars.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class TeamPickerGui {
    private Inventory bukkitInventory;
    private Player host;
    private Game game;

    public TeamPickerGui(Player host, Game game) {
        this.host = host;
        this.game = game;
        this.bukkitInventory = Bukkit.createInventory(host, 27, "§6Pick your teammate");

        for (int slot = 0; slot < 36; slot++) {
            bukkitInventory.setItem(slot, new ItemStack(Material.RED_STAINED_GLASS_PANE));
        }

        int count = 0;
        for (GamePlayer p : game.getPlayers()) {
            Main.preventInventoryCloseList.add(p.getBukkitPlayer());
            ItemStack skull = Utils.getPlayerSkull(p.getBukkitPlayer(), p.getBukkitPlayer().getDisplayName());
            bukkitInventory.setItem(count, skull);
            count++;
        }

        bukkitInventory.setItem(35, Utils.getItemStackWithNameAndLore(Material.BARRIER, "Random Teammate", new ArrayList<>()));
    }

    public Inventory getBukkitInventory() {
        return bukkitInventory;
    }
}
