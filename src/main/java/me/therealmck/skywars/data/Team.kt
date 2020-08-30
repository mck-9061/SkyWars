package me.therealmck.skywars.data

import me.therealmck.skywars.data.players.GamePlayer
import org.bukkit.Material
import java.util.*

class Team {
    private val players: MutableList<GamePlayer> = ArrayList()
    var icon: Material? = null
    fun getPlayers(): List<GamePlayer> {
        return players
    }

    fun addPlayer(player: GamePlayer) {
        players.add(player)
    }

    fun removePlayer(player: GamePlayer?) {
        players.remove(player)
    }

}