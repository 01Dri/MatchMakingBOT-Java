package me.dri.core.commands;

import discord4j.core.event.domain.interaction.ApplicationCommandInteractionEvent;
import reactor.core.publisher.Mono;

public interface Commands {

    Mono<Object> handle(ApplicationCommandInteractionEvent event);

    Mono<Void> delete(ApplicationCommandInteractionEvent event);

}
