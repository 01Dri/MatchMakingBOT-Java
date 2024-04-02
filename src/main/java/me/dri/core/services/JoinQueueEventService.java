package me.dri.core.services;

import discord4j.core.event.domain.interaction.ButtonInteractionEvent;
import me.dri.core.commands.Commands;
import me.dri.core.entities.Player;
import me.dri.core.entities.Queue;
import me.dri.core.enums.QueueStatus;
import me.dri.core.enums.Rank;
import me.dri.core.repositories.PlayerRepository;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

public class JoinQueueEventService implements QueueCallbackServices {

    private final QueueService queueService;
    private final PlayerRepository playerRepository;
    private final Commands commands;
    private List<Player> playerList = new ArrayList<>();

    public JoinQueueEventService(QueueService queueService, PlayerRepository playerRepository, Commands commands) {
        this.queueService = queueService;
        this.playerRepository = playerRepository;
        this.commands = commands;
    }

    @Override
    public Mono<Void> joinQuitOnQueueCallback(ButtonInteractionEvent event)
    {
        Queue currentQueue = queueService.findCurrentQueue(event.getInteraction().getUser().getUsername());
        String playerName = event.getInteraction().getUser().getUsername();
        if (currentQueue != null) {
            queueService.removePlayerOnQueue(playerRepository.findPlayerByDiscordName(playerName));
            playerList.remove(playerRepository.findPlayerByDiscordName(playerName));
            return event.reply("Você saiu da fila!")
                    .withEphemeral(true)
                    .then(commands.updateButtonQueues(playerList));
        } else {
            Player player = playerRepository.findPlayerByDiscordName(event.getInteraction().getUser().getUsername());
            if (player == null) {
                player = new Player(null, event.getInteraction().getUser().getId().toString(), event.getInteraction().getUser().getUsername(), Rank.RANK_B, 0, 0, 0, QueueStatus.DEFAULT);
                playerRepository.save(player);
                player = playerRepository.findPlayerByDiscordName(player.getDiscordName());
            }
            queueService.addPlayerOnQueue(player);
            playerList.add(player);
            return event.reply("Você entrou na fila!")
                    .withEphemeral(true)
                    .then(commands.updateButtonQueues(playerList));
        }
    }
}
