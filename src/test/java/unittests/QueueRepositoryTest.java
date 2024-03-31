package unittests;

import me.dri.core.entities.Player;
import me.dri.core.entities.Queue;
import me.dri.core.enums.QueueStatus;
import me.dri.core.enums.Rank;
import me.dri.core.exceptions.NotFoundQueueException;
import me.dri.core.exceptions.QueueFullException;
import me.dri.core.repositories.QueueRepository;
import me.dri.core.repositories.QueueRepositoryImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.*;

public class QueueRepositoryTest {

    private  QueueRepository queueRepository;

    @Before
    public void setup() {
        this.queueRepository = new QueueRepositoryImpl();
    }

    @Test
    public void saveQueueTest() {
        Queue mockQueue = new Queue(UUID.randomUUID(), Rank.RANK_A, 2);
        Assert.assertTrue(this.queueRepository.saveQueue(mockQueue));
        Assert.assertNotNull(this.queueRepository.findQueueById(mockQueue.getId()));
        assertEquals(1, this.queueRepository.findAllQueues().size());
    }

    @Test
    public void deleteQueueTest() {
        Queue mockQueue = new Queue(UUID.randomUUID(), Rank.RANK_A, 2);
        Assert.assertTrue(this.queueRepository.saveQueue(mockQueue));
        Assert.assertTrue(this.queueRepository.deleteQueue(mockQueue));
        assertEquals(0, this.queueRepository.findAllQueues().size());
    }

    @Test
    public void findQueueById() {
        Queue mockQueue = new Queue(UUID.randomUUID(), Rank.RANK_A, 2);
        Assert.assertTrue(this.queueRepository.saveQueue(mockQueue));
        Queue result = this.queueRepository.findQueueById(mockQueue.getId()).get();
        assertNotNull(result);
        assertEquals(1, this.queueRepository.findAllQueues().size());
        assertEquals(mockQueue.getId(), result.getId());
        assertEquals(mockQueue.getRank(), result.getRank());
        assertEquals(mockQueue.getMaxPlayers(), result.getMaxPlayers());
        assertEquals(0, result.getPlayers().size());

    }

    @Test
    public void findQueueNotFull() {
        // ARRANGE
        List<Player> players = new ArrayList<>();
        Player player = new Player(1L, "teste", "drikill", Rank.RANK_A, 120, 0, 10, QueueStatus.DEFAULT);
        Player player2 = new Player(2L, "teste", "drikill", Rank.RANK_A, 120, 0, 10, QueueStatus.DEFAULT);
        Player player3 = new Player(3L, "teste", "drikill", Rank.RANK_A, 120, 0, 10, QueueStatus.DEFAULT);
        Player player4 = new Player(4L, "teste", "drikill", Rank.RANK_A, 120, 0, 10, QueueStatus.DEFAULT);
        Player player5 = new Player(5L, "teste", "drikill", Rank.RANK_A, 120, 0, 10, QueueStatus.DEFAULT);
        players.add(player);
        players.add(player2);
        players.add(player3);
        players.add(player4);
        players.add(player5);

        List<Queue> queues = new ArrayList<>();
        Queue queue = new Queue(UUID.randomUUID(), Rank.RANK_A, 2);
        Queue queue2 = new Queue(UUID.randomUUID(), Rank.RANK_A, 2);
        Queue queue3 = new Queue(UUID.randomUUID(), Rank.RANK_A, 2);
        queues.add(queue);
        queues.add(queue2);
        queues.add(queue3);

        for (Queue q : queues) {
            this.queueRepository.saveQueue(q);
        }

        queue.addPlayer(player);
        queue.addPlayer(player2);
        queue2.addPlayer(player3);
        queue2.addPlayer(player4);
        queue3.addPlayer(player5);

        // ACT
        Queue result = this.queueRepository.findQueueNotFull().get();
        assertNotNull(result);
        assertEquals(queue3.getId(), result.getId());

        //ASSERT

    }

    @Test
    public void findAllQueuesTest() {

        // ARRANGE
        Queue mockQueue = new Queue(UUID.randomUUID(), Rank.RANK_A, 2);
        Queue mockQueue2 = new Queue(UUID.randomUUID(), Rank.RANK_A, 2);
        Queue mockQueue3 = new Queue(UUID.randomUUID(), Rank.RANK_A, 2);
        this.queueRepository.saveQueue(mockQueue);
        this.queueRepository.saveQueue(mockQueue2);
        this.queueRepository.saveQueue(mockQueue3);

        // ACT
        var result = this.queueRepository.findAllQueues().size();

        // Assert
        assertEquals(3, result);

    }

    @Test
    public void updateQueueStatusTest() {
        Player player = new Player(1L, "teste", "drikill", Rank.RANK_A, 120, 0, 10, QueueStatus.DEFAULT);
        Queue mockQueue = new Queue(UUID.randomUUID(), Rank.RANK_A, 2);
        this.queueRepository.saveQueue(mockQueue);
        assertEquals(0, mockQueue.getPlayers().size());
        mockQueue.addPlayer(player);
        this.queueRepository.saveQueue(mockQueue);

        var result = this.queueRepository.findQueueById(mockQueue.getId()).get();
        assertEquals(1, result.getPlayers().size());
        assertEquals(mockQueue.getId(), result.getId());

    }

    @Test
    public void failedToDeleteQueueTest() {
        Queue mockQueue = new Queue(UUID.randomUUID(), Rank.RANK_A, 2);
        assertFalse(this.queueRepository.deleteQueue(mockQueue));
    }

    @Test
    public void notFoundQueueByIdTest() {
        assertThrows(NotFoundQueueException.class, () -> this.queueRepository.findQueueById(UUID.randomUUID()));
    }

    @Test
    public void notFoundQueueNotFull() {
        Player player = new Player(1L, "teste", "drikill", Rank.RANK_A, 120, 0, 10, QueueStatus.DEFAULT);
        Player player2 = new Player(2L, "teste", "drikill", Rank.RANK_A, 120, 0, 10, QueueStatus.DEFAULT);
        Queue mockQueue = new Queue(UUID.randomUUID(), Rank.RANK_A, 2);
        mockQueue.addPlayer(player);
        mockQueue.addPlayer(player2);
        var result = this.queueRepository.findQueueNotFull();
        assertEquals(Optional.empty(), result);
    }

}
