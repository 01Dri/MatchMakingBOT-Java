package me.dri.core.commands;

import discord4j.core.event.domain.interaction.ApplicationCommandInteractionEvent;
import me.dri.core.entities.Player;
import reactor.core.publisher.Mono;

import java.util.List;

public interface Commands {

    Mono<Object> handle(ApplicationCommandInteractionEvent event);

    Mono<Void> delete(ApplicationCommandInteractionEvent event);
    Mono<Void> updateButtonQueues(List<Player> players);


}
