package me.dri.core.entities;

import me.dri.core.enums.Rank;
import me.dri.core.exceptions.QueueFullException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Queue {

    private UUID id;
    private Rank rank;
    private Integer maxPlayers = 2;
    private List<Player> players = new ArrayList<>();

    public Queue() {

    }

    public Queue(UUID id, Rank rank, Integer maxPlayers) {
        this.id = id;
        this.rank = rank;
        this.maxPlayers = maxPlayers;
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public Integer getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(Integer maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public List<Player> addPlayer(Player player) {
        if (this.players.size() > maxPlayers) {
            throw new QueueFullException("Queue is full!");
        }
        this.players.add(player);
        return players;
    }

    public List<Player> getPlayers() {
        return this.players;
    }

    public boolean removePlayer(Player player) {
        return this.players.remove(player);
    }
}
