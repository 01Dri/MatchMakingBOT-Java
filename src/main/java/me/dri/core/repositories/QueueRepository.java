package me.dri.core.repositories;

import me.dri.core.entities.Queue;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface QueueRepository {

    boolean saveQueue(Queue queue);
    boolean deleteQueue(Queue queue);
    Optional<Queue> findQueueById(UUID queueId);
    List<Queue> findQueueNotFull();
    List<Queue> findAllQueues();

}
