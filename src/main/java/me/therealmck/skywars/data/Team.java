package me.therealmck.skywars.data;

import me.therealmck.skywars.data.players.GamePlayer;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private List<GamePlayer> players = new ArrayList<>();
    private Material icon;

    public Team() {

    }

    public List<GamePlayer> getPlayers() {
        return players;
    }

    public void addPlayer(GamePlayer player) {
        players.add(player);
    }

    public void removePlayer(GamePlayer player) {
        players.remove(player);
    }

    public Material getIcon() {
        return icon;
    }

    public void setIcon(Material icon) {
        this.icon = icon;
    }
}
