package me.dri.infra.discord.callbacks;

import discord4j.core.event.domain.interaction.ButtonInteractionEvent;
import me.dri.core.entities.Button;
import me.dri.core.services.ButtonService;
import me.dri.core.services.QueueCallbackServices;
import me.dri.infra.discord.interfaces.ButtonCallbacks;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public class ButtonCallbacksImpl implements ButtonCallbacks {


    private final ButtonService buttonService;
    private final QueueCallbackServices queueCallbackServices;

    public ButtonCallbacksImpl(ButtonService buttonService, QueueCallbackServices queueCallbackServices) {
        this.buttonService = buttonService;
        this.queueCallbackServices = queueCallbackServices;
    }

    public Function<ButtonInteractionEvent, Mono<Void>> handle() {
        return event -> {
            Button button = this.buttonService.findButtonCustomId(event.getCustomId());
            if (button.getIdButton().equals("buttonQueue")) {
                return queueCallbackServices.joinQuitOnQueueCallback(event);
            }
            return null;
        };
    }
}
