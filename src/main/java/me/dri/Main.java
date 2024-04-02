package me.dri;

import discord4j.common.util.Snowflake;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.interaction.ApplicationCommandInteractionEvent;
import discord4j.core.event.domain.interaction.ButtonInteractionEvent;
import io.github.cdimascio.dotenv.Dotenv;
import me.dri.core.commands.Commands;
import me.dri.core.commands.QueueCommands;
import me.dri.core.commands.RegisterQueueCommands;
import me.dri.core.repositories.*;
import me.dri.core.services.button.ButtonService;
import me.dri.core.services.button.ButtonServiceImpl;
import me.dri.core.services.button.JoinQueueEventService;
import me.dri.core.services.queues.QueueCallbackServices;
import me.dri.core.services.queues.QueueService;
import me.dri.core.services.queues.QueueServiceImpl;
import me.dri.infra.discord.callbacks.ButtonJoinQueueCallbackImpl;
import me.dri.infra.discord.commands.SlashCommandManager;
import me.dri.infra.discord.config.DiscordBotConfig;
import me.dri.infra.discord.config.GuildConfig;
import me.dri.infra.discord.interfaces.ButtonCallbacks;
import org.hibernate.cfg.Configuration;

public class Main {
    public static void main(String[] args) {
        GuildConfig guildConfig = new GuildConfig(Snowflake.of("1069132221388177468"), "espera");
        Dotenv dotenv = Dotenv.configure().load();
        DiscordBotConfig config = new DiscordBotConfig(dotenv.get("TOKEN_DISCORD"), ".");
        GatewayDiscordClient gateway = config.getClient().gateway().login().block();

        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml"); // ou qualquer outra forma de configuração
        var sessionFactory = configuration.buildSessionFactory();


        PlayerRepository playerRepository = new PlayerRepositoryImpl(sessionFactory);
        IButtonRepository buttonRepository = new ButtonsRepository();
        ButtonService buttonService = new ButtonServiceImpl(buttonRepository);
        QueueRepository queueRepository = new QueueRepositoryImpl();
        QueueService queueService = new QueueServiceImpl(queueRepository);
        Commands queueCommands = new QueueCommands(queueService, buttonService);
        QueueCallbackServices queueCallbackServices = new JoinQueueEventService(queueService, playerRepository, queueCommands, gateway, guildConfig);
        ButtonCallbacks buttonCallbacks = new ButtonJoinQueueCallbackImpl(buttonService, queueCallbackServices);

        // REGISTER COMMANDS
        SlashCommandManager.registerCommands(gateway, new RegisterQueueCommands(queueCommands));

        // LISTENER
        gateway.on(ApplicationCommandInteractionEvent.class)
                .flatMap(SlashCommandManager.handleInteraction(new RegisterQueueCommands(queueCommands)))
                .subscribe();

        gateway.on(ButtonInteractionEvent.class)
                        .flatMap(buttonCallbacks.handle(gateway)).subscribe();
        gateway.onDisconnect().block();


    }
}