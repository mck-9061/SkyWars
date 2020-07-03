package me.therealmck.skywars.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class Utils {
    public static ItemStack getItemStackWithNameAndLore(Material material, String name, List<String> lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        meta.setLore(lore);
        meta.setDisplayName(name);
        item.setItemMeta(meta);

        return item;
    }
}
