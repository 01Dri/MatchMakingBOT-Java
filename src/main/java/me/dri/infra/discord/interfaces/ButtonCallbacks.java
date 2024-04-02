package me.dri.infra.discord.interfaces;

import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.interaction.ButtonInteractionEvent;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public interface ButtonCallbacks {

    Function<ButtonInteractionEvent, Mono<Void>> handle(GatewayDiscordClient gatewayDiscordClient);
}
