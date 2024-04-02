package me.dri.core.services;

import me.dri.core.entities.Player;
import me.dri.core.entities.Queue;
import me.dri.core.enums.Rank;

public interface QueueService {

    Queue createQueues(Rank rank);
    Boolean saveQueue(Queue queue);
    Queue addPlayerOnQueue(Player player);
    Queue findCurrentQueue(String playerName);

    void removePlayerOnQueue(Player player);
}
