package me.dri.core.services;

import discord4j.core.event.domain.interaction.ButtonInteractionEvent;
import reactor.core.publisher.Mono;

public interface QueueCallbackServices {

    Mono<Void>  joinQuitOnQueueCallback( ButtonInteractionEvent event);
}
