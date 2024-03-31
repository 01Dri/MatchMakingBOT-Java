package me.dri;

import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.interaction.ApplicationCommandInteractionEvent;
import io.github.cdimascio.dotenv.Dotenv;
import me.dri.infra.discord.commands.SlashCommandManager;
import me.dri.infra.discord.config.DiscordBotConfig;

public class Main {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure().load();
        DiscordBotConfig config = new DiscordBotConfig(dotenv.get("TOKEN_DISCORD"), ".");
        GatewayDiscordClient gateway = config.getClient().gateway().login().block();

        SlashCommandManager.registerSlashCommand(gateway, "start");
        SlashCommandManager.registerSlashCommand(gateway, "cancel");

        gateway.on(ApplicationCommandInteractionEvent.class)
                .flatMap(SlashCommandManager.handleInteraction())
                .subscribe();
        gateway.onDisconnect().block();

    }
}