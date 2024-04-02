package me.dri.infra.discord.interfaces;

import discord4j.core.event.domain.interaction.ButtonInteractionEvent;
import me.dri.core.services.ButtonService;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public interface ButtonCallbacks {

    Function<ButtonInteractionEvent, Mono<Void>> handle();
}
