package me.dri.infra.discord.commands;

import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.interaction.ApplicationCommandInteractionEvent;
import discord4j.discordjson.json.ApplicationCommandRequest;
import me.dri.core.commands.Commands;
import me.dri.core.commands.QueueCommands;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public class SlashCommandManager {

    public static Disposable registerSlashCommand(GatewayDiscordClient gatewayDiscordClient, String nameCommand) {
        ApplicationCommandRequest commandRequest = ApplicationCommandRequest.builder()
                .name(nameCommand)
                .description("Test command")
                .build();

        long applicationId = gatewayDiscordClient.getRestClient().getApplicationId().block();
        return gatewayDiscordClient.getRestClient().getApplicationService().createGlobalApplicationCommand(
                        applicationId, commandRequest)
                .subscribe();
    }

    public static Function<ApplicationCommandInteractionEvent, Mono<Void>> handleInteraction() {
        Commands queueCommands = new QueueCommands();
        return event -> {
            String commandName = event.getCommandName();
            switch (commandName) {
                case "start":
                    return queueCommands.handle(event).then();
                case "cancel":
                    return queueCommands.delete(event);

                default:
                    return Mono.empty();
            }
        };
    }
}
