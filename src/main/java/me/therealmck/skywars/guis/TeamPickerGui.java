package me.therealmck.skywars.guis;

import me.therealmck.skywars.Main;
import me.therealmck.skywars.data.Game;
import me.therealmck.skywars.data.players.GamePlayer;
import me.therealmck.skywars.utils.Utils;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class TeamPickerGui {
    private Inventory bukkitInventory;
    private Player host;
    private Game game;

    public TeamPickerGui(Player host, Game game) {
        this.host = host;
        this.game = game;
        this.bukkitInventory = Bukkit.createInventory(host, 18, "§6Pick your team");

        for (int slot = 0; slot < 18; slot++) {
            bukkitInventory.setItem(slot, new ItemStack(Material.RED_STAINED_GLASS_PANE));
        }

        for (GamePlayer p : game.getPlayers()) {
            Main.preventInventoryCloseList.add(p.getBukkitPlayer());
        }

        List<Material> wools = new ArrayList<>();
        wools.add(Material.WHITE_WOOL);
        wools.add(Material.BLACK_WOOL);
        wools.add(Material.BLUE_WOOL);
        wools.add(Material.BROWN_WOOL);
        wools.add(Material.CYAN_WOOL);
        wools.add(Material.GRAY_WOOL);
        wools.add(Material.GREEN_WOOL);
        wools.add(Material.LIME_WOOL);
        wools.add(Material.MAGENTA_WOOL);
        wools.add(Material.ORANGE_WOOL);
        wools.add(Material.PINK_WOOL);
        wools.add(Material.PURPLE_WOOL);
        wools.add(Material.RED_WOOL);
        wools.add(Material.YELLOW_WOOL);

        for (int i = 0; i < Main.skyWarsConfig.getInt("TeamCount"); i++) {
            bukkitInventory.setItem(i, Utils.getItemStackWithNameAndLore(wools.get(i), WordUtils.capitalize(wools.get(i).name().split("_")[0].toLowerCase()), new ArrayList<>()));
        }
    }

    public Inventory getBukkitInventory() {
        return bukkitInventory;
    }
}
