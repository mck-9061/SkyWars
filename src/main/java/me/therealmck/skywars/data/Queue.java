package me.therealmck.skywars.data;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Queue {
    private List<Player> regularQueue;
    private List<Player> fastPassQueue;

    public Queue() {
        this.regularQueue = new ArrayList<>();
        this.fastPassQueue = new ArrayList<>();
    }
}
