package me.dri.core.entities;

import me.dri.core.enums.QueueStatus;
import me.dri.core.enums.Rank;

public class Player {

    private Long id;
    private String discordId;
    private String discordName;

    private Rank rank;

    private Integer points;
    private Integer wins;

    private Integer losses;

    private QueueStatus queueStatus;

    public Player() {

    }

    public Player(Long id, String discordId, String discordName, Rank rank, Integer points, Integer wins, Integer losses, QueueStatus queueStatus) {
        this.id = id;
        this.discordId = discordId;
        this.discordName = discordName;
        this.rank = rank;
        this.points = points;
        this.wins = wins;
        this.losses = losses;
        this.queueStatus = queueStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDiscordId() {
        return discordId;
    }

    public void setDiscordId(String discordId) {
        this.discordId = discordId;
    }

    public String getDiscordName() {
        return discordName;
    }

    public void setDiscordName(String discordName) {
        this.discordName = discordName;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getWins() {
        return wins;
    }

    public void setWins(Integer wins) {
        this.wins = wins;
    }

    public Integer getLosses() {
        return losses;
    }

    public void setLosses(Integer losses) {
        this.losses = losses;
    }

    public QueueStatus getQueueStatus() {
        return queueStatus;
    }

    public void setQueueStatus(QueueStatus queueStatus) {
        this.queueStatus = queueStatus;
    }
}
