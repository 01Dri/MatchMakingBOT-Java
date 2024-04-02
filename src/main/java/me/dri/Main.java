package me.dri;

import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.interaction.ApplicationCommandInteractionEvent;
import discord4j.core.event.domain.interaction.ButtonInteractionEvent;
import io.github.cdimascio.dotenv.Dotenv;
import me.dri.core.commands.Commands;
import me.dri.core.commands.QueueCommands;
import me.dri.core.commands.RegisterQueueCommands;
import me.dri.core.repositories.*;
import me.dri.core.services.*;
import me.dri.infra.discord.callbacks.ButtonCallbacksImpl;
import me.dri.infra.discord.commands.SlashCommandManager;
import me.dri.infra.discord.config.DiscordBotConfig;
import me.dri.infra.discord.interfaces.ButtonCallbacks;
import org.hibernate.cfg.Configuration;

public class Main {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml"); // ou qualquer outra forma de configuração
        var sessionFactory = configuration.buildSessionFactory();


        PlayerRepository playerRepository = new PlayerRepositoryImpl(sessionFactory);
        IButtonRepository buttonRepository = new ButtonsRepository();
        ButtonService buttonService = new ButtonServiceImpl(buttonRepository);
        QueueRepository queueRepository = new QueueRepositoryImpl();
        QueueService queueService = new QueueServiceImpl(queueRepository);
        Commands queueCommands = new QueueCommands(queueService, buttonService);
        QueueCallbackServices queueCallbackServices = new JoinQueueEventService(queueService, playerRepository, queueCommands);
        ButtonCallbacks buttonCallbacks = new ButtonCallbacksImpl(buttonService, queueCallbackServices);

        Dotenv dotenv = Dotenv.configure().load();
        DiscordBotConfig config = new DiscordBotConfig(dotenv.get("TOKEN_DISCORD"), ".");

        // REGISTER COMMANDS
        GatewayDiscordClient gateway = config.getClient().gateway().login().block();
        SlashCommandManager.registerCommands(gateway, new RegisterQueueCommands(queueCommands));

        // LISTENER
        gateway.on(ApplicationCommandInteractionEvent.class)
                .flatMap(SlashCommandManager.handleInteraction(new RegisterQueueCommands(queueCommands)))
                .subscribe();

        gateway.on(ButtonInteractionEvent.class)
                        .flatMap(buttonCallbacks.handle()).subscribe();
        gateway.onDisconnect().block();


    }
}