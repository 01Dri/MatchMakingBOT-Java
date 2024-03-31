package me.dri.core.repositories;

import me.dri.core.entities.Queue;
import me.dri.core.exceptions.FailedToSaveQueue;
import me.dri.core.exceptions.NotFoundQueueException;

import java.util.*;

public class QueueRepositoryImpl  implements  QueueRepository{

    private HashMap<UUID, Queue> queues = new HashMap<>();

    @Override
    public boolean saveQueue(Queue queue) {
        try {
            this.queues.put(queue.getId(), queue);
            return true;
        } catch (Exception exception) {
            throw new FailedToSaveQueue(exception.getMessage());
        }
    }

    @Override
    public boolean deleteQueue(Queue queue) {
        if (queues.containsKey(queue.getId())) {
            this.queues.remove(queue.getId());
            return true;
        }
        return false;
    }

    @Override
    public Optional<Queue> findQueueById(UUID queueId) {
        Queue queue = this.queues.get(queueId);
        if (queue == null) {
            throw new NotFoundQueueException("Not found queue!");
        }
        return Optional.of(queue);
    }

    @Override
    public Optional<Queue> findQueueNotFull() {
        for (Queue queue : this.queues.values()) {
            if (queue.getPlayers().size() < queue.getMaxPlayers()) {
                return Optional.of(queue);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Queue> findAllQueues() {
        return new ArrayList<Queue>(this.queues.values());
    }
}
