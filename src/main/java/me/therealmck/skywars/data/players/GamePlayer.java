package me.therealmck.skywars.data.players;

import me.therealmck.skywars.data.Game;
import org.bukkit.entity.Player;

public class GamePlayer {
    private Player bukkitPlayer;
    private int kills;
    private Game currentGame;

    public GamePlayer(Player bukkitPlayer) {
        this.bukkitPlayer = bukkitPlayer;
        this.kills = 0;
        this.currentGame = null;
    }

    public Player getBukkitPlayer() {
        return bukkitPlayer;
    }

    public int getKills() {
        return kills;
    }

    public Game getCurrentGame() {
        return currentGame;
    }

    public void setCurrentGame(Game game) {
        this.currentGame = game;
    }

    public void addKill() {
        this.kills++;
    }
}
