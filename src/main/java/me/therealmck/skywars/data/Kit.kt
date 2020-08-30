package me.therealmck.skywars.data;

import me.therealmck.skywars.Main;
import me.therealmck.skywars.utils.Utils;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Kit {
    private String name;
    private String permission;
    private ItemStack icon;
    private List<ItemStack> items;

    public Kit(String identifier) {
        this.name = identifier;

        ConfigurationSection section = Main.kitsConfig.getConfigurationSection(name);
        this.permission = section.getString("permission");
        this.icon = section.getItemStack("icon");
        this.items = (List<ItemStack>) section.getList("items");
    }

    public Kit(Kit clone) {
        this.name = clone.name;
        this.permission = clone.permission;
        this.items = clone.items;
        this.icon = clone.icon;
    }

    public String getName() {
        return name;
    }

    public String getPermission() {
        return permission;
    }

    public ItemStack getIcon(Player player) {
        List<String> lore = new ArrayList<>();
        for (ItemStack item : items) {
            String name = WordUtils.capitalize(item.getType().toString().replace("_", " "));
            lore.add(String.format("%s x %s", name, item.getAmount()));
        }
        ItemStack icon = Utils.getItemStackWithNameAndLore(this.icon.getType(), name, lore);

        if (!player.hasPermission(permission)) {
            icon.setType(Material.BARRIER);
            ItemMeta meta = icon.getItemMeta();
            meta.setLore(new ArrayList<>());
            meta.setDisplayName("Kit locked!");
            icon.setItemMeta(meta);
        }
        return icon;
    }

    public List<ItemStack> getItems() {
        return items;
    }
}
