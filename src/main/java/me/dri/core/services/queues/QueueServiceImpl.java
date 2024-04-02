package me.dri.core.services.queues;

import me.dri.core.entities.Player;
import me.dri.core.entities.Queue;
import me.dri.core.enums.Rank;
import me.dri.core.repositories.QueueRepository;
import me.dri.core.services.queues.QueueService;

import java.util.List;
import java.util.UUID;

public class QueueServiceImpl implements QueueService {

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
        List<Queue> queueNotFull = this.queueRepository.findQueueNotFull();
        for (Queue queue: queueNotFull) {
            if (queue.getRank() == Rank.RANK_A) {
                if (player.getRank() == Rank.RANK_A) {
                    queue.addPlayer(player);
                    return queue;
                }
            } else {
                queue.addPlayer(player);
                return queue;
            }
        }
        return null;
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
