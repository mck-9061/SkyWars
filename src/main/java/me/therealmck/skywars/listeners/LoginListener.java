package me.therealmck.skywars.listeners;

import me.therealmck.skywars.Main;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class LoginListener implements Listener {
    @EventHandler
    public void onLogin(PlayerJoinEvent event) {
        ConfigurationSection section = Main.playerDataConfig.getConfigurationSection(event.getPlayer().getUniqueId().toString());
        if (section == null) {
            section = Main.playerDataConfig.createSection(event.getPlayer().getUniqueId().toString());
            section.set("tp", false);
            section.set("kills", 0);
            section.set("deaths", 0);
            section.set("wins", 0);
        }

        if (section.getBoolean("tp")) {
            event.getPlayer().teleport(Main.skyWarsConfig.getLocation("LobbyLocation"));
            section.set("tp", false);
        }

        Main.savePlayerDataConfig();


    }
}
