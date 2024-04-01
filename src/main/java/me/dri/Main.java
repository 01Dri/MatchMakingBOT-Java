package me.dri;

import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.interaction.ApplicationCommandInteractionEvent;
import io.github.cdimascio.dotenv.Dotenv;
import me.dri.core.commands.Commands;
import me.dri.core.commands.QueueCommands;
import me.dri.core.commands.RegisterQueueCommands;
import me.dri.core.repositories.QueueRepository;
import me.dri.core.repositories.QueueRepositoryImpl;
import me.dri.core.services.QueueService;
import me.dri.core.services.QueueServiceImpl;
import me.dri.infra.discord.commands.SlashCommandManager;
import me.dri.infra.discord.config.DiscordBotConfig;

public class Main {
    public static void main(String[] args) {
        QueueRepository queueRepository = new QueueRepositoryImpl();
        QueueService queueService = new QueueServiceImpl(queueRepository);
        Commands queueCommands = new QueueCommands(queueService);
        Dotenv dotenv = Dotenv.configure().load();
        DiscordBotConfig config = new DiscordBotConfig(dotenv.get("TOKEN_DISCORD"), ".");

        // REGISTER COMMANDS
        GatewayDiscordClient gateway = config.getClient().gateway().login().block();
        SlashCommandManager.registerCommands(gateway, new RegisterQueueCommands(queueCommands));

        // LISTENER
        gateway.on(ApplicationCommandInteractionEvent.class)
                .flatMap(SlashCommandManager.handleInteraction(new RegisterQueueCommands(queueCommands)))
                .subscribe();
        gateway.onDisconnect().block();


    }
}