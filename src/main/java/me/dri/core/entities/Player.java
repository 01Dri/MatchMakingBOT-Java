package me.dri.core.entities;

import jakarta.persistence.*;
import me.dri.core.enums.QueueStatus;
import me.dri.core.enums.Rank;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "players")
public class Player implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "discord_id")
    private String discordId;
    @Column(name = "discord_name")
    private String discordName;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "rank")
    private Rank rank;

    @Column(name = "points")
    private Integer points;

    @Column(name = "wins")

    private Integer wins;
    @Column(name = "losses")

    private Integer losses;

    @Column(name = "queue_status")
    @Enumerated(EnumType.ORDINAL)
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

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", discordId='" + discordId + '\'' +
                ", discordName='" + discordName + '\'' +
                ", rank=" + rank +
                ", points=" + points +
                ", wins=" + wins +
                ", losses=" + losses +
                ", queueStatus=" + queueStatus +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Player player = (Player) object;
        return Objects.equals(id, player.id) && Objects.equals(discordId, player.discordId) && Objects.equals(discordName, player.discordName) && rank == player.rank && Objects.equals(points, player.points) && Objects.equals(wins, player.wins) && Objects.equals(losses, player.losses) && queueStatus == player.queueStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, discordId, discordName, rank, points, wins, losses, queueStatus);
    }
}
