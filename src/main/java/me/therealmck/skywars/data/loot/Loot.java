package me.therealmck.skywars.data.loot;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Loot {
    private int chance;
    private int rolls;
    private List<ItemStack> items;

    public Loot(int chance, int rolls, List<?> items) {
        this.chance = chance;
        this.rolls = rolls;
        this.items = (List<ItemStack>) items;
    }

    public int getChance() {
        return chance;
    }

    public int getRolls() {
        return rolls;
    }

    public List<ItemStack> getItems() {
        return items;
    }
}
