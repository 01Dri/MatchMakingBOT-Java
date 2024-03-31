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
    public Boolean createQueues(Rank rank) {
        Queue queueRankA = new Queue(UUID.randomUUID(), rank, MAX_PLAYERS);
        Queue queueRankB = new Queue(UUID.randomUUID(), rank, MAX_PLAYERS);
        this.queueRepository.saveQueue(queueRankA);
        this.queueRepository.saveQueue(queueRankB);
        return true;
    }

    @Override
    public Boolean saveQueue(Queue queue) {
        this.queueRepository.saveQueue(queue);
        return true;
    }

    @Override
    public Queue addPlayerOnQueue(Player player) {
        Queue queue = this.queueRepository.findQueueNotFull().get();
        if (player.getRank() == Rank.RANK_A) {
            if (queue != null) {
                queue.addPlayer(player);
                this.queueRepository.saveQueue(queue);
            }
        }
        return queue;
    }
}
