package me.therealmck.skywars;

import me.therealmck.skywars.data.SkyWarsMap;
import me.therealmck.skywars.placeholderapi.SkyWarsPlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class Main extends JavaPlugin {
    public static List<SkyWarsMap> maps;


    @Override
    public void onEnable() {
        //PlaceholderAPI
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null){
            new SkyWarsPlaceholderExpansion(this).register();
        }
    }

    @Override
    public void onDisable() {

    }
}
