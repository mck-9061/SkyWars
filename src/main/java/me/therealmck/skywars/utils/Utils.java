package me.therealmck.skywars.utils;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
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

    public static void copyFileStructure(File source, File target){
        try {
            ArrayList<String> ignore = new ArrayList<>(Arrays.asList("uid.dat", "session.lock"));
            if(!ignore.contains(source.getName())) {
                if(source.isDirectory()) {
                    if(!target.exists())
                        if (!target.mkdirs())
                            throw new IOException("Couldn't create world directory!");
                    String files[] = source.list();
                    for (String file : files) {
                        File srcFile = new File(source, file);
                        File destFile = new File(target, file);
                        copyFileStructure(srcFile, destFile);
                    }
                } else {
                    InputStream in = new FileInputStream(source);
                    OutputStream out = new FileOutputStream(target);
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = in.read(buffer)) > 0)
                        out.write(buffer, 0, length);
                    in.close();
                    out.close();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean unloadWorld(String worldName) {
        World world = Bukkit.getWorld(worldName);
        return world!=null && Bukkit.getServer().unloadWorld(world, false);
    }

    public static ItemStack getPlayerSkull(Player paramPlayer, String name) {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1);

        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        meta.setOwningPlayer(paramPlayer);
        meta.setDisplayName(name);
        skull.setItemMeta(meta);

        return skull;
    }

    public static OfflinePlayer getPlayerFromSkull(ItemStack skull) {
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        return meta.getOwningPlayer();
    }
}
