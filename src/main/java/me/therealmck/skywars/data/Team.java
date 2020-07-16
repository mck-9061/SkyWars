package me.therealmck.skywars.data;

import me.therealmck.skywars.data.players.GamePlayer;

public class Team {
    private GamePlayer player1;
    private GamePlayer player2;

    public Team() {

    }

    public GamePlayer getPlayer1() {
        return player1;
    }

    public void setPlayer1(GamePlayer player1) {
        this.player1 = player1;
    }

    public GamePlayer getPlayer2() {
        return player2;
    }

    public void setPlayer2(GamePlayer player2) {
        this.player2 = player2;
    }

    public boolean containsPlayer(GamePlayer player) {
        return player.equals(player1) || player.equals(player2);
    }
}
