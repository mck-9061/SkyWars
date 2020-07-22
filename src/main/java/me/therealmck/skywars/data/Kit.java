package me.therealmck.skywars.data;

import me.therealmck.skywars.Main;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

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

    public ItemStack getIcon() {
        return icon;
    }

    public List<ItemStack> getItems() {
        return items;
    }
}
