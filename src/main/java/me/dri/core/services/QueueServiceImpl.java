package me.dri.core.services;

import me.dri.core.entities.Player;
import me.dri.core.entities.Queue;
import me.dri.core.enums.Rank;
import me.dri.core.repositories.QueueRepository;

import java.util.UUID;

public class QueueServiceImpl implements  QueueService{

    private final QueueRepository queueRepository;

    private static final Integer MAX_PLAYERS = 2;

    public QueueServiceImpl(QueueRepository queueRepository) {
        this.queueRepository = queueRepository;
    }

    @Override
    public Queue createQueues(Rank rank) {
        Queue queueRank = new Queue(UUID.randomUUID(), rank, MAX_PLAYERS);
        this.queueRepository.saveQueue(queueRank);
        return queueRank;
    }

    @Override
    public Boolean saveQueue(Queue queue) {
        this.queueRepository.saveQueue(queue);
        return true;
    }

    @Override
    public Queue addPlayerOnQueue(Player player) {
        Queue queue = this.queueRepository.findQueueNotFull().get();
        queue.addPlayer(player);
        this.queueRepository.saveQueue(queue);

        return queue;
    }

    @Override
    public Queue findCurrentQueue(String playerName) {
        for (Queue queue : queueRepository.findAllQueues()) {
            for (Player player : queue.getPlayers()) {
                if (player.getDiscordName().equals(playerName)) {
                    return queue;
                }
            }
        }
        return null;
    }

    @Override
    public void removePlayerOnQueue(Player player) {
        Queue queue = this.findCurrentQueue(player.getDiscordName());
        queue.removePlayer(player);
    }
}
