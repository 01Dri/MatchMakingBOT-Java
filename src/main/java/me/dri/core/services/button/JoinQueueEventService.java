package me.dri.core.services.button;

import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.interaction.ButtonInteractionEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.channel.VoiceChannel;
import me.dri.core.commands.Commands;
import me.dri.core.entities.Player;
import me.dri.core.entities.Queue;
import me.dri.core.enums.QueueStatus;
import me.dri.core.enums.Rank;
import me.dri.core.repositories.PlayerRepository;
import me.dri.core.services.queues.QueueCallbackServices;
import me.dri.core.services.queues.QueueService;
import me.dri.infra.discord.config.GuildConfig;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static me.dri.core.embeds.EmbedsMessages.embedJoinedQueue;

public class JoinQueueEventService implements QueueCallbackServices {

    private final QueueService queueService;
    private final PlayerRepository playerRepository;
    private final Commands commands;
    private final GatewayDiscordClient gatewayDiscordClient;

    private final GuildConfig guildConfig;

    private List<Player> playerList = new ArrayList<>();


    public JoinQueueEventService(QueueService queueService, PlayerRepository playerRepository, Commands commands, GatewayDiscordClient gatewayDiscordClient, GuildConfig guildConfig) {
        this.queueService = queueService;
        this.playerRepository = playerRepository;
        this.commands = commands;
        this.gatewayDiscordClient = gatewayDiscordClient;
        this.guildConfig = guildConfig;
    }


    @Override
    public Mono joinQuitOnQueueCallback(ButtonInteractionEvent event, GatewayDiscordClient ga) {
        Queue currentQueue = queueService.findCurrentQueue(event.getInteraction().getUser().getUsername());
        String playerName = event.getInteraction().getUser().getUsername();
        if (currentQueue != null) {
            queueService.removePlayerOnQueue(playerRepository.findPlayerByDiscordName(playerName));
            playerList.remove(playerRepository.findPlayerByDiscordName(playerName));
            return event.reply("Você saiu da fila!")
                    .withEphemeral(true)
                    .then(commands.updateButtonQueues(playerList));
        }
        return this.handleJoinQueue(event, playerName);
    }

    private Mono handleJoinQueue(ButtonInteractionEvent event, String playerName) {
        Player player = playerRepository.findPlayerByDiscordName(event.getInteraction().getUser().getUsername());
        if (player == null) {
            player = new Player(null, event.getInteraction().getUser().getId().toString(), event.getInteraction().getUser().getUsername(), Rank.RANK_B, 0, 0, 0, QueueStatus.DEFAULT);
            playerRepository.save(player);
            player = playerRepository.findPlayerByDiscordName(player.getDiscordName());
        }
        queueService.addPlayerOnQueue(player);
        playerList.add(player);
        return this.sendEmbedJoinedQueue(event, playerName);
    }


    private Mono startQueue(Queue queue) {
        if (queue.getPlayers().size() == queue.getMaxPlayers()) {
            System.out.println("FILA INICIADA!");
        }
        return Mono.empty();
    }

    private Mono sendEmbedJoinedQueue(ButtonInteractionEvent event, String playerName) {
        return event.reply("Você entrou na fila!")
                .withEphemeral(true)
                .withEmbeds(embedJoinedQueue(this.queueService.findCurrentQueue(playerName), this.getVoiceChannel()))
                .then(commands.updateButtonQueues(playerList))
                .then(this.startQueue(this.queueService.findCurrentQueue(playerName)));
    }


    private VoiceChannel getVoiceChannel() {
        return this.gatewayDiscordClient.getGuildById(this.guildConfig.getGuildId())
                .flatMapMany(Guild::getChannels)
                .ofType(VoiceChannel.class)
                .filter(channel -> channel.getName().equalsIgnoreCase(guildConfig.getVoiceChannelWaitingQueueName()))
                .next()
                .block();
    }
}
