package me.therealmck.skywars.data.players;

import me.therealmck.skywars.Main;
import me.therealmck.skywars.data.Game;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class GamePlayer {
    private Player bukkitPlayer;
    private int kills;
    private boolean dead = false;

    public GamePlayer(Player bukkitPlayer) {
        this.bukkitPlayer = bukkitPlayer;
        this.kills = 0;
    }

    public Player getBukkitPlayer() {
        return bukkitPlayer;
    }

    public int getKills() {
        return kills;
    }

    public void addKill() {
        this.kills++;
    }

    public void saveStats(boolean won) {
        ConfigurationSection section = Main.playerDataConfig.getConfigurationSection(bukkitPlayer.getUniqueId().toString());
        int currentKills = section.getInt("kills");
        int currentDeaths = section.getInt("deaths");
        int currentWins = section.getInt("wins");

        currentKills += kills;

        if (won) currentWins++;
        else currentDeaths++;

        section.set("kills", currentKills);
        section.set("deaths", currentDeaths);
        section.set("wins", currentWins);

        Main.savePlayerDataConfig();
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }
}
